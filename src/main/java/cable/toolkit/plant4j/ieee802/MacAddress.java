package cable.toolkit.plant4j.ieee802;

import java.util.Arrays;
import java.util.Objects;

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
	
	protected final byte[] addressOctets;
	
	private MacAddress(byte[] addressOctets) {
		Objects.requireNonNull(addressOctets);
		if (addressOctets.length != 6) {
			throw new IllegalArgumentException("Invalid MAC address");
		}
		this.addressOctets = addressOctets;
	}
	
	/**
	 * Create a MAC address from the 6 octets of EUI-48 format
	 * @param address 6 byte array
	 * @return
	 */
	public static MacAddress fromBytes(byte[] address) {
		return new MacAddress(address);
	}
	
	public static MacAddress fromString(String macAddr) {
		return null;
	}
	
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
