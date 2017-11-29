package cable.toolkit.plant4j.docsis;

/**
 * The conceptual row of docsIf3CmStatusTable.
 * An instance exist for the CM MAC Domain Interface.
 * Reference: DOCS-IF3-MIB
 */
public final class DocsIF3CmStatusEntry {
	
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
	 * This attribute denotes the current CM connectivity
	 * state. For the case of IP acquisition related states,
	 * this attribute reflects states for the current CM
	 * provisioning mode, not the other DHCP process associated
	 * with dual stack operation.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusValue}.
	 */
	public final int value;
	
	/**
	 * This attribute denotes the status code for CM as defined
	 * in the OSSI Specification. The status code consists
	 * of a single character indicating error groups,
	 * followed by a two- or three-digit number indicating
	 * the status condition, followed by a decimal. An example
	 * of a returned value could be 'T101.0'. The zero-length
	 * hex string indicates no status code yet registered.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusCode}.
	 */
	public final String code;
	
	/**
	 * This attribute denotes the number of times the CM reset
	 * or initialized this interface. Discontinuities
	 * in the value of this counter can occur at re-initialization
	 * of the managed system, and at other times as
	 * indicated by the value of ifCounterDiscontinuityTime
	 * for the CM MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusResets}.
	 */
	public final long resets;
	
	/**
	 * This attribute denotes the number of times the CM lost
	 * synchronization with the downstream channel. Discontinuities
	 * in the value of this counter can occur
	 * at re-initialization of the managed system, and at
	 * other times as indicated by the value of
	 * ifCounterDiscontinuityTime for the CM MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusLostSyncs}.
	 */
	public final long lostSyncs;
	
	/**
	 * This attribute denotes the number of times the CM received
	 * invalid MAP messages. Discontinuities in the
	 * value of this counter can occur at re-initialization
	 * of the managed system, and at other times as indicated
	 * by the value of ifCounterDiscontinuityTime for
	 * the CM MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusInvalidMaps}.
	 */
	public final long invalidMaps;
	
	/**
	 * This attribute denotes the number of times the CM received
	 * invalid UCD messages. Discontinuities in the
	 * value of this counter can occur at re-initialization
	 * of the managed system, and at other times as indicated
	 * by the value of ifCounterDiscontinuityTime for
	 * the CM MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusInvalidUcds}.
	 */
	public final long invalidUcds;
	
	/**
	 * This attribute denotes the number of times the CM received
	 * invalid ranging response messages. Discontinuities
	 * in the value of this counter can occur at re-initialization
	 * of the managed system, and at other
	 * times as indicated by the value of ifCounterDiscontinuityTime
	 * for the CM MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusInvalidRangingRsps}.
	 */
	public final long invalidRangingRsps;
	
	/**
	 * This attribute denotes the number of times the CM received
	 * invalid registration response messages. Discontinuities
	 * in the value of this counter can occur
	 * at re-initialization of the managed system, and at
	 * other times as indicated by the value of
	 * ifCounterDiscontinuityTime for the CM MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusInvalidRegRsps}.
	 */
	public final long invalidRegRsps;
	
	/**
	 * This attribute denotes the number of times counter
	 * T1 expired in the CM. Discontinuities in the value of
	 * this counter can occur at re-initialization of the
	 * managed system, and at other times as indicated by the
	 * value of ifCounterDiscontinuityTime for the CM MAC
	 * Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusT1Timeouts}.
	 */
	public final long t1Timeouts;
	
	/**
	 * This attribute denotes the number of times counter
	 * T2 expired in the CM. Discontinuities in the value of
	 * this counter can occur at re-initialization of the
	 * managed system, and at other times as indicated by the
	 * value of ifCounterDiscontinuityTime for the CM MAC
	 * Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusT2Timeouts}.
	 */
	public final long t2Timeouts;
	
	/**
	 * This attribute denotes the number of successful Upstream
	 * Channel Change transactions. Discontinuities
	 * in the value of this counter can occur at re-initialization
	 * of the managed system, and at other times
	 * as indicated by the value of ifCounterDiscontinuityTime
	 * for the CM MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusUCCsSuccesses}.
	 */
	public final long UCCsSuccesses;
	
	/**
	 * This attribute denotes the number of failed Upstream
	 * Channel Change transactions. Discontinuities
	 * in the value of this counter can occur at re-initialization
	 * of the managed system, and at other times as indicated
	 * by the value of ifCounterDiscontinuityTime
	 * for the CM MAC Domain interface.
	 * See {@link cable.toolkit.plant4j.docsis.DocsIF3Mib#oid_docsIf3CmStatusUCCFails}.
	 */
	public final long UCCFails;
	
	public DocsIF3CmStatusEntry(long rsid, int value, String code, long resets, long lostSyncs, long invalidMaps,
			long invalidUcds, long invalidRangingRsps, long invalidRegRsps, long t1Timeouts, long t2Timeouts,
			long uCCsSuccesses, long uCCFails) {
		this.rsid = rsid;
		this.value = value;
		this.code = code;
		this.resets = resets;
		this.lostSyncs = lostSyncs;
		this.invalidMaps = invalidMaps;
		this.invalidUcds = invalidUcds;
		this.invalidRangingRsps = invalidRangingRsps;
		this.invalidRegRsps = invalidRegRsps;
		this.t1Timeouts = t1Timeouts;
		this.t2Timeouts = t2Timeouts;
		UCCsSuccesses = uCCsSuccesses;
		UCCFails = uCCFails;
	}

}
