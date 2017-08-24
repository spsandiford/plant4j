package cable.toolkit.plant4j.docsis;

import org.snmp4j.smi.OID;

/**
 * docsIf3Mib MODULE-IDENTITY
 *   LAST-UPDATED    "201608040000Z" -- August 4, 2016 
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
 *      management of DOCSIS 3.0 features, primarily channel bonding,
 *      interface topology and enhanced signal quality monitoring.
 *      Copyright 1999-2016 Cable Television Laboratories, Inc.
 *      All rights reserved."
 *   ::= {  clabProjDocsis 20 }
 *
 */
public class DocsIF3Mib {

	public static final OID oid_docsIf3Mib = new OID("1.3.6.1.4.1.4491.2.1.20");
	
	/**
	 * docsIf3CmtsCmRegStatusTable OBJECT-TYPE
     *      SYNTAX      SEQUENCE OF DocsIf3CmtsCmRegStatusEntry
     *      MAX-ACCESS  not-accessible
     *      STATUS      current
     *      DESCRIPTION
     *         "This object defines attributes that represent the CM's
     *         registration status as tracked by the CMTS.
     *         Refer to the individual attribute definitions for 
     *         applicability to 3.0 and 3.1 Cable Modems."
     *      ::= { docsIf3MibObjects 3}
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusTable = new OID("1.3.6.1.4.1.4491.2.1.20.1.3");
	
	/**
	 * docsIf3CmtsCmRegStatusEntry OBJECT-TYPE
     *      SYNTAX      DocsIf3CmtsCmRegStatusEntry
     *      MAX-ACCESS  not-accessible
     *      STATUS      current
     *      DESCRIPTION
     *          "The conceptual row of docsIf3CmtsCmRegStatusTable."
     *      INDEX {
     *              docsIf3CmtsCmRegStatusId
     *            }
     *      ::= { docsIf3CmtsCmRegStatusTable 1 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusEntry = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1");

	/**
	 * docsIf3CmtsCmRegStatusId OBJECT-TYPE
     *      SYNTAX      Unsigned32 (1..4294967295)
     *      MAX-ACCESS  not-accessible
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute uniquely identifies a CM.  The CMTS
     *         must assign a single id value for each CM MAC address seen
     *         by the CMTS.  The CMTS should ensure that the association
     *         between an Id and MAC Address remains constant
     *         during CMTS uptime."
     *      ::= { docsIf3CmtsCmRegStatusEntry 1 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusId = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.1");

	/**
	 * docsIf3CmtsCmRegStatusMacAddr OBJECT-TYPE
     *      SYNTAX      MacAddress
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute represents the MAC address of the CM.
     *         If the CM has multiple MAC addresses, this is the MAC
     *         address associated with the MAC Domain interface."
     *      ::= { docsIf3CmtsCmRegStatusEntry 2 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusMacAddr = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.2");
	
	
	/**
	 * docsIf3CmtsCmRegStatusIPv6Addr OBJECT-TYPE
     *      SYNTAX      InetAddressIPv6
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute represents the IPv6 address of the
     *         CM. If the CM has no Internet address assigned, or the
     *         Internet address is unknown, the value of this attribute
     *         is the all zeros address."
     *      ::= { docsIf3CmtsCmRegStatusEntry 3 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusIPv6Addr = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.3");

	/**
	 * docsIf3CmtsCmRegStatusIPv6LinkLocal OBJECT-TYPE
     *      SYNTAX      InetAddressIPv6
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute represents the IPv6 local scope address
     *         of the CM. If the CM has no link local address assigned,
     *         or the Internet address is unknown, the value
     *         of this attribute is the all zeros address."
     *      ::= { docsIf3CmtsCmRegStatusEntry 4 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusIPv6LinkLocal = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.4");
	

	/**
	 * docsIf3CmtsCmRegStatusIPv4Addr OBJECT-TYPE
     *      SYNTAX      InetAddressIPv4
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute represents the IPv4 address of this
     *         CM. If the CM has no IP address assigned, or the IP address
     *         is unknown, this object returns 0.0.0.0."
     *      ::= { docsIf3CmtsCmRegStatusEntry 5 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusIPv4Addr = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.5");
	
	/**
	 * docsIf3CmtsCmRegStatusValue OBJECT-TYPE
     *      SYNTAX      CmtsCmRegState
     *      MAX-ACCESS  read-only
     *      STATUS      current
     *      DESCRIPTION
     *         "This attribute represents the current CM connectivity
     *         state."
     *      REFERENCE
     *          "DOCSIS 3.0 MAC and Upper Layer Protocols Interface
     *          Specification CM-SP-MULPIv3.0-I08-080522, Cable Modem
     *          Initialization and Reinitialization section."
     *      ::= { docsIf3CmtsCmRegStatusEntry 6 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusValue   = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.6");


}
