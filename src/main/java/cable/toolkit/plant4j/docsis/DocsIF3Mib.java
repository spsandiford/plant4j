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
	 * docsIf3CmStatusTable OBJECT-TYPE
	 *      SYNTAX      SEQUENCE OF DocsIf3CmStatusEntry
	 *      MAX-ACCESS  not-accessible
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This object defines attributes of the CM connectivity
	 *         status.This object provides CM connectivity status
	 *         information of the CM previously available in
	 *         the SNMP table docsIfCmStatusTable."
	 *      REFERENCE
	 *         "RFC 4546"
	 *      ::= { docsIf3MibObjects 1}
	 */
	public static final OID oid_docsIf3CmStatusTable = new OID("1.3.6.1.4.1.4491.2.1.20.1.1");
	
	/**
	 * docsIf3CmStatusEntry OBJECT-TYPE
	 *      SYNTAX      DocsIf3CmStatusEntry
	 *      MAX-ACCESS  not-accessible
	 *      STATUS      current
	 *      DESCRIPTION
	 *          "The conceptual row of docsIf3CmStatusTable.
	 *          An instance exist for the CM MAC Domain Interface."
	 *      INDEX {
	 *              ifIndex
	 *            }
	 *      ::= { docsIf3CmStatusTable 1 }
	 */
	public static final OID oid_docsIf3CmStatusEntry = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1");
	
	/**
	 * docsIf3CmStatusValue OBJECT-TYPE
	 *      SYNTAX      CmRegState
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the current CM connectivity
	 *         state. For the case of IP acquisition related states,
	 *         this attribute reflects states for the current CM
	 *         provisioning mode, not the other DHCP process associated
	 *         with dual stack operation."
	 *      REFERENCE
	 *          "DOCSIS 3.0 MAC and Upper Layer Protocols Interface
	 *          Specification CM-SP-MULPIv3.0-I08-080522, Establishing
	 *          IP Connectivity section."
	 *      ::= { docsIf3CmStatusEntry 1 }
	 */
	public static final OID oid_docsIf3CmStatusValue = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.1");
	
	/** indicates any state not described below. */
	public static final int CmRegState_other = 1;
	
	/** indicates that the CM has not started the registration process */
	public static final int CmRegState_notReady = 2;
	
	/**
	 * indicates that the CM has not initiated or completed the
	 * synchronization of the downstream physical layer
	 */
	public static final int CmRegState_notSynchronized = 3;
	
	/**
	 * indicates that the CM has completed the synchronization of
	 * the downstream physical layer
	 */
	public static final int CmRegState_phySynchronized = 4;
	
	/** indicates that the CM is attempting to determine its MD-DS-SG */
	public static final int CmRegState_dsTopologyResolutionInProgress = 21;
	
	/**
	 * indicates that the CM has completed the upstream parameters
	 * acquisition or have completed the downstream and upstream
	 * service groups resolution, wheater the CM is registering in
	 * a pre-3.0 or a 3.0 CMTS.
	 */
	public static final int CmRegState_usParametersAcquired = 5;
	
	/** indicates that the CM has initiated the ranging process. */
	public static final int CmRegState_rangingInProgress = 22;
	
	/**
	 * indicates that the CM has completed initial ranging and
	 * received a Ranging Status of success from the CMTS in the
	 * RNG-RSP message.
	 */
	public static final int CmRegState_rangingComplete = 6;
	
	/** indicates that the CM has sent an Auth Info message for EAE. */
	public static final int CmRegState_eaeInProgress = 14;
	
	/** indicates that the CM has sent a DHCPv4 DISCOVER to gain IP connectivity. */
	public static final int CmRegState_dhcpv4InProgress = 15;
	
	/** indicates that the CM has sent an DHCPv6 Solicit message. */
	public static final int CmRegState_dhcpv6InProgress = 16;
	
	/** indicates that the CM has received a DHCPv4 ACK message from the CMTS. */
	public static final int CmRegState_dhcpv4Complete = 7;
	
	/** indicates that the CM has received a DHCPv6 Reply message from the CMTS. */
	public static final int CmRegState_dhcpv6Complete = 17;
	
	/**
	 * indicates that the CM has successfully acquired time of day.
	 * If the ToD is acquired after the CM is operational, this
	 * value should not be reported.
	 */
	public static final int CmRegState_todEstablished = 8;
	
	/** indicates that the CM has successfully completed the BPI initialization process. */
	public static final int CmRegState_securityEstablished = 9;
	
	/** indicates that the CM has completed the config file download process. */
	public static final int CmRegState_configFileDownloadComplete = 10;
	
	/** indicates that the CM has sent a Registration Request (REG-REQ or REG-REQ-MP) */
	public static final int CmRegState_registrationInProgress = 18;
	
	/**
	 * indicates that the CM has successfully completed the
	 * Registration process with the CMTS.
	 */
	public static final int CmRegState_registrationComplete = 11;
	
	/** indicates that the CM has received a registration aborted notification from the CMTS */
	public static final int CmRegState_accessDenied = 13;
	
	/**
	 * indicates that the CM has completed all necessary
	 * initialization steps and is operational.
	 */
	public static final int CmRegState_operational = 12;
	
	/**
	 * indicates that the CM has started the BPI initialization
	 * process as indicated in the CM config file. If the CM already
	 * performed EAE, this state is skipped by the CM.
	 */
	public static final int CmRegState_bpiInit = 19;
	
	/**
	 * indicates that the registration process was completed, but 
	 * the network access option in the received configuration file
	 * prohibits forwarding.
	 */
	public static final int CmRegState_forwardingDisabled = 20;
	
	/**
	 * indicates that the CM is instructed to mute all channels 
	 * in the CM-CTRL-REQ message from CMTS.
	 */
	public static final int CmRegState_rfMuteAll = 23;
	
	/**
	 * docsIf3CmStatusCode OBJECT-TYPE
	 *      SYNTAX      OCTET STRING (SIZE( 0 | 5 | 6 ))
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the status code for CM as defined
	 *         in the OSSI Specification. The status code consists
	 *         of a single character indicating error groups,
	 *         followed by a two- or three-digit number indicating
	 *         the status condition, followed by a decimal. An example
	 *         of a returned value could be 'T101.0'. The zero-length
	 *         hex string indicates no status code yet registered."
	 *      REFERENCE
	 *         "DOCSIS 3.0 Operations Support System Interface
	 *          Specification CM-SP-OSSIv3.0-I07-080522, Format and Content
	 *          for Event, Syslog, and SNMP Notification Annex."
	 *      ::= { docsIf3CmStatusEntry 2 }
	 */
	public static final OID oid_docsIf3CmStatusCode = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.2");
	
	/**
	 * docsIf3CmStatusResets OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "resets"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of times the CM reset
	 *         or initialized this interface. Discontinuities
	 *         in the value of this counter can occur at re-initialization
	 *         of the managed system, and at other times as
	 *         indicated by the value of ifCounterDiscontinuityTime
	 *         for the CM MAC Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 3 }
	 */
	public static final OID oid_docsIf3CmStatusResets = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.3");
	
	/**
	 * docsIf3CmStatusLostSyncs OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "messages"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of times the CM lost
	 *         synchronization with the downstream channel. Discontinuities
	 *         in the value of this counter can occur
	 *         at re-initialization of the managed system, and at
	 *         other times as indicated by the value of
	 *         ifCounterDiscontinuityTime for the CM MAC Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 4 }
	 */
	public static final OID oid_docsIf3CmStatusLostSyncs = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.4");
	
	/**
	 * docsIf3CmStatusInvalidMaps OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "maps"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of times the CM received
	 *         invalid MAP messages. Discontinuities in the
	 *         value of this counter can occur at re-initialization
	 *         of the managed system, and at other times as indicated
	 *         by the value of ifCounterDiscontinuityTime for
	 *         the CM MAC Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 5 }
	 */
	public static final OID oid_docsIf3CmStatusInvalidMaps = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.5");
	
	/**
	 * docsIf3CmStatusInvalidUcds OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "messages"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of times the CM received
	 *         invalid UCD messages. Discontinuities in the
	 *         value of this counter can occur at re-initialization
	 *         of the managed system, and at other times as indicated
	 *         by the value of ifCounterDiscontinuityTime for
	 *         the CM MAC Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 6 }
	 */
	public static final OID oid_docsIf3CmStatusInvalidUcds = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.6");

	/**
	 * docsIf3CmStatusInvalidRangingRsps OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "messages"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of times the CM received
	 *         invalid ranging response messages. Discontinuities
	 *         in the value of this counter can occur at re-initialization
	 *         of the managed system, and at other
	 *         times as indicated by the value of ifCounterDiscontinuityTime
	 *         for the CM MAC Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 7 }
	 */
	public static final OID oid_docsIf3CmStatusInvalidRangingRsps = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.7");
	
	/**
	 * docsIf3CmStatusInvalidRegRsps OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "messages"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of times the CM received
	 *         invalid registration response messages. Discontinuities
	 *         in the value of this counter can occur
	 *         at re-initialization of the managed system, and at
	 *         other times as indicated by the value of
	 *         ifCounterDiscontinuityTime for the CM MAC Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 8 }
	 */
	public static final OID oid_docsIf3CmStatusInvalidRegRsps = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.8");
	
	/**
	 * docsIf3CmStatusT1Timeouts OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "timeouts"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of times counter
	 *         T1 expired in the CM. Discontinuities in the value of
	 *         this counter can occur at re-initialization of the
	 *         managed system, and at other times as indicated by the
	 *         value of ifCounterDiscontinuityTime for the CM MAC
	 *         Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 9 }
	 */
	public static final OID oid_docsIf3CmStatusT1Timeouts = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.9");
	
	/**
	 * docsIf3CmStatusT2Timeouts OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "timeouts"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of times counter
	 *         T2 expired in the CM. Discontinuities in the value of
	 *         this counter can occur at re-initialization of the
	 *         managed system, and at other times as indicated by the
	 *         value of ifCounterDiscontinuityTime for the CM MAC
	 *         Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 10 }
	 */
	public static final OID oid_docsIf3CmStatusT2Timeouts = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.10");
	
	/**
	 * docsIf3CmStatusUCCsSuccesses OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "attempts"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of successful Upstream
	 *         Channel Change transactions. Discontinuities
	 *         in the value of this counter can occur at re-initialization
	 *         of the managed system, and at other times
	 *         as indicated by the value of ifCounterDiscontinuityTime
	 *          for the CM MAC Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 11 }
	 */
	public static final OID oid_docsIf3CmStatusUCCsSuccesses = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.11");
	
	/**
	 * docsIf3CmStatusUCCFails OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      UNITS       "attempts"
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute denotes the number of failed Upstream
	 *         Channel Change transactions. Discontinuities
	 *         in the value of this counter can occur at re-initialization
	 *         of the managed system, and at other times as indicated
	 *         by the value of ifCounterDiscontinuityTime
	 *         for the CM MAC Domain interface."
	 *      REFERENCE
	 *         "RFC 2863."
	 *      ::= { docsIf3CmStatusEntry 12 }
	 */
	public static final OID oid_docsIf3CmStatusUCCFails = new OID("1.3.6.1.4.1.4491.2.1.20.1.1.1.12");
	
	
	
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
	 *      REFERENCE
	 *          "DOCSIS 3.0 MAC and Upper Layer Protocols Interface
	 *          Specification CM-SP-MULPIv3.0-I08-080522, Cable Modem -
	 *          CMTS Interaction section."
	 */
	
	/** indicates any state not described below. */
	public static final int CmtsCmRegState_other = 1;
	
	/**
	 * indicates that the CMTS has received an Initial Ranging
	 * Request message from the CM, and the ranging process is not yet
	 * complete.
	 */
	public static final int CmtsCmRegState_initialRanging = 2;
	
	/**
	 * indicates that the CM has completed initial ranging and the
	 * CMTS sends a Ranging Status of success in the RNG-RSP.
	 */
	public static final int CmtsCmRegState_rangingAutoAdjComplete = 4;
	
	/** indicates that the CMTS has received an Auth Info message for EAE from the CM. */
	public static final int CmtsCmRegState_startEae = 10;

	/** indicates that the CMTS has received a DHCPv4 DISCOVER message from the CM. */
	public static final int CmtsCmRegState_startDhcpv4 = 11;
	
	/** indicates that the CMTS has received a DHCPv6 Solicit message from the CM. */
	public static final int CmtsCmRegState_startDhcpv6 = 12;
	
	/** indicates that the CMTS has sent a DHCPv4 ACK message to the CM. */
	public static final int CmtsCmRegState_dhcpv4Complete = 5;
	
	/** indicates that the CMTS has sent a DHCPv6 Reply message to the CM. */
	public static final int CmtsCmRegState_dhcpv6Complete = 13;
	
	/**
	 * indicates that the CM has started the config file download.
	 * If the TFTP Proxy feature is not enabled, the CMTS may not
	 * report this state.
	 */
	public static final int CmtsCmRegState_startConfigFileDownload = 14;
	
	/**
	 * indicates that the CM has completed the config file download
	 * process.  If the TFTP Proxy feature is not enabled, the CMTS
	 * is not required to report this state.
	 */
	public static final int CmtsCmRegState_configFileDownloadComplete = 15;
	
	/**
	 * indicates that the CMTS has received a Registration
	 * Request (REG-REQ or REG-REQ-MP) from the CM.
	 */
	public static final int CmtsCmRegState_startRegistration = 16;
	
	/**
	 * indicates that the CMTS has received a Registration Acknowledge
	 * (REG-ACK) with a confirmation code of okay/success.
	 */
	public static final int CmtsCmRegState_registrationComplete = 6;
	
	/**
	 * indicates that the CM has completed all necessary
	 * initialization steps and is operational.
	 */
	public static final int CmtsCmRegState_operational = 8;
	
	/**
	 * indicates that the CMTS has received an Auth Info or Auth
	 * Request message as part of BPI Initialization.
	 */
	public static final int CmtsCmRegState_bpiInit = 9;
	
	/**
	 * indicates that the registration process was completed, but 
	 * the network access option in the received configuration 
	 * file prohibits forwarding.
	 */
	public static final int CmtsCmRegState_forwardingDisabled = 17;
	
	/**
	 * indicates that the CM is instructed to mute all channels 
	 * in the CM-CTRL-REQ message from CMTS."
	 */
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
	
	/**
	 * docsIf3CmtsCmRegStatusAddrResolutionReqs OBJECT-TYPE
	 *      SYNTAX      Counter32
	 *      MAX-ACCESS  read-only
	 *      STATUS      current
	 *      DESCRIPTION
	 *         "This attribute counts represents the number of upstream
	 *         packets received on the SIDs assigned to a CM that
	 *         are any of the following:
	 *         Upstream IPv4 ARP Requests
	 *         Upstream IPv6 Neighbor Solicitation Requests
	 *         (For routing CMTSs) Upstream IPv4 or IPv6 packets to
	 *         unresolved destinations in locally connected downstream
	 *         subnets in the HFC.
	 *         Discontinuities in the value of this counter can occur
	 *         at re-initialization of the managed system, and at
	 *         other times as indicated by the value of
	 *         ifCounterDiscontinuityTime for the associated MAC Domain
	 *         interface."
	 *      REFERENCE
	 *          "DOCSIS 3.0 Security Specification CM-SP-MULPIv3.0-I08-080522,
	 *          Secure Provisioning section.
	 *          RFC 2863."
	 *      ::= { docsIf3CmtsCmRegStatusEntry 15 }

	 */
	public static final OID oid_docsIf3CmtsCmRegStatusAddrResolutionReqs = new OID("1.3.6.1.4.1.4491.2.1.20.1.3.1.15");
	
}
