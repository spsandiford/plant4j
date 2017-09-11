package cable.toolkit.plant4j.dsmcc.adaptation;

import java.nio.ByteBuffer;

import cable.toolkit.plant4j.osi.ATMEndSystemAddress;

/**
 * 
 * | Syntax          | Num Bytes |
 * |-----------------|-----------|
 * | dsmccUserID() { |           |
 * |   reserved      | 1         |
 * |   userID        | 20        |
 * | }               |           |
 * 
 * 
 */
public class DSMCCUserIdAdaptation extends DSMCCAdaptationHeader {
	
	public static final int ADAPTATION_TYPE_ID = 0x02;
	public static final int ADAPTATION_HEADER_SIZE = 22;  // byte+byte+byte[20]
	public final ATMEndSystemAddress serverId;

	public DSMCCUserIdAdaptation(ATMEndSystemAddress serverId) {
		this.serverId = serverId;
	}

	@Override
	public byte[] toBytes() {
		ByteBuffer bb = ByteBuffer.allocate(ADAPTATION_HEADER_SIZE);
		bb.put((byte)(ADAPTATION_TYPE_ID & 0xFF));
		bb.put((byte)0x00); // reserved
		bb.put(serverId.toBytes());
		return bb.array();
	}

	@Override
	public int size() {
		return ADAPTATION_HEADER_SIZE;
	}

	@Override
	public int getAdaptationTypeId() {
		return ADAPTATION_TYPE_ID;
	}

}
