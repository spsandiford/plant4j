package cable.toolkit.plant4j.equipment;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.Snmp;
import org.snmp4j.Target;

import cable.toolkit.plant4j.snmp.SnmpAgentSystemInfo;

/**
 * A CableModem is a device that uses the DOCSIS protocol
 * to connect to a CMTS and provide MAC domain layer 2 connectivity
 * for Customer Premise Equipment (CPE).
 * 
 * @author psandiford
 *
 */
public class CableModem {
	
	private static final int CM_SNMP_RETRIES = 2;
	private static final long CM_SNMP_TIMEOUT = 3500;
	
	Target target;
	Snmp snmp;
	SnmpAgentSystemInfo systemDetails;
	Logger logger;
	
	/**
	 * This attribute uniquely identifies a CM.  The CMTS
     * must assign a single id value for each CM MAC address seen
     * by the CMTS.  The CMTS should ensure that the association
     * between an Id and MAC Address remains constant
     * during CMTS uptime.
	 */
	Long rsid;

	/**
	 * This attribute represents the MAC address of the CM.
     * If the CM has multiple MAC addresses, this is the MAC
     * address associated with the MAC Domain interface.
	 */
	byte[] macAddress;

	/** This attribute represents the IPv6 address of this CM */
	InetAddress ipv6addr;

	/** This attribute represents the IPv6 link local address of this CM */
	InetAddress ipv6LinkLocalAddr;

	/** This attribute represents the IPv4 address of this CM */
	InetAddress ipv4addr;

	/** This attribute represents the current CM connectivity state */
	Integer regStatusValue;
	
	/** This attribute represents the last time the CM registered with the CMTS */
	Date lastRegistrationTime;
	
	public CableModem(byte[] macAddress) {
		this.logger = LoggerFactory.getLogger(CableModem.class);
		this.macAddress = macAddress;
	}
	
	public void setSnmpTarget(Target target) {
		this.target = Objects.requireNonNull(target);
		this.target.setRetries(CM_SNMP_RETRIES);
		this.target.setTimeout(CM_SNMP_TIMEOUT);
	}
	
	public void setSnmp(Snmp snmp) {
		this.snmp = Objects.requireNonNull(snmp);
	}

	public byte[] getMacAddress() {
		return Arrays.copyOf(macAddress, macAddress.length);
	}
	
	public Long getRsid() {
		return rsid;
	}

	public void setRsid(Long rsid) {
		this.rsid = rsid;
	}

	public InetAddress getIpv6addr() {
		return ipv6addr;
	}

	public void setIpv6addr(InetAddress ipv6addr) {
		this.ipv6addr = ipv6addr;
	}

	public InetAddress getIpv6LinkLocalAddr() {
		return ipv6LinkLocalAddr;
	}

	public void setIpv6LinkLocalAddr(InetAddress ipv6LinkLocalAddr) {
		this.ipv6LinkLocalAddr = ipv6LinkLocalAddr;
	}

	public InetAddress getIpv4addr() {
		return ipv4addr;
	}

	public void setIpv4addr(InetAddress ipv4addr) {
		this.ipv4addr = ipv4addr;
	}

	public int getRegStatusValue() {
		return regStatusValue;
	}

	public void setRegStatusValue(Integer regStatusValue) {
		this.regStatusValue = regStatusValue;
	}

	public Date getLastRegistrationTime() {
		return lastRegistrationTime;
	}

	public void setLastRegistrationTime(Date lastRegistrationTime) {
		this.lastRegistrationTime = lastRegistrationTime;
	}

	public static String macAddrArrayToString(byte[] m) {
		Objects.requireNonNull(m);
		return String.format("%02X:%02X:%02X:%02X:%02X:%02X", m[0], m[1], m[2], m[3], m[4], m[5]);
	}
	
	@Override
	public String toString() {
		if (this.macAddress == null) {
			return super.toString();
		} else {
			return "CableModem [" + macAddrArrayToString(this.macAddress) + "]";
		}
	}

	/**
	 * Get the SNMP Agent system details for this CM
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
