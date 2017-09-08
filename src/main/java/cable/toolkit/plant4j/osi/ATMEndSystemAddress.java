package cable.toolkit.plant4j.osi;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * An ATM End System Address as defined in ATM Forum's UNI Specification, Version 3.1.
 * 
 * |        IDP         |
 * |--------------------|
 * |        |    IDI    |            DSP                      |
 * |--------|-----------|-------------------------------------|
 * |   AFI  |   E.164   |   HO-DSP   |    ESI        |  SEL   |
 * | 1-byte |   8-byte  |   4-byte   |   6-byte      | 1-byte |
 * |--------|-----------|------------|---------------|--------|
 * 
 * Total Length: 20 bytes
 * AFI:          45 (ISO/IEC 8348 registered)
 * IDI:          8-byte BCD-encoded E.164 Address
 * DSP:          Contains the Internet Protocol (IP) address in the 4 byte
 *               High Order-DSP (HO-DSP), the MAC address in the 6-byte
 *               End System Identifier (ESI), and a subscriber's identifier
 *               in the 1-byte Selector (SEL).
 * 
 * @author psandiford
 *
 */
public final class ATMEndSystemAddress extends NSAPAddress {
	
	public static final byte[] DEFAULT_E164_ADDRESS = {0,0,0,0,0,0,0,0};
	public static final byte[] DEFAULT_MAC_ADDRESS = {0,0,0,0,0,0};
	public static final byte DEFAULT_SELECTOR = 0;
	
	private static final byte AFI_E164 = 0x2d;

	public final byte[] e164ATMAddress;
	
	public final InetAddress ipv4Address;
	
	public final byte[] macAddress;
	
	public final byte selector;

	public ATMEndSystemAddress(byte[] e164atmAddress, InetAddress ipv4Address, byte[] macAddress, byte selector) {
		super();
		Objects.requireNonNull(e164atmAddress);
		if (e164atmAddress.length != 8) {
			throw new IllegalArgumentException("E.164 Initial Domain Identifier must be 8 bytes");
		}
		Objects.requireNonNull(ipv4Address);
		if (ipv4Address instanceof Inet6Address) {
			throw new IllegalArgumentException("IPv6 Addresses are not supported");
		}
		Objects.requireNonNull(macAddress);
		if (macAddress.length != 6) {
			throw new IllegalArgumentException("End System Identifier MAC Address must be 6 bytes");
		}
		this.e164ATMAddress = e164atmAddress;
		this.ipv4Address = ipv4Address;
		this.macAddress = macAddress;
		this.selector = selector;
	}
	
	public ATMEndSystemAddress(InetAddress ipv4Address) {
		this(DEFAULT_E164_ADDRESS,ipv4Address,DEFAULT_MAC_ADDRESS,DEFAULT_SELECTOR);
	}
	
	@Override
	public byte[] toBytes() {
		ByteBuffer bb = ByteBuffer.allocate(NSAP_ADDR_MAX_BYTES);
		bb.put(AFI_E164);
		bb.put(this.e164ATMAddress);
		bb.put(this.ipv4Address.getAddress());
		bb.put(this.macAddress);
		bb.put(this.selector);
		return bb.array();
	}
	
}
