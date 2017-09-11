package cable.toolkit.plant4j.dsmcc.adaptation;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.*;

import cable.toolkit.plant4j.osi.ATMEndSystemAddress;

public class DSMCCUserIdAdaptationTest {

	@Test
	public void testGetAdaptationTypeId() throws UnknownHostException {
		InetAddress iaddr = InetAddress.getByName("10.253.0.1");
		ATMEndSystemAddress serverId = new ATMEndSystemAddress(iaddr);
		DSMCCAdaptationHeader userid = new DSMCCUserIdAdaptation(serverId);
		assertEquals(2,userid.getAdaptationTypeId());
	}

	@Test
	public void testToBytes() throws UnknownHostException {
		InetAddress iaddr = InetAddress.getByName("10.253.0.1");
		ATMEndSystemAddress serverId = new ATMEndSystemAddress(iaddr);
		DSMCCAdaptationHeader userid = new DSMCCUserIdAdaptation(serverId);
		byte[] useridbytes = userid.toBytes();
		assertEquals(22,useridbytes.length);
		assertEquals(0x02,useridbytes[0]);
		assertEquals(0x00,useridbytes[1]);
		assertEquals(0x2d,useridbytes[2]);
		assertEquals(0x00,useridbytes[3]);
		assertEquals(0x00,useridbytes[4]);
		assertEquals(0x00,useridbytes[5]);
		assertEquals(0x00,useridbytes[6]);
		assertEquals(0x00,useridbytes[7]);
		assertEquals(0x00,useridbytes[8]);
		assertEquals(0x00,useridbytes[9]);
		assertEquals(0x00,useridbytes[10]);
		assertEquals((byte)0x0A,useridbytes[11]);
		assertEquals((byte)0xFD,useridbytes[12]);
		assertEquals((byte)0x00,useridbytes[13]);
		assertEquals((byte)0x01,useridbytes[14]);
		assertEquals(0x00,useridbytes[15]);
		assertEquals(0x00,useridbytes[16]);
		assertEquals(0x00,useridbytes[17]);
		assertEquals(0x00,useridbytes[18]);
		assertEquals(0x00,useridbytes[19]);
		assertEquals(0x00,useridbytes[20]);
		assertEquals(0x00,useridbytes[21]);
	}

	@Test
	public void testSize() throws UnknownHostException {
		InetAddress iaddr = InetAddress.getByName("10.253.0.1");
		ATMEndSystemAddress serverId = new ATMEndSystemAddress(iaddr);
		DSMCCAdaptationHeader userid = new DSMCCUserIdAdaptation(serverId);
		assertEquals(22,userid.size());
	}

}
