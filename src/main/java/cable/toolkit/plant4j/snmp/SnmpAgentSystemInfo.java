package cable.toolkit.plant4j.snmp;

import java.io.IOException;
import java.util.Objects;

import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.VariableBinding;

/**
 * General information about an SNMP agent
 * 
 * Includes:
 *  - sysDescr (1.3.6.1.2.1.1.1)
 *  - sysUpTime (1.3.6.1.2.1.1.3)
 *  - sysContact (1.3.6.1.2.1.1.4)
 *  - sysName (1.3.6.1.2.1.1.5)
 *  - sysLocation (1.3.6.1.2.1.1.6)
 * 
 * @author psandiford
 *
 */
public class SnmpAgentSystemInfo {
	public final String sysDescr;
	public final String sysUpTime;
	public final String sysContact;
	public final String sysName;
	public final String sysLocation;
	
	public SnmpAgentSystemInfo(String sysDescr,
			String sysUpTime,
			String sysContact,
			String sysName,
			String sysLocation) {
		super();
		this.sysDescr = Objects.requireNonNull(sysDescr);
		this.sysUpTime = Objects.requireNonNull(sysUpTime);
		this.sysContact = Objects.requireNonNull(sysContact);
		this.sysName = Objects.requireNonNull(sysName);
		this.sysLocation = Objects.requireNonNull(sysLocation);
	}

	@Override
	public String toString() {
		return this.sysName + " | " + this.sysLocation;
	}
	
	/**
	 *  Use SNMP to get the system details for a piece of equipment.
	 *  
	 * @param snmp An initialized Snmp4j Snmp object
	 * @param target An initialized Snmp4j Target object
	 * @return A populated SnmpAgentSystemInfo object or null if any
	 * 		error was encountered
	 */
	public static SnmpAgentSystemInfo getSystemInfo(Snmp snmp, Target target) throws Exception {
		Objects.requireNonNull(snmp);
		Objects.requireNonNull(target);
		PDU getAgentDetails = new PDU();
		getAgentDetails.add(new VariableBinding(SnmpConstants.sysDescr));
		getAgentDetails.add(new VariableBinding(SnmpConstants.sysUpTime));
		getAgentDetails.add(new VariableBinding(SnmpConstants.sysContact));
		getAgentDetails.add(new VariableBinding(SnmpConstants.sysName));
		getAgentDetails.add(new VariableBinding(SnmpConstants.sysLocation));
		getAgentDetails.setType(PDU.GET);
		
		ResponseEvent responseEvent = snmp.send(getAgentDetails, target);
		if (responseEvent == null) {
			throw new IOException("No response from " + target.getAddress().toString());
		}
		PDU responsePDU = responseEvent.getResponse();
		if (responsePDU == null) {
			throw new IOException("Empty response from " + target.getAddress().toString());
		}
		String sysDescr = responsePDU.getVariable(SnmpConstants.sysDescr).toString();
		String sysUpTime = responsePDU.getVariable(SnmpConstants.sysUpTime).toString();
		String sysContact = responsePDU.getVariable(SnmpConstants.sysContact).toString();
		String sysName = responsePDU.getVariable(SnmpConstants.sysName).toString();
		String sysLocation = responsePDU.getVariable(SnmpConstants.sysLocation).toString();
		return new SnmpAgentSystemInfo(sysDescr,sysUpTime,sysContact,sysName,sysLocation);
	}

}
