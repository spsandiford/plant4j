package cable.toolkit.plant4j.ieee802;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A media access control address (MAC address) of a computer is a unique identifier 
 * assigned to network interfaces for communications at the data link layer of a 
 * network segment. MAC addresses are used as a network address for most IEEE 802 
 * network technologies, including Ethernet and Wi-Fi. Logically, MAC addresses are 
 * used in the media access control protocol sublayer of the OSI reference model.
 * 
 * @author psandiford
 *
 */
public final class MacAddress {
	
	private static final Logger logger = LoggerFactory.getLogger(MacAddress.class);
	protected final byte[] addressOctets;
	
	private MacAddress(byte[] addressOctets) {
		Objects.requireNonNull(addressOctets);
		if (addressOctets.length != 6) {
			throw new IllegalArgumentException("Invalid MAC address");
		}
		this.addressOctets = addressOctets;
	}
	
	/**
	 * Create a MacAddress object from the 6 octets of EUI-48 format
	 * @param address 6 byte array
	 */
	public static Optional<MacAddress> fromBytes(byte[] address) {
		MacAddress result = null;
		try {
			result = new MacAddress(address);
		} catch (IllegalArgumentException ex){
			logger.error("MAC address byte arrays must be 6 bytes");
		}
		return Optional.ofNullable(result);
	}
	
	/**
	 * Create a MacAddress object from the string representation of a MAC-48 address.
	 * Suitable formats are:
	 *  - 01-23-45-67-89-ab
	 *  - 01:23:45:67:89:ab
	 *  - 0123.4567.89ab
	 */
	public static Optional<MacAddress> fromString(String macAddr) {
		MacAddress result = null;
		Objects.requireNonNull(macAddr);
		String onlyHex = macAddr.replaceAll("[^a-fA-F0-9]", "");
		Pattern p = Pattern.compile("^([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})$");
		Matcher m = p.matcher(onlyHex);
		if (m.matches()) {
			byte[] mac_byteArray = new byte[6];
			for (int i=0; i<6; i++) {
				String octet_hex = m.group(i+1);
				int octet = Integer.parseInt(octet_hex, 16);
				mac_byteArray[i] = (byte)(octet & 0xFF);
			}
			result = new MacAddress(mac_byteArray);
		} else {
			logger.error("MAC Address " + macAddr + "is not in a proper format");
		}
		return Optional.ofNullable(result);
	}
	
	/**
	 * Packets sent to the broadcast address, all one bits, are received by all stations on a local area network.
	 * In hexadecimal the broadcast address would be FF:FF:FF:FF:FF:FF. A broadcast frame is flooded and is
	 * forwarded to and accepted by all other nodes.
	 */
	public static MacAddress broadcastMacAddress() {
		byte[] broadcastBytes = { (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF };
		return new MacAddress(broadcastBytes);
	}
	
	public byte[] toBytes() {
		return Arrays.copyOf(this.addressOctets, this.addressOctets.length);
	}
	
	@Override
	public String toString() {
		return this.toColonString();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof MacAddress) {
			try {
				MacAddress otherAddr = (MacAddress)obj;
				if (Arrays.equals(this.addressOctets, otherAddr.addressOctets)) {
					return true;
				} else {
					return false;
				}
			} catch (Exception ex) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(MacAddress.class,this.addressOctets);
	}

	public String toHyphenString() {
		return String.format("%02x-%02x-%02x-%02x-%02x-%02x",
				this.addressOctets[0],
				this.addressOctets[1],
				this.addressOctets[2],
				this.addressOctets[3],
				this.addressOctets[4],
				this.addressOctets[5]);
	}
	
	public String toColonString() {
		return String.format("%02x:%02x:%02x:%02x:%02x:%02x",
				this.addressOctets[0],
				this.addressOctets[1],
				this.addressOctets[2],
				this.addressOctets[3],
				this.addressOctets[4],
				this.addressOctets[5]);
	}
	
	public String toDotString() {
		return String.format("%02x%02x.%02x%02x.%02x%02x",
				this.addressOctets[0],
				this.addressOctets[1],
				this.addressOctets[2],
				this.addressOctets[3],
				this.addressOctets[4],
				this.addressOctets[5]);
	}

}
