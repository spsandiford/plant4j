package cable.toolkit.plant4j.docsis;

public final class DocsIF3CmStatusEntry {
	public final long rsid;
	public final int value;
	public final String code;
	public final long resets;
	public final long lostSyncs;
	public final long invalidMaps;
	public final long invalidUcds;
	public final long invalidRangingRsps;
	public final long invalidRegRsps;
	public final long t1Timeouts;
	public final long t2Timeouts;
	public final long UCCsSuccesses;
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
