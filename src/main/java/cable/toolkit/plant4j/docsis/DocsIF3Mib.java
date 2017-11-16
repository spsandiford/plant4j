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

	/*
	 * CmtsCmRegState ::= TEXTUAL-CONVENTION
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This data type defines the CM connectivity states as reported
	 *         by the CMTS.
	 *         The enumerated values associated with the CmtsCmRegState are:
	 * 
	 *         'other'
	 *          indicates any state not described below.
	 *         'initialRanging'
	 *          indicates that the CMTS has received an Initial Ranging
	 *          Request message from the CM, and the ranging process is not yet
	 *          complete.
	 *         'rangingAutoAdjComplete'
	 *          indicates that the CM has completed initial ranging and the
	 *          CMTS sends  a Ranging Status of success in the RNG-RSP.
	 *         'startEae'
	 *          indicates that the CMTS has received an Auth Info message for
	 *          EAE from the CM.
	 *         'startDhcpv4'
	 *          indicates that the CMTS has received a DHCPv4 DISCOVER message
	 *          from the CM.
	 *         'startDhcpv6'
	 *          indicates that the CMTS has received a DHCPv6 Solicit message
	 *          from the CM.
	 *         'dhcpv4Complete'
	 *          indicates that the CMTS has sent a DHCPv4 ACK message to the
	 *          CM.
	 *         'dhcpv6Complete'
	 *          indicates that the CMTS has sent a DHCPv6 Reply message to the
	 *          CM.
	 *         'startConfigFileDownload'
	 *          indicates that the CM has started the config file download.
	 *          If the TFTP Proxy feature is not enabled, the CMTS may not
	 *          report this state.
	 *         'configFileDownloadComplete'
	 *          indicates that the CM has completed the config file download
	 *          process.  If the TFTP Proxy feature is not enabled, the CMTS
	 *          is not required to report this state.
	 *         'startRegistration'
	 *          indicates that the CMTS has received a Registration
	 *          Request (REG-REQ or REG-REQ-MP) from the CM.
	 *         'registrationComplete'
	 *          indicates that the CMTS has received a Registration Acknowledge
	 *          (REG-ACK) with a confirmation code of okay/success.
	 *         'operational'
	 *          indicates that the CM has completed all necessary
	 *          initialization steps and is operational.
	 *         'bpiInit'
	 *          indicates that the CMTS has received an Auth Info or Auth
	 *          Request message as part of BPI Initialization.
	 *         'forwardingDisabled'
	 *          indicates that the registration process was completed, but 
	 *          the network access option in the received configuration 
	 *          file prohibits forwarding.
	 *         'rfMuteAll'
	 *          indicates that the CM is instructed to mute all channels 
	 *          in the CM-CTRL-REQ message from CMTS."
	 *      REFERENCE
	 *          "DOCSIS 3.0 MAC and Upper Layer Protocols Interface
	 *          Specification CM-SP-MULPIv3.0-I08-080522, Cable Modem -
	 *          CMTS Interaction section."
	 */
	public static final int CmtsCmRegState_other = 1;
	public static final int CmtsCmRegState_initialRanging = 2;
	public static final int CmtsCmRegState_rangingAutoAdjComplete = 4;
	public static final int CmtsCmRegState_startEae = 10;
	public static final int CmtsCmRegState_startDhcpv4 = 11;
	public static final int CmtsCmRegState_startDhcpv6 = 12;
	public static final int CmtsCmRegState_dhcpv4Complete = 5;
	public static final int CmtsCmRegState_dhcpv6Complete = 13;
	public static final int CmtsCmRegState_startConfigFileDownload = 14;
	public static final int CmtsCmRegState_configFileDownloadComplete = 15;
	public static final int CmtsCmRegState_startRegistration = 16;
	public static final int CmtsCmRegState_registrationComplete = 6;
	public static final int CmtsCmRegState_operational = 8;
	public static final int CmtsCmRegState_bpiInit = 9;
	public static final int CmtsCmRegState_forwardingDisabled = 17;
	public static final int CmtsCmRegState_rfMuteAll = 18;

	/**
	 * docsIf3CmtsCmRegStatusMdIfIndex OBJECT-TYPE
	 *      SYNTAX      InterfaceIndexOrZero
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute represents the interface Index of
	 *         the CMTS MAC Domain where the CM is active. If the interface
	 *         is unknown, the CMTS returns a value of zero."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 7 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusMdIfIndex = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.7");

	/**
	 * docsIf3CmtsCmRegStatusMdCmSgId OBJECT-TYPE
	 *      SYNTAX      Unsigned32
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute represents the ID of the MAC Domain
	 *         CM Service Group Id (MD-CM-SG-ID) in which the CM is registered.
	 *         If the ID is unknown, the CMTS returns a value
	 *         of zero."
	 *      REFERENCE
	 *          "DOCSIS 3.0 MAC and Upper Layer Protocols Interface
	 *          Specification CM-SP-MULPIv3.0-I08-080522, Cable Modem
	 *          Service Group (CM-SG) section."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 8 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusMdCmSgId = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.8");

	/**
	 * docsIf3CmtsCmRegStatusRcpId OBJECT-TYPE
	 *      SYNTAX      RcpId
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute represents the RCP-ID associated
	 *         with the CM. If the RCP-ID is unknown the CMTS returns
	 *         a five octet long string of zeros."
	 *      REFERENCE
	 *          "DOCSIS 3.0 MAC and Upper Layer Protocols Interface
	 *          Specification CM-SP-MULPIv3.0-I08-080522, RCP-ID
	 *          section in the Common Radio Frequency Interface
	 *          Encodings Annex."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 9 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusRcpId = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.9");

	/**
	 * docsIf3CmtsCmRegStatusRccStatusId OBJECT-TYPE
	 *      SYNTAX      Unsigned32
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute represents the RCC Id the CMTS used
	 *         to configure the CM receive channel set during the registration
	 *         process. If unknown, the CMTS returns the
	 *         value zero."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 10 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusRccStatusId = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.10");

	/**
	 * docsIf3CmtsCmRegStatusRcsId OBJECT-TYPE
	 *      SYNTAX      ChSetId
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute represents the Receive Channel Set
	 *         (RCS) that the CM is currently using. If the RCS is unknown,
	 *         the CMTS returns the value zero."
	 *      REFERENCE
	 *          "DOCSIS 3.0 MAC and Upper Layer Protocols Interface
	 *          Specification CM-SP-MULPIv3.0-I08-080522, Cable Modem
	 *          Physical Receive Channel Configuration section and the
	 *          Receive Channels section in the Common Radio Frequency
	 *          Interface Encodings Annex."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 11 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusRcsId = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.11");

	/**
	 * docsIf3CmtsCmRegStatusTcsId OBJECT-TYPE
	 *      SYNTAX      ChSetId
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute represents Transmit Channel Set (TCS)
	 *         the CM is currently using. If the TCS is unknown,
	 *         the CMTS returns the value zero."
	 *      REFERENCE
	 *          "DOCSIS 3.0 MAC and Upper Layer Protocols Interface
	 *          Specification CM-SP-MULPIv3.0-I08-080522, Changes to
	 *          the Transmit Channel Set section."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 12 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusTcsId = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.12");

	/**
	 * docsIf3CmtsCmRegStatusQosVersion OBJECT-TYPE
	 *      SYNTAX      DocsisQosVersion
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the queuing services the CM
	 *         registered, either DOCSIS 1.1 QoS or DOCSIS 1.0 CoS mode."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 13 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusQosVersion = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.13");

	/**
	 * docsIf3CmtsCmRegStatusLastRegTime OBJECT-TYPE
	 *      SYNTAX      DateAndTime
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute represents the last time the CM registered."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 14 }
	 */
	public static final OID oid_docsIf3CmtsCmRegStatusLastRegTime = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.14");
}
