package cable.toolkit.plant4j.equipment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;

import cable.toolkit.plant4j.docsis.DocsIF3CmStatusEntry;
import cable.toolkit.plant4j.docsis.DocsIF3CmtsCmRegStatusEntry;
import cable.toolkit.plant4j.docsis.DocsIF3Mib;
import cable.toolkit.plant4j.docsis.DocsSubMgt3Mib;
import cable.toolkit.plant4j.ieee802.MacAddress;
import cable.toolkit.plant4j.snmp.DateAndTime;
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
public class Cmts implements CpeListProducer, CableModemListProducer {

	private static final int CMTS_SNMP_RETRIES = 2;
	private static final int CMTS_SNMP_TIMEOUT = 5000;
	private static final int BULK_GET_CPE_IP_TABLE_NUM = 30;
	
	Snmp snmp;
	Target target;
	Logger logger;
	SnmpAgentSystemInfo systemDetails;
	String hostname;
	
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
	
	/**
	 * Construct a Cmts object with a given InetAddress object.  When using
	 * this constructor, an Snmp object must be provided before calling
	 * any routines that communicate with the CMTS.  This constructor should
	 * only be used if the Cmts uses SNMP version 2c.
	 * 
	 * @param target The InetAddress of the CMTS
	 */
	public Cmts(InetAddress iaddr, String community) {
		this();
		
		UdpAddress cmts_addr = new UdpAddress(iaddr,SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT);
		CommunityTarget cmts_target = new CommunityTarget();
		cmts_target.setCommunity(new OctetString(community));
		cmts_target.setAddress(cmts_addr);
		cmts_target.setVersion(SnmpConstants.version2c);
		this.target = cmts_target;
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
	
	public Target getTarget() {
		return this.target;
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
	
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public Optional<String> getHostname() {
		return Optional.ofNullable(this.hostname);
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

	@Override
	public void produceCableModemsList(Consumer<CableModem> consumer) {
		Objects.requireNonNull(this.snmp);
		Objects.requireNonNull(this.target);
		
		discoverAllCableModems((c) -> consumer.accept(c));
	}

	@Override
	public void produceCableModemsList(ExecutorService threadPool, Consumer<CableModem> consumer) {
		Objects.requireNonNull(this.snmp);
		Objects.requireNonNull(this.target);
		
		discoverAllCableModems((c) -> {
			threadPool.submit(() -> consumer.accept(c));
		});
	}
	
	private void discoverAllCableModems(Consumer<CableModem> consumer) {
		MibTable docsIf3CmtsCmRegStatusTable = new MibTable(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusTable,this.snmp,this.target);
		List<OID> columnOIDs = new ArrayList<OID>();
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusMacAddr);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusIPv4Addr);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusValue);
		
		Consumer<List<VariableBinding>> rowConsumer = list -> {
			VariableBinding vb_macaddr = list.get(0);
			VariableBinding vb_ipv4addr = list.get(1);
			VariableBinding vb_rsvalue = list.get(2);
			
			Long rsid = vb_macaddr.getOid().getUnsigned(vb_macaddr.getOid().size() - 2);
			MacAddress macAddress = MacAddress.fromBytes(((OctetString) vb_macaddr.getVariable()).getValue());
			byte[] ipv4addrArray = ((OctetString) vb_ipv4addr.getVariable()).getValue();
			Integer rsValue = vb_rsvalue.getVariable().toInt();
			try {
				InetAddress ipv4Address = InetAddress.getByAddress(ipv4addrArray);
				CableModem cm = new CableModem(macAddress);
				cm.setRsid(rsid);
				cm.setIpv4addr(ipv4Address);
				cm.setRegStatusValue(rsValue);
				consumer.accept(cm);
			} catch (UnknownHostException e) {
				this.logger.error("Unable to process InetAddress", e);
			}
		};
		
		docsIf3CmtsCmRegStatusTable.getRows(BULK_GET_CPE_IP_TABLE_NUM, columnOIDs, rowConsumer);
	}
	
	public void walkCmtsCmRegStatusTable(Consumer<DocsIF3CmtsCmRegStatusEntry> consumer) {
		Objects.requireNonNull(this.snmp);
		Objects.requireNonNull(this.target);
		MibTable docsIf3CmtsCmRegStatusTable = new MibTable(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusTable,this.snmp,this.target);
		List<OID> columnOIDs = new ArrayList<OID>();
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusMacAddr);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusIPv6Addr);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusIPv6LinkLocal);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusIPv4Addr);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusValue);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusMdIfIndex);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusMdCmSgId);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusRcpId);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusRccStatusId);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusRcsId);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusTcsId);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusQosVersion);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusLastRegTime);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmtsCmRegStatusAddrResolutionReqs);
		
		Consumer<List<VariableBinding>> rowConsumer = list -> {
			VariableBinding vb_macaddr = list.get(0);
			VariableBinding vb_ipv6addr = list.get(1);
			VariableBinding vb_ipv6lladdr = list.get(2);
			VariableBinding vb_ipv4addr = list.get(3);
			VariableBinding vb_rsvalue = list.get(4);
			VariableBinding vb_mdifindex = list.get(5);
			VariableBinding vb_mdcmsgid = list.get(6);
			VariableBinding vb_rcpid = list.get(7);
			VariableBinding vb_rccstatusid = list.get(8);
			VariableBinding vb_rcsid = list.get(9);
			VariableBinding vb_tcsid = list.get(10);
			VariableBinding vb_qosversion = list.get(11);
			VariableBinding vb_lastregtime = list.get(12);
			VariableBinding vb_addrResReqs = list.get(13);
			
			long rsid = vb_macaddr.getOid().lastUnsigned();
			MacAddress macAddress = MacAddress.fromBytes(((OctetString) vb_macaddr.getVariable()).getValue());

			byte[] ipv6addrArray = ((OctetString) vb_ipv6addr.getVariable()).getValue();
			byte[] ipv6lladdrArray = ((OctetString) vb_ipv6lladdr.getVariable()).getValue();
			byte[] ipv4addrArray = ((OctetString) vb_ipv4addr.getVariable()).getValue();
			
			int regStatusValue = vb_rsvalue.getVariable().toInt();
			long mdIFIndex = vb_mdifindex.getVariable().toLong();
			long mdCmSgId = vb_mdcmsgid.getVariable().toLong();
			
			byte[] rcpId = ((OctetString) vb_rcpid.getVariable()).getValue();
			
			long rccStatusId = vb_rccstatusid.getVariable().toLong();
			long rcsId = vb_rcsid.getVariable().toLong();
			long tcsId = vb_tcsid.getVariable().toLong();
			int qosVersion = vb_qosversion.getVariable().toInt();
			
			byte[] lastRegTimeArray = ((OctetString) vb_lastregtime.getVariable()).getValue();
			
			InetAddress ipv6Addr = null;
			InetAddress ipv6LLAddr = null;
			InetAddress ipv4Addr = null;
			try {
				ipv6Addr = InetAddress.getByAddress(ipv6addrArray);
				ipv6LLAddr = InetAddress.getByAddress(ipv6lladdrArray);
				ipv4Addr = InetAddress.getByAddress(ipv4addrArray);
			} catch (UnknownHostException e) {
				this.logger.error("Unable to process InetAddress", e);
			}

			Date lastRegTime = DateAndTime.toDate(lastRegTimeArray);
			
			long addrResReqs = vb_addrResReqs.getVariable().toLong();

			DocsIF3CmtsCmRegStatusEntry entry = new DocsIF3CmtsCmRegStatusEntry(
					rsid, macAddress, ipv6Addr, ipv6LLAddr, ipv4Addr,
					regStatusValue, mdIFIndex, mdCmSgId, rcpId,
					rccStatusId, rcsId, tcsId, qosVersion, lastRegTime, addrResReqs
					);
			consumer.accept(entry);
		};
		
		docsIf3CmtsCmRegStatusTable.getRows(BULK_GET_CPE_IP_TABLE_NUM, columnOIDs, rowConsumer);
	}
	
	public void walkCmStatusTable(Consumer<DocsIF3CmStatusEntry> consumer) {
		Objects.requireNonNull(this.snmp);
		Objects.requireNonNull(this.target);
		MibTable docsIf3CmStatusTable = new MibTable(DocsIF3Mib.oid_docsIf3CmStatusTable,this.snmp,this.target);
		List<OID> columnOIDs = new ArrayList<OID>();
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusValue);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusCode);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusResets);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusLostSyncs);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusInvalidMaps);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusInvalidUcds);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusInvalidRangingRsps);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusInvalidRegRsps);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusT1Timeouts);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusT2Timeouts);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusUCCsSuccesses);
		columnOIDs.add(DocsIF3Mib.oid_docsIf3CmStatusUCCFails);
		
		Consumer<List<VariableBinding>> rowConsumer = list -> {
			VariableBinding vb_value = list.get(0);
			VariableBinding vb_code = list.get(1);
			VariableBinding vb_resets = list.get(2);
			VariableBinding vb_lostSyncs = list.get(3);
			VariableBinding vb_invalidMaps = list.get(4);
			VariableBinding vb_invalidUcds = list.get(5);
			VariableBinding vb_invalidRangingRsps = list.get(6);
			VariableBinding vb_invalidRegRsps = list.get(7);
			VariableBinding vb_t1Timeouts = list.get(8);
			VariableBinding vb_t2Timeouts = list.get(9);
			VariableBinding vb_UCCsSuccesses = list.get(10);
			VariableBinding vb_UCCFails = list.get(11);
			
			long rsid = vb_value.getOid().getUnsigned(vb_value.getOid().size() - 2);
			int value = vb_value.getVariable().toInt();
			String code = vb_code.getVariable().toString();
			long resets = vb_resets.getVariable().toLong();
			long lostSyncs = vb_lostSyncs.getVariable().toLong();
			long invalidMaps = vb_invalidMaps.getVariable().toLong();
			long invalidUcds = vb_invalidUcds.getVariable().toLong();
			long invalidRangingRsps = vb_invalidRangingRsps.getVariable().toLong();
			long invalidRegRsps = vb_invalidRegRsps.getVariable().toLong();
			long t1Timeouts = vb_t1Timeouts.getVariable().toLong();
			long t2Timeouts = vb_t2Timeouts.getVariable().toLong();
			long UCCsSuccesses = vb_UCCsSuccesses.getVariable().toLong();
			long UCCFails = vb_UCCFails.getVariable().toLong();
			
			DocsIF3CmStatusEntry entry = new DocsIF3CmStatusEntry(
					rsid, value, code, resets, lostSyncs, invalidMaps,
					invalidUcds, invalidRangingRsps, invalidRegRsps,
					t1Timeouts, t2Timeouts, UCCsSuccesses, UCCFails
					);
			
			consumer.accept(entry);
		};
		
		docsIf3CmStatusTable.getRows(BULK_GET_CPE_IP_TABLE_NUM, columnOIDs, rowConsumer);
	}
}
