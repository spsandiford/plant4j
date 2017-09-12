package cable.toolkit.plant4j.dsmcc;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.junit.*;

public class DSMCCMessageHeaderTest {

	@Test
	public void testBuilder() {
		DSMCCMessageHeader messageHeader = DSMCCMessageHeader
				.dsmccType(0x01)
				.messageId(0x01)
				.transactionId(BigInteger.valueOf(50L))
				.adaptationLength(0)
				.messageLength(100)
				.build();
		assertNotNull(messageHeader);
		assertEquals(0x01,messageHeader.dsmccType.intValue());
		assertEquals(0x01,messageHeader.messageId.intValue());
		assertEquals(50L,messageHeader.transactionId.longValue());
		assertEquals(0,messageHeader.adaptationLength.intValue());
		assertEquals(100,messageHeader.messageLength.intValue());
	}
	
	@Test
	public void testBadDsmccType() {
		List<Integer> badValues = new LinkedList<Integer>();
		
		// 0x00 is reserved
		badValues.add(0x00);

		// Values between 0x06 and 0x7F are reserved
		for (int i=0x06; i <= 0x7F; i++) {
			badValues.add(i);
		}
		
		badValues.forEach(v -> {
			try {
				DSMCCMessageHeader.dsmccType(v); 
				fail("dsmccType " + v.toString() + " should not be accepted");
			} catch (IllegalArgumentException ex) {
			}
		});
	}
	
	@Test
	public void test1ByteOutOfRangeFields() {
		List<Integer> outOfRange = new LinkedList<Integer>();
		
		// Negative values are not supported
		outOfRange.add(-1);
		outOfRange.add(-10);
		outOfRange.add(Integer.MIN_VALUE);
		
		// Values over 0xFF are not supported
		outOfRange.add(0x100);
		outOfRange.add(0x1000);
		outOfRange.add(Integer.MAX_VALUE);
		
		outOfRange.forEach(v -> {
			// dsmccType is one byte
			try {
				DSMCCMessageHeader.dsmccType(v); 
				fail("dsmccType " + v.toString() + " should not be accepted");
			} catch (IllegalArgumentException ex) {
			}
			// adaptationLength is one byte
			try {
				DSMCCMessageHeader.dsmccType(0x01).messageId(0x01).transactionId(BigInteger.ZERO).adaptationLength(v); 
				fail("adaptationLength " + v.toString() + " should not be accepted");
			} catch (IllegalArgumentException ex) {
			}
		});
	}
	
	@Test
	public void test2ByteOutOfRangeFields() {
		List<Integer> outOfRange = new LinkedList<Integer>();
		
		// Negative values are not supported
		outOfRange.add(-1);
		outOfRange.add(-10);
		outOfRange.add(Integer.MIN_VALUE);
		
		// Values over 0xFFFF are not supported
		outOfRange.add(0x10000);
		outOfRange.add(0x100000);
		outOfRange.add(Integer.MAX_VALUE);
		
		outOfRange.forEach(v -> {
			// messageId is two bytes
			try {
				DSMCCMessageHeader.dsmccType(0x01).messageId(v); 
				fail("messageId " + v.toString() + " should not be accepted");
			} catch (IllegalArgumentException ex) {
			}
			// messageLength is two bytes
			try {
				DSMCCMessageHeader.dsmccType(0x01).messageId(0x01).transactionId(BigInteger.ZERO).adaptationLength(0).messageLength(v); 
				fail("messageLength " + v.toString() + " should not be accepted");
			} catch (IllegalArgumentException ex) {
			}
		});
	}

	@Test
	public void test4ByteOutOfRangeFields() {
		List<BigInteger> outOfRange = new LinkedList<BigInteger>();
		
		// Negative values are not supported
		outOfRange.add(BigInteger.valueOf(-1L));
		outOfRange.add(BigInteger.valueOf(-10L));
		outOfRange.add(BigInteger.valueOf(Long.MIN_VALUE));
		
		// Values over 0xFFFFFFFF are not supported
		outOfRange.add(BigInteger.valueOf(0x100000000L));
		outOfRange.add(BigInteger.valueOf(0x1000000000L));
		outOfRange.add(BigInteger.valueOf(Long.MAX_VALUE));
		
		outOfRange.forEach(v -> {
			// transactionId is four bytes
			try {
				DSMCCMessageHeader.dsmccType(0x01).messageId(0x01).transactionId(v); 
				fail("transactionId " + v.toString() + " should not be accepted");
			} catch (IllegalArgumentException ex) {
			}
		});
	}

	@Test
	public void testToBytes() {
		byte[] bytes = DSMCCMessageHeader
				.dsmccType(0x01)
				.messageId(0x01)
				.transactionId(BigInteger.valueOf(50L))
				.adaptationLength(0)
				.messageLength(100)
				.build()
				.toBytes();
		ByteArrayInputStream bb = new ByteArrayInputStream(bytes);
		assertEquals(0x11,bb.read());
		assertEquals(0x01,bb.read());
		assertEquals(0x00,bb.read());
		assertEquals(0x01,bb.read());
		assertEquals(0x00,bb.read());
		assertEquals(0x00,bb.read());
		assertEquals(0x00,bb.read());
		assertEquals(0x32,bb.read());
		assertEquals(0xFF,bb.read());
		assertEquals(0x00,bb.read());
		assertEquals(0x00,bb.read());
		assertEquals(0x64,bb.read());
	}
	
	@Test
	public void fromBytes() {
		byte[] buffer = new byte[] {0x11, 0x05, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, (byte)0xff, 0x00, 0x00, 0x39};
		DSMCCMessageHeader header = null;
		try {
			header = DSMCCMessageHeader.fromBytes(buffer);
		} catch (IOException e) {
			fail("IOException while reading buffer");
		}
		assertEquals((short)5,header.dsmccType.shortValue());
		assertEquals(1,header.messageId.intValue());
		assertEquals(0L,header.transactionId.longValue());
		assertEquals(0,header.adaptationLength.intValue());
		assertEquals(57,header.messageLength.intValue());
	}
	
	@Test
	public void fromBytesLargeValues() {
		byte[] buffer = new byte[] {0x11, 0x05, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xff, (byte)0xFF, (byte)0xFF, (byte)0xFF};
		DSMCCMessageHeader header = null;
		try {
			header = DSMCCMessageHeader.fromBytes(buffer);
		} catch (IOException e) {
			fail("IOException while reading buffer");
		}
		assertEquals(5,header.dsmccType.intValue());
		assertEquals(65535,header.messageId.intValue());
		assertEquals(4294967295L,header.transactionId.longValue());
		assertEquals(255,header.adaptationLength.intValue());
		assertEquals(65535,header.messageLength.intValue());
	}

}
