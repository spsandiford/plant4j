package cable.toolkit.plant4j.ieee802;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MacAddressTest {

	final byte[] goodMac1Bytes = { (byte)0xe4, (byte)0x48, (byte)0xc7, (byte)0xab, (byte)0xcd, (byte)0xef };
	final byte[] goodMac2Bytes = { (byte)0x11, (byte)0x22, (byte)0x33, (byte)0x44, (byte)0x55, (byte)0x66 };
	final byte[] broadcastBytes = { (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF };
	
	@Test
	public void testFromBytesGood() {
		MacAddress macaddr = MacAddress.fromBytes(goodMac1Bytes);
		assertTrue(Arrays.equals(goodMac1Bytes, macaddr.addressOctets));
	}
	
	@Test
	public void testFromBytesBad() {
		try {
			byte[] badmacaddress = null;
			MacAddress.fromBytes(badmacaddress);
			fail("null MAC Address should not be accepted");
		} catch (Exception ex) {
		}
		
		List<Integer> badMACAddressLengths = Arrays.asList(1,2,3,4,5,7,8,9,10,11,12,13,100,1000);
		badMACAddressLengths.forEach(l -> {
			try {
				byte[] badmacaddress = new byte[l];
				MacAddress.fromBytes(badmacaddress);
				fail("MAC Address of length" + l + " should not be accepted");
			} catch (Exception ex) {
			}
		});
	}

	@Test
	public void testFromStringGoodColon() {
		MacAddress macaddr = MacAddress.fromString("E4:48:C7:Ab:cd:ef");
		assertTrue(Arrays.equals(goodMac1Bytes, macaddr.addressOctets));
	}

	@Test
	public void testFromStringGoodHyphen() {
		MacAddress macaddr = MacAddress.fromString("e4-48-C7-aB-cd-Ef");
		assertTrue(Arrays.equals(goodMac1Bytes, macaddr.addressOctets));
	}

	@Test
	public void testFromStringGoodDots() {
		MacAddress macaddr = MacAddress.fromString("E448.c7aB.cdeF");
		assertTrue(Arrays.equals(goodMac1Bytes, macaddr.addressOctets));
	}

//	@Test
//	public void testFromStringBad() {
//		List<String> badValues = new ArrayList<String>();
//		badValues.add("");
//		badValues.add("10.253.0.1");
//		badValues.add("gg:22:aa:11:zz:99");
//		badValues.add("11:22:33:44");
//		badValues.add("1122.3344.5566.7788");
//		badValues.add("11-22:33:44.5566");
//		
//		badValues.forEach(v -> {
//			try {
//				MacAddress.fromString(v);
//				fail("MacAddress.fromString should not accept " + v);
//			} catch (Exception ex) {
//			}
//		});
//	}

//	@Test
//	public void testToBytes() {
//		MacAddress macaddr = MacAddress.fromString("E4:48:C7:Ab:cd:ef");
//		byte[] bytes = macaddr.toBytes();
//		assertTrue(Arrays.equals(goodMac1Bytes, bytes));
//	}

	@Test
	public void testToHyphenString() {
		MacAddress macaddr = MacAddress.fromBytes(goodMac1Bytes);
		assertEquals("e4-48-c7-ab-cd-ef",macaddr.toHyphenString());
	}

	@Test
	public void testToColonString() {
		MacAddress macaddr = MacAddress.fromBytes(goodMac1Bytes);
		assertEquals("e4:48:c7:ab:cd:ef",macaddr.toColonString());
	}

	@Test
	public void testToDotString() {
		MacAddress macaddr = MacAddress.fromBytes(goodMac1Bytes);
		assertEquals("e448.c7ab.cdef",macaddr.toDotString());
	}
	
	@Test
	public void testBroadcast() {
		MacAddress macaddr = MacAddress.broadcastMacAddress();
		assertTrue(Arrays.equals(broadcastBytes, macaddr.addressOctets));
	}
	
	@Test
	public void testSideEffect() {
		MacAddress macaddr = MacAddress.broadcastMacAddress();
		byte[] bytes = macaddr.toBytes();
		for (int i=0; i<bytes.length; i++) {
			bytes[i] = 0x00;
		}
		bytes = macaddr.toBytes();
		assertTrue(Arrays.equals(broadcastBytes, bytes));
	}
	
	@Test
	public void testEquals() {
		MacAddress m1 = MacAddress.fromBytes(goodMac1Bytes);
		MacAddress m2 = MacAddress.fromBytes(goodMac1Bytes);
		MacAddress m3 = MacAddress.fromBytes(goodMac2Bytes);
		assertTrue(m1.equals(m2));
		assertFalse(m1.equals(m3));
		assertTrue(m2.equals(m1));
		assertFalse(m2.equals(m3));
	}

}
