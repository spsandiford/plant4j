package cable.toolkit.plant4j.docsis;

import java.net.InetAddress;
import java.util.Date;

import cable.toolkit.plant4j.ieee802.MacAddress;

/**
 * The conceptual row of docsIf3CmtsCmRegStatusTable.
 * Reference: DOCS-IF3-MIB
 */
public final class DocsIF3CmtsCmRegStatusEntry {
	
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
	 * This attribute represents the MAC address of the CM.
	 * If the CM has multiple MAC addresses, this is the MAC
	 * address associated with the MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusMacAddr}.
	 */
	public final MacAddress macAddress;
	
	/**
	 * This attribute represents the IPv6 address of the
	 * CM. If the CM has no Internet address assigned, or the
	 * Internet address is unknown, the value of this attribute
	 * is the all zeros address.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusIPv6Addr}.
	 */
	public final InetAddress ipv6Addr;
	
	/**
	 * This attribute represents the IPv6 local scope address
	 * of the CM. If the CM has no link local address assigned,
	 * or the Internet address is unknown, the value
	 * of this attribute is the all zeros address.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusIPv6LinkLocal}.
	 */
	public final InetAddress ipv6LLAddr;
	
	/**
	 * This attribute represents the IPv4 address of this
	 * CM. If the CM has no IP address assigned, or the IP address
	 * is unknown, this object returns 0.0.0.0.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusIPv4Addr}.
	 */
	public final InetAddress ipv4Addr;
	
	/**
	 * This attribute represents the current CM connectivity
	 * state.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusValue}.
	 */
	public final int regStatusValue;
	
	/**
	 * This attribute represents the interface Index of
	 * the CMTS MAC Domain where the CM is active. If the interface
	 * is unknown, the CMTS returns a value of zero.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusMdIfIndex}.
	 */
	public final long mdIFIndex;
	
	/**
	 * This attribute represents the ID of the MAC Domain
	 * CM Service Group Id (MD-CM-SG-ID) in which the CM is registered.
	 * If the ID is unknown, the CMTS returns a value
	 * of zero.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusMdCmSgId}.
	 */
	public final long mdCmSgId;
	
	/**
	 * This attribute represents the RCP-ID associated
	 * with the CM. If the RCP-ID is unknown the CMTS returns
	 * a five octet long string of zeros.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusRcpId}.
	 */
	byte[] rcpId;
	
	/**
	 * This attribute represents the RCC Id the CMTS used
	 * to configure the CM receive channel set during the registration
	 * process. If unknown, the CMTS returns the
	 * value zero.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusRccStatusId}.
	 */
	public final long rccStatusId;
	
	/**
	 * This attribute represents the Receive Channel Set
	 * (RCS) that the CM is currently using. If the RCS is unknown,
	 * the CMTS returns the value zero.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusRcsId}.
	 */
	public final long rcsId;
	
	/**
	 * This attribute represents Transmit Channel Set (TCS)
	 * the CM is currently using. If the TCS is unknown,
	 * the CMTS returns the value zero.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusTcsId}.
	 */
	public final long tcsId;
	
	/**
	 * This attribute denotes the queuing services the CM 
	 * registered, either DOCSIS 1.1 QoS or DOCSIS 1.0 CoS mode.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusQosVersion}.
	 */
	public final int qosVersion;
	
	/**
	 * This attribute represents the last time the CM registered.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusLastRegTime}.
	 */
	Date lastRegTime;
	
	/**
	 * This attribute counts represents the number of upstream
	 * packets received on the SIDs assigned to a CM that
	 * are any of the following:
	 * Upstream IPv4 ARP Requests
	 * Upstream IPv6 Neighbor Solicitation Requests
	 * (For routing CMTSs) Upstream IPv4 or IPv6 packets to
	 * unresolved destinations in locally connected downstream
	 * subnets in the HFC.
	 * Discontinuities in the value of this counter can occur
	 * at re-initialization of the managed system, and at
	 * other times as indicated by the value of
	 * ifCounterDiscontinuityTime for the associated MAC Domain
	 * interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmtsCmRegStatusAddrResolutionReqs}.
	 */
	public final long addressResolutionRequests;
	
	public DocsIF3CmtsCmRegStatusEntry(long rsid, MacAddress macAddress, InetAddress ipv6Addr, InetAddress ipv6llAddr,
			InetAddress ipv4Addr, int regStatusValue, long mdIFIndex, long mdCmSgId, byte[] rcpId, long rccStatusId,
			long rcsId, long tcsId, int qosVersion, Date lastRegTime, long addressResolutionRequests) {
		this.rsid = rsid;
		this.macAddress = macAddress;
		this.ipv6Addr = ipv6Addr;
		this.ipv6LLAddr = ipv6llAddr;
		this.ipv4Addr = ipv4Addr;
		this.regStatusValue = regStatusValue;
		this.mdIFIndex = mdIFIndex;
		this.mdCmSgId = mdCmSgId;
		this.rcpId = rcpId;
		this.rccStatusId = rccStatusId;
		this.rcsId = rcsId;
		this.tcsId = tcsId;
		this.qosVersion = qosVersion;
		this.lastRegTime = lastRegTime;
		this.addressResolutionRequests = addressResolutionRequests;
	}
	public String getRcpId() {
		if ((rcpId != null) && (rcpId.length == 5)) {
			return String.format("%02X:%02X:%02X:%02X:%02X", rcpId[0], rcpId[1], rcpId[2], rcpId[3], rcpId[4]);
		} else {
			return ("00:00:00:00:00");
		}
	}
	public Date getLastRegTime() {
		return (Date) lastRegTime.clone();
	}
	
	@Override
	public String toString() {
		return Long.toString(rsid) + "[" + macAddress.toString() + "]";
	}
}
