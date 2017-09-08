package cable.toolkit.plant4j.osi;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.junit.*;

public class ATMEndSystemAddressTest {

	@Test
	public void testCreateDefault() {
		InetAddress iaddr = null;
		try {
			iaddr = InetAddress.getByName("10.253.0.1");
		} catch (UnknownHostException e) {
			fail("UnknownHostException");
		}
		ATMEndSystemAddress addr = new ATMEndSystemAddress(iaddr);
		for (int i=0; i < addr.e164ATMAddress.length; i++) {
			assertEquals((byte)0x00,addr.e164ATMAddress[i]);
		}
		for (int i=0; i < addr.macAddress.length; i++) {
			assertEquals((byte)0x00,addr.macAddress[i]);
		}
		assertEquals(0,addr.selector);
		assertEquals(iaddr,addr.ipv4Address);
	}
	
	@Test
	public void testCreateDetail() {
		InetAddress iaddr = null;
		try {
			iaddr = InetAddress.getByName("255.255.255.255");
		} catch (UnknownHostException e) {
			fail("UnknownHostException");
		}
		byte[] e164address = new byte[8];
		for (int i=0; i<e164address.length; i++) {
			e164address[i] = (byte)0xFF;
		}
		byte[] macaddress = new byte[6];
		for (int i=0; i<macaddress.length; i++) {
			macaddress[i] = (byte)0xFF;
		}
		ATMEndSystemAddress addr = new ATMEndSystemAddress(e164address,iaddr,macaddress,(byte)0xFF);
		for (int i=0; i < addr.e164ATMAddress.length; i++) {
			assertEquals((byte)0xFF,addr.e164ATMAddress[i]);
		}
		for (int i=0; i < addr.macAddress.length; i++) {
			assertEquals((byte)0xFF,addr.macAddress[i]);
		}
		assertEquals(-1,addr.selector);
		assertEquals(iaddr,addr.ipv4Address);
	}
	
	@Test
	public void testBadInputs() throws UnknownHostException {
		final InetAddress ipv4address = InetAddress.getByName("10.253.0.1");
		final InetAddress ipv6address = InetAddress.getByName("2001::0");

		byte[] goode164address = new byte[8];
		for (int i=0; i<goode164address.length; i++) {
			goode164address[i] = 0;
		}
		byte[] goodmacaddress = new byte[6];
		for (int i=0; i<goodmacaddress.length; i++) {
			goodmacaddress[i] = 0;
		}
		byte goodselector = 0;
		
		try {
			byte[] bade164address = null;
			new ATMEndSystemAddress(bade164address,ipv4address,goodmacaddress,goodselector);
			fail("null e164atmAddress should not be accepted");
		} catch (Exception ex) {
		}
		
		List<Integer> badE164AddressLengths = Arrays.asList(1,2,3,4,5,6,7,9,10,11,12,13,100,1000);
		badE164AddressLengths.forEach(l -> {
			try {
				byte[] bade164address = new byte[l];
				new ATMEndSystemAddress(bade164address,ipv4address,goodmacaddress,goodselector);
				fail("e164atmAddress of length" + l + " should not be accepted");
			} catch (Exception ex) {
			}
		});
		
		try {
			new ATMEndSystemAddress(goode164address,ipv6address,goodmacaddress,goodselector);
			fail("ipv6 address should not be accepted");
		} catch (Exception ex) {
		}
		
		try {
			byte[] badmacaddress = null;
			new ATMEndSystemAddress(goode164address,ipv4address,badmacaddress,goodselector);
			fail("null macAddress should not be accepted");
		} catch (Exception ex) {
		}
		
		List<Integer> badMACAddressLengths = Arrays.asList(1,2,3,4,5,7,8,9,10,11,12,13,100,1000);
		badMACAddressLengths.forEach(l -> {
			try {
				byte[] badmacaddress = new byte[l];
				new ATMEndSystemAddress(goode164address,ipv4address,badmacaddress,goodselector);
				fail("macAddress of length" + l + " should not be accepted");
			} catch (Exception ex) {
			}
		});
	}
	
	@Test
	public void testToBytesDefault() throws UnknownHostException {
		final InetAddress ipv4address = InetAddress.getByName("255.255.255.255");
		ATMEndSystemAddress addr = new ATMEndSystemAddress(ipv4address);
		byte[] addrbytes = addr.toBytes();
		assertEquals(addrbytes.length,20);
		assertEquals(0x2d,addrbytes[0]);
		assertEquals(0x00,addrbytes[1]);
		assertEquals(0x00,addrbytes[2]);
		assertEquals(0x00,addrbytes[3]);
		assertEquals(0x00,addrbytes[4]);
		assertEquals(0x00,addrbytes[5]);
		assertEquals(0x00,addrbytes[6]);
		assertEquals(0x00,addrbytes[7]);
		assertEquals(0x00,addrbytes[8]);
		assertEquals((byte)0xFF,addrbytes[9]);
		assertEquals((byte)0xFF,addrbytes[10]);
		assertEquals((byte)0xFF,addrbytes[11]);
		assertEquals((byte)0xFF,addrbytes[12]);
		assertEquals(0x00,addrbytes[13]);
		assertEquals(0x00,addrbytes[14]);
		assertEquals(0x00,addrbytes[15]);
		assertEquals(0x00,addrbytes[16]);
		assertEquals(0x00,addrbytes[17]);
		assertEquals(0x00,addrbytes[18]);
		assertEquals(0x00,addrbytes[19]);
	}
	
	@Test
	public void testToBytesWithMAC() throws UnknownHostException {
		final InetAddress ipv4address = InetAddress.getByName("10.253.0.1");
		final byte[] macaddr = { (byte)0xe4, (byte)0x48, (byte)0xc7, (byte)0xab, (byte)0xcd, (byte)0xef };
		ATMEndSystemAddress addr = new ATMEndSystemAddress(ATMEndSystemAddress.DEFAULT_E164_ADDRESS,ipv4address,macaddr,(byte)0);
		byte[] addrbytes = addr.toBytes();
		assertEquals(addrbytes.length,20);
		assertEquals(0x2d,addrbytes[0]);
		assertEquals(0x00,addrbytes[1]);
		assertEquals(0x00,addrbytes[2]);
		assertEquals(0x00,addrbytes[3]);
		assertEquals(0x00,addrbytes[4]);
		assertEquals(0x00,addrbytes[5]);
		assertEquals(0x00,addrbytes[6]);
		assertEquals(0x00,addrbytes[7]);
		assertEquals(0x00,addrbytes[8]);
		assertEquals(0x0a,addrbytes[9]);
		assertEquals((byte)0xFD,addrbytes[10]);
		assertEquals(0x00,addrbytes[11]);
		assertEquals(0x01,addrbytes[12]);
		assertEquals((byte)0xe4,addrbytes[13]);
		assertEquals((byte)0x48,addrbytes[14]);
		assertEquals((byte)0xc7,addrbytes[15]);
		assertEquals((byte)0xab,addrbytes[16]);
		assertEquals((byte)0xcd,addrbytes[17]);
		assertEquals((byte)0xef,addrbytes[18]);
		assertEquals(0x00,addrbytes[19]);
	}

}
