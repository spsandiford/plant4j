package cable.toolkit.plant4j.docsis;

import java.net.InetAddress;

public class DocsSubMgt3CpeIpEntry {

	/**
	 * This attribute uniquely identifies a CM.  The CMTS
	 * must assign a single id value for each CM MAC address seen
	 * by the CMTS.  The CMTS should ensure that the association
	 * between an Id and MAC Address remains constant
	 * during CMTS uptime.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusId}.
	 */
	public final long rsid;
	
	/**
	 * This attribute represents a unique identifier for 
	 * a CPE IP of the CM. An instance of this attribute exists 
	 * for each CPE provisioned in the 'Subscriber Management 
	 * CPE IPv4 Table' or 'Subscriber Management CPE 
	 * IPv6 Table' encodings. An entry is created either through 
	 * the included CPE IP addresses in the provisioning 
	 * object, or CPEs learned from traffic sourced from the 
	 * CM.
	 * See {@link cable.toolkit.plant4j.docsis.DocsSubMgt3Mib#oid_docsSubmgt3CpeIpId}.
	 */
	public final long cpeIpId;
	
	/**
	 * This attribute represents the IP address either set 
	 * from provisioning or learned via address gleaning 
	 * or other forwarding means.
	 * See {@link cable.toolkit.plant4j.docsis.DocsSubMgt3Mib#oid_docsSubmgt3CpeIpAddr}.
	 */
	public final InetAddress cpeIpAddr;
	
	/**
	 * This attribute represents the prefix length associated with 
	 * the IP subnet prefix either set from provisioning or learned 
	 * via address gleaning or other forwarding means. For IPv4 CPE 
	 * addresses this attribute generally reports the value 32  
	 * (32 bits) to indicate a unicast IPv4 address. For IPv6, this 
	 * attribute represents either an IPv6 unicast address 
	 * (128 bits, equal to /128 prefix length) or a subnet prefix  
	 * length (for example 56 bits, equal to /56 prefix length).
	 * See {@link cable.toolkit.plant4j.docsis.DocsSubMgt3Mib#oid_docsSubmgt3CpeIpAddrPrefixLen}.
	 */
	public final long cpeIpAddrPrefixLength;
	
	/**
	 * This attribute is set to 'true' when the IP address 
	 * was learned from IP packets sent upstream rather than 
	 * via the CM provisioning process.
	 * See {@link cable.toolkit.plant4j.docsis.DocsSubMgt3Mib#oid_docsSubmgt3CpeIpLearned}.
	 */
	public final boolean cpeIpAddrLearned;
	
	/**
	 * This attribute represents the type of CPE based on 
	 * the following classification below: 
	 *              'cpe' Regular CPE clients. 
	 *              'ps'  CableHome Portal Server (PS) 
	 *              'mta' PacketCable Multimedia Terminal Adapter (MTA) 
	 *              'stb' Digital Set-top Box (STB). 
	 *              'tea' T1 Emulation adapter (TEA) 
	 *              'erouter' Embedded Router (eRouter)
	 * See {@link cable.toolkit.plant4j.docsis.DocsSubMgt3Mib#oid_docsSubmgt3CpeIpType}.
	 */
	public final int cpeIpType;

	public DocsSubMgt3CpeIpEntry(long rsid, long cpeIpId, InetAddress cpeIpAddr, long cpeIpAddrPrefixLength,
			boolean cpeIpAddrLearned, int cpeIpType) {
		super();
		this.rsid = rsid;
		this.cpeIpId = cpeIpId;
		this.cpeIpAddr = cpeIpAddr;
		this.cpeIpAddrPrefixLength = cpeIpAddrPrefixLength;
		this.cpeIpAddrLearned = cpeIpAddrLearned;
		this.cpeIpType = cpeIpType;
	}

}
