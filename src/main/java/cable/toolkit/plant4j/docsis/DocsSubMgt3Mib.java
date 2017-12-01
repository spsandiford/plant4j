package cable.toolkit.plant4j.docsis;

import org.snmp4j.smi.OID;

/**
 * docsSubmgt3Mib MODULE-IDENTITY
 *  LAST-UPDATED    "201506030000Z" -- June 3, 2015
 *   ORGANIZATION    "Cable Television Laboratories, Inc."
 *   CONTACT-INFO
 *       "
 *       Postal: Cable Television Laboratories, Inc.
 *       858 Coal Creek Circle
 *       Louisville, Colorado 80027-9750
 *       U.S.A.
 *       Phone: +1 303-661-9100
 *       Fax:   +1 303-661-9199
 *       E-mail: mibs@cablelabs.com"
 *   DESCRIPTION
 *      "This MIB module contains the management objects for the
 *      CMTS control of the IP4 and IPv6 traffic with origin and
 *      destination to CMs and/or CPEs behind the CM."
 * 
 *   ::= {  clabProjDocsis 10 }
 */
public class DocsSubMgt3Mib {

	public static final OID oid_docsSubmgt3Mib = new OID("1.3.6.1.4.1.4491.2.1.10");
	

	/**
	 * docsSubmgt3CpeIpTable OBJECT-TYPE
     *      SYNTAX      SEQUENCE OF DocsSubmgt3CpeIpEntry
     *      MAX-ACCESS  not-accessible
     *      STATUS      current
     *      DESCRIPTION
     *         "This object defines the list of IP Addresses behind
     *         the CM known by the CMTS.
     *
     *         If the Active attribute of the CpeCtrl object associated
     *         with a CM is set to 'true' and the CMTS receives an
     *         IP packet from a CM that contains a source IP address that
     *         does not match one of the CPE IP addresses associated
     *         with this CM, one of two things occurs. If the number
     *         of CPE IPs is less than the MaxCpeIp of the CpeCtrl object
     *         for that CM, the source IP address is added to this
     *         object and the packet is forwarded; otherwise, the
     *         packet is dropped."
     *      ::= { docsSubmgt3MibObjects 3}
	 */
	public static final OID oid_docsSubmgt3CpeIpTable = new OID("1.3.6.1.4.1.4491.2.1.10.1.3");
	
	/**
	 * docsSubmgt3CpeIpEntry OBJECT-TYPE
     *      SYNTAX      DocsSubmgt3CpeIpEntry
     *      MAX-ACCESS  not-accessible
     *      STATUS      current
     *      DESCRIPTION
     *          "The conceptual row of docsSubmgt3CpeIpTable."
     *      INDEX { 
     *              docsIf3CmtsCmRegStatusId, 
     *              docsSubmgt3CpeIpId
     *            }
     *      ::= { docsSubmgt3CpeIpTable 1 }
	 */
	public static final OID oid_docsSubmgt3CpeIpEntry = new OID("1.3.6.1.4.1.4491.2.1.10.1.3.1");

	/**
	 * docsSubmgt3CpeIpId OBJECT-TYPE
     *      SYNTAX      Unsigned32 (1..1023)
     *      MAX-ACCESS  not-accessible
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute represents a unique identifier for
     *         a CPE IP of the CM. An instance of this attribute exists
     *         for each CPE provisioned in the 'Subscriber Management
     *         CPE IPv4 Table' or 'Subscriber Management CPE
     *         IPv6 Table' encodings. An entry is created either through
     *         the included CPE IP addresses in the provisioning
     *         object, or CPEs learned from traffic sourced from the
     *         CM."
     *      ::= { docsSubmgt3CpeIpEntry 1 }
	 */
	public static final OID oid_docsSubmgt3CpeIpId    = new OID("1.3.6.1.4.1.4491.2.1.10.1.3.1.1");

	/**
	 * docsSubmgt3CpeIpAddrType OBJECT-TYPE
     *      SYNTAX      InetAddressType
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "The type of Internet address of the Addr attribute, such as IPv4 or IPv6."
     *      ::= { docsSubmgt3CpeIpEntry 2 }
	 */
	public static final OID oid_docsSubmgt3CpeIpAddrType = new OID("1.3.6.1.4.1.4491.2.1.10.1.3.1.2");
	
	/**
	 * docsSubmgt3CpeIpAddr OBJECT-TYPE
     *      SYNTAX      InetAddress
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute represents the IP address either set
     *         from provisioning or learned via address gleaning of the DHCP exchange 
     *         or some other means."
     *      ::= { docsSubmgt3CpeIpEntry 3 }
	 */
	public static final OID oid_docsSubmgt3CpeIpAddr = new OID("1.3.6.1.4.1.4491.2.1.10.1.3.1.3");
	
	/**
	 * docsSubmgt3CpeIpAddrPrefixLen OBJECT-TYPE
     *      SYNTAX      InetAddressPrefixLength
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute represents the prefix length associated
     *          with the IP prefix (IPv4 or IPv6) that is either set via
     *          provisioning or learned via address gleaning of the DHCP exchange
     *          or  some other means. For IPv4 CPE addresses, this attribute 
     *          generally reports the value 32 (32 bits) to indicate a unicast 
     *          IPv4 address. For IPv6 CPE addresses, this attribute represents 
     *          either a discrete IPv6 IA_NA unicast address (a value of 128 bits, 
     *          equal to /128 prefix length) or a delegated prefix (IA_PD) and 
     *          its associated length (such as 56 bits, equal to /56 prefix length)."
     *      ::= { docsSubmgt3CpeIpEntry 4 }
	 */
	public static final OID oid_docsSubmgt3CpeIpAddrPrefixLen = new OID("1.3.6.1.4.1.4491.2.1.10.1.3.1.4");
	
	/**
	 * docsSubmgt3CpeIpLearned OBJECT-TYPE
     *      SYNTAX      TruthValue
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute is set to 'true' when the IP address
     *         was learned from IP packets sent upstream rather than
     *         via the CM provisioning process."
     *      ::= { docsSubmgt3CpeIpEntry 5 }
	 */
	public static final OID oid_docsSubmgt3CpeIpLearned = new OID("1.3.6.1.4.1.4491.2.1.10.1.3.1.5");
	
	/**
	 * docsSubmgt3CpeIpType OBJECT-TYPE
	 *      SYNTAX      INTEGER {
	 *                           cpe(1),
	 *                           ps(2),
	 *                           mta(3),
	 *                           stb(4),
	 *                           tea(5),
	 *                           erouter(6),
	 *                           dva(7),
	 *                           sg(8),
	 *                           card(9)
	 *                          }
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute represents the type of CPE based on
	 *         the following classification below:
	 *                      'cpe' Regular CPE clients.
	 *                      'ps'  CableHome Portal Server (PS)
	 *                      'mta' PacketCable Multimedia Terminal Adapter (MTA)
	 *                      'stb' Digital Set-top Box (STB)
	 *                      'tea' T1 Emulation adapter (TEA)
	 *                      'erouter' Embedded Router (eRouter)
	 *                      'dva' Digital Voice Adapter (DVA)
	 *                      'sg'  Security Gateway (SG)
	 *                      'card' CableCARD"
	 *      ::= { docsSubmgt3CpeIpEntry 6 }
	 */
	public static final OID oid_docsSubmgt3CpeIpType     = new OID("1.3.6.1.4.1.4491.2.1.10.1.3.1.6");
	public static final int docsSubmgt3CpeIpType_cpe     = 1;
	public static final int docsSubmgt3CpeIpType_ps      = 2;
	public static final int docsSubmgt3CpeIpType_mta     = 3;
	public static final int docsSubmgt3CpeIpType_stb     = 4;
	public static final int docsSubmgt3CpeIpType_tea     = 5;
	public static final int docsSubmgt3CpeIpType_erouter = 6;
	public static final int docsSubmgt3CpeIpType_dva     = 7;
	public static final int docsSubmgt3CpeIpType_sg      = 8;
	public static final int docsSubmgt3CpeIpType_card    = 9;
	

	
}
