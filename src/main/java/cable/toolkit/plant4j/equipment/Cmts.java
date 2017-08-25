package cable.toolkit.plant4j.equipment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import cable.toolkit.plant4j.docsis.DocsSubMgt3Mib;
import cable.toolkit.plant4j.snmp.MibTable;
import cable.toolkit.plant4j.snmp.SnmpAgentSystemInfo;

/**
 * A cable modem termination system or CMTS is a piece of equipment,
 * typically located in a cable company's headend or hubsite, which
 * is used to provide high speed data services, such as cable Internet
 * or Voice over Internet Protocol, to cable subscribers.  Settop boxes
 * may also be connected to a CMTS.  A CMTS provides management
 * through SNMP.
 * 
 * @author psandiford
 *
 */
public class Cmts implements CpeListProducer {

	private static final int CMTS_SNMP_RETRIES = 2;
	private static final int CMTS_SNMP_TIMEOUT = 5000;
	private static final int BULK_GET_CPE_IP_TABLE_NUM = 30;
	
	Snmp snmp;
	Target target;
	Logger logger;
	SnmpAgentSystemInfo systemDetails;
	
	/**
	 * Default constructor
	 */
	private Cmts() {
		this.logger = LoggerFactory.getLogger(Cmts.class);
	}
	
	/**
	 * Construct a Cmts object with a given Snmp and Target object
	 * 
	 * @param snmp an initialized org.snmp4j.Snmp object
	 * @param target an initialized org.snmp4j.Target object that can be
	 * 		used to communicate with the Cmts
	 */
	public Cmts(Snmp snmp, Target target) {
		this();
		
		this.snmp = Objects.requireNonNull(snmp);

		this.target = Objects.requireNonNull(target);
		this.target.setRetries(CMTS_SNMP_RETRIES);
		this.target.setTimeout(CMTS_SNMP_TIMEOUT);
	}
	
	/**
	 * Construct a Cmts object with a give Target object.  When using
	 * this constructor, an Snmp object must be provided before calling
	 * any routines that communicate with the CMTS.
	 * 
	 * @param target an initialized org.snmp4j.Target object that can be
	 * 		used to communicate with the Cmts
	 */
	public Cmts(Target target) {
		this();
		
		this.target = Objects.requireNonNull(target);
		this.target.setRetries(CMTS_SNMP_RETRIES);
		this.target.setTimeout(CMTS_SNMP_TIMEOUT);
	}
	
	@Override
	public String toString() {
		if (this.target == null) {
			return super.toString();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("CMTS");
		if (this.systemDetails != null) {
			sb.append(" " + this.systemDetails.toString());
		}
		sb.append(" " + this.target.getAddress().toString());
		return sb.toString();
	}
	
	public void setSnmpTarget(Target target) {
		this.target = Objects.requireNonNull(target);
		this.target.setRetries(CMTS_SNMP_RETRIES);
		this.target.setTimeout(CMTS_SNMP_TIMEOUT);
	}
	
	public void setSnmp(Snmp snmp) {
		this.snmp = Objects.requireNonNull(snmp);
	}
	
	/**
	 * Get the SNMP Agent system details for this CMTS
	 */
	public Optional<SnmpAgentSystemInfo> getSystemDetails() {
		Objects.requireNonNull(this.snmp);
		Objects.requireNonNull(this.target);
		if (this.systemDetails == null) {
			try {
				SnmpAgentSystemInfo agent_info = SnmpAgentSystemInfo.getSystemInfo(this.snmp, this.target);
				this.systemDetails = agent_info;
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		
		return Optional.ofNullable(this.systemDetails);
	}

	/**
	 * Query the CMTS for a list of all CPE IP addresses and the CPE types.
	 * The given consumer is called for each CPE that is discovered.
	 */
	public void produceCpeList(Consumer<Cpe> consumer) {
		Objects.requireNonNull(this.snmp);
		Objects.requireNonNull(this.target);
		
		discoverAllCpe((c) -> consumer.accept(c));
	}

	/**
	 * Query the CMTS for a list of all CPE IP addresses and the CPE types.
	 * The given consumer is called for each CPE that is discovered.  Each time
	 * the consumer is called, a Runnable is submitted to the given ExecutorService
	 * thread pool.
	 */
	public void produceCpeList(ExecutorService threadPool, Consumer<Cpe> consumer) {
		Objects.requireNonNull(this.snmp);
		Objects.requireNonNull(this.target);
		
		discoverAllCpe((c) -> {
			threadPool.submit(() -> consumer.accept(c));
		});
	}
	
	private void discoverAllCpe(Consumer<Cpe> consumer) {
		
		MibTable cpeIpTable = new MibTable(DocsSubMgt3Mib.oid_docsSubmgt3CpeIpTable,this.snmp,this.target);
		List<OID> columnOIDs = new ArrayList<OID>();
		columnOIDs.add(DocsSubMgt3Mib.oid_docsSubmgt3CpeIpAddr);
		columnOIDs.add(DocsSubMgt3Mib.oid_docsSubmgt3CpeIpType);
		
		Consumer<List<VariableBinding>> rowConsumer = list -> {
			VariableBinding vb_addr = list.get(0);
			VariableBinding vb_type = list.get(1);
			
			Long rsid = vb_addr.getOid().getUnsigned(vb_addr.getOid().size() - 2);
			Integer cpeIpId = vb_addr.getOid().last();
			Integer cpeIpType = vb_type.getVariable().toInt();
			byte[] addressArray = ((OctetString) vb_addr.getVariable()).getValue();
			try {
				InetAddress cpeIpAddress = InetAddress.getByAddress(addressArray);
				Cpe cpe = new Cpe(cpeIpType,cpeIpAddress);
				cpe.setRsid(rsid);
				cpe.setCpeIpId(cpeIpId);
				consumer.accept(cpe);
			} catch (UnknownHostException e) {
				this.logger.error("Unable to process InetAddress", e);
			}
		};
		
		cpeIpTable.getRows(BULK_GET_CPE_IP_TABLE_NUM, columnOIDs, rowConsumer);
	}
}
