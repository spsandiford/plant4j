package cable.toolkit.plant4j.equipment;

import java.net.InetAddress;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.Snmp;
import org.snmp4j.Target;

import cable.toolkit.plant4j.snmp.SnmpAgentSystemInfo;

/**
 * Customer Premise Equipment is anything at the customer location
 * that connects to the plant.  This includes both managed
 * and unmanaged devices.
 * 
 * @author psandiford
 *
 */
public class Cpe {
	private static final int CPE_SNMP_RETRIES = 2;
	private static final int CPE_SNMP_TIMEOUT = 3500;

	Target target;
	Snmp snmp;
	Integer cpe_type;
	InetAddress iaddr;
	SnmpAgentSystemInfo systemDetails;
	Logger logger;
	Long rsid;
	Integer cpeIpId;
	
	public Cpe() {
		this.logger = LoggerFactory.getLogger(Cpe.class);
	}
	
	public Cpe(Integer cpe_type, InetAddress iaddr) {
		this();
		this.cpe_type = cpe_type;
		this.iaddr = iaddr;
	}

	public Integer getCpe_type() {
		return cpe_type;
	}

	public InetAddress getIaddr() {
		return iaddr;
	}
	
	public void setSnmpTarget(Target target) {
		this.target = Objects.requireNonNull(target);
		this.target.setRetries(CPE_SNMP_RETRIES);
		this.target.setTimeout(CPE_SNMP_TIMEOUT);
	}
	
	public void setSnmp(Snmp snmp) {
		this.snmp = Objects.requireNonNull(snmp);
	}

	public Long getRsid() {
		return rsid;
	}
	public void setRsid(Long rsid) {
		this.rsid = rsid;
	}

	public Integer getCpeIpId() {
		return cpeIpId;
	}
	public void setCpeIpId(Integer cpeIpId) {
		this.cpeIpId = cpeIpId;
	}

	@Override
	public String toString() {
		if (cpe_type == null || iaddr == null) {
			return super.toString();
		} else {
			return "CPE [type=" + cpe_type.toString() + ",iaddr=" + iaddr.getHostAddress() + "]";
		}
	}
	
	/**
	 * Get the SNMP Agent system details for this CPE
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


}
