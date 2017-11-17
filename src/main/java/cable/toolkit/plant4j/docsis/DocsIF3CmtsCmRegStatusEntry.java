package cable.toolkit.plant4j.docsis;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;

import cable.toolkit.plant4j.equipment.CableModem;

public final class DocsIF3CmtsCmRegStatusEntry {
	public final long rsid;
	byte[] macAddress;
	public final InetAddress ipv6Addr;
	public final InetAddress ipv6LLAddr;
	public final InetAddress ipv4Addr;
	public final int regStatusValue;
	public final long mdIFIndex;
	public final long mdCmSgId;
	byte[] rcpId;
	public final long rccStatusId;
	public final long rcsId;
	public final long tcsId;
	public final int qosVersion;
	Date lastRegTime;
	public DocsIF3CmtsCmRegStatusEntry(long rsid, byte[] macAddress, InetAddress ipv6Addr, InetAddress ipv6llAddr,
			InetAddress ipv4Addr, int regStatusValue, long mdIFIndex, long mdCmSgId, byte[] rcpId, long rccStatusId,
			long rcsId, long tcsId, int qosVersion, Date lastRegTime) {
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
	}
	public byte[] getMacAddress() {
		return Arrays.copyOf(macAddress, macAddress.length);
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
		return Long.toString(rsid) + "[" + CableModem.macAddrArrayToString(macAddress) + "]";
	}
}
