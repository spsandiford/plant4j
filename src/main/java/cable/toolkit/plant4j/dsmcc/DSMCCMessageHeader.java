package cable.toolkit.plant4j.dsmcc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Objects;

/**
 * A message header for the Digital Storage Media Command and Control (DSM-CC) protocol.
 * See ISO/IEC 13818-6.
 * 
 * All fields in the header are big-endian.
 * 
 * All java number types are signed.  All fields in the header are unsigned, therefore
 * the containing types must be promoted to store all possible values.
 * 
 * | Syntax                        | Num Bytes |
 * |-------------------------------|-----------|
 * | dsmccMessageHeader() {        |           |
 * |   protocolDiscriminator       | 1         |
 * |   dsmccType                   | 1         |
 * |   messageId                   | 2         |
 * |   transactionId               | 4         |
 * |   reserved                    | 1         |
 * |   adaptationLength            | 1         |
 * |   messageLength               | 2         |
 * |   if (adaptationLength > 0) { |           |
 * |     dsmccAdaptationHeader()   |           |
 * |   }                           |           |
 * | }                             |           |
 * 
 * The protocolDiscriminator field is used to indicate that the message is a MPEG-2
 * DSM-CC message.  The value of this field shall be 0x11.
 * 
 * If the message is a DownloadDataBlock message, the transactionID is referred to as the 
 * downloadID.  See [http://www.interactivetvweb.org/tutorials/dtv_intro/dsmcc/data_carousel].
 * 
 * @author psandiford
 *
 */
public class DSMCCMessageHeader {

	public static final int DSMCC_TYPE_UN_CONFIG = 0x01;
	public static final int DSMCC_TYPE_UN_SESSION = 0x02;
	public static final int DSMCC_TYPE_DOWNLOAD = 0x03;
	public static final int DSMCC_TYPE_SDB_CCP = 0x04;
	public static final int DSMCC_TYPE_UN_PASSTHRU = 0x05;
	
	private static final int PROTOCOL_DISCRIMINATOR = 0x11;
	protected static final int MESSAGE_HEADER_SIZE = 12;  // 1+1+2+4+1+1+2
	
	Integer dsmccType;
	Integer messageId;
	BigInteger transactionId;
	Integer adaptationLength;
	Integer messageLength;
	
	private DSMCCMessageHeader() {
	}
	
	// Chained message builder interfaces
	public interface HeaderMessageID {
		HeaderTransactionID messageId(Integer messageId);
	}
	public interface HeaderTransactionID {
		HeaderAdaptationLength transactionId(BigInteger transactionId);
	}
	public interface HeaderAdaptationLength {
		HeaderMessageLength adaptationLength(Integer adaptationLength);
	}
	public interface HeaderMessageLength {
		HeaderBuild messageLength(Integer messageLength);
	}
	public interface HeaderBuild {
		DSMCCMessageHeader build();
	}

	/**
	 * Chained builder method for a DSM-CC message header.  Set the dsmccType field.
	 * 
	 * Example usage:
	 * ```java
	 * DSMCCMessageHeader messageHeader = DSMCCMessageHeader
     *          .dsmccType(0x01)
     *          .messageId(0x01)
     *          .transactionId(BigInteger.valueOf(50L))
     *          .adaptationLength(0)
     *          .messageLength(100)
     *          .build();
	 * ```
	 * @param dsmccType
	 * @return Chaining interface to continue building the header
	 */
	public static HeaderMessageID dsmccType(Integer dsmccType) {
		Builder theBuilder = new Builder();
		return theBuilder.dsmccType(dsmccType);
	}

	public static class Builder implements HeaderMessageID, HeaderTransactionID, HeaderAdaptationLength, HeaderMessageLength, HeaderBuild {
		private DSMCCMessageHeader instance;
		
		private Builder() {
			instance = new DSMCCMessageHeader();
		}
		
		/**
		 * Chained builder method for a DSM-CC message header.  Set the dsmccType field.
		 * 
		 * |  dsmccType  | Description                                               |
		 * |:-----------:|-----------------------------------------------------------|
		 * |     0x00    | ISO/IEC 13818-6 Reserved                                  |
		 * |     0x01    | ISO/IEC 13818-6 IS User-to-Network configuration message. |
		 * |     0x02    | ISO/IEC 13818-6 IS User-to-Network session message.       |
		 * |     0x03    | ISO/IEC 13818-6 IS Download message.                      |
		 * |     0x04    | ISO/IEC 13818-6 IS SDB Channel Change Protocol message.   |
		 * |     0x05    | ISO/IEC 13818-6 IS User-to- Network pass-thru message.    |
		 * | 0x06 - 0x7F | ISO/IEC 13818-6 Reserved.                                 |
		 * | 0x80 - 0xFF | User Defined message type.                                |
		 * 
		 * @param dsmccType
		 * @return Chaining interface to continue building the header
		 */
		public HeaderMessageID dsmccType(Integer dsmccType) {
			Objects.requireNonNull(dsmccType);
			if (dsmccType.compareTo(0) == 0) {
				throw new IllegalArgumentException("dsmccType 0x00 is reserved by ISO/IEC 13818-6");
			}
			if (dsmccType.compareTo(0x06) >= 0 && dsmccType.compareTo(0x7F) <= 0) {
				throw new IllegalArgumentException("dsmccType " + dsmccType.toString() + " is reserved by ISO/IEC 13818-6");
			}
			if (dsmccType.compareTo(0xFF) > 0) {
				throw new IllegalArgumentException("dsmccType " + dsmccType.toString() + " is not an acceptable value");
			}
			if (dsmccType.compareTo(0) < 0) {
				throw new IllegalArgumentException("dsmccType " + dsmccType.toString() + " is not an acceptable value");
			}
			this.instance.dsmccType = dsmccType;
			return this;
		}

		/**
		 * Chained builder method for a DSM-CC message header.  Set the messageId field
		 * @param messageId 0 <= messageId <= 65535
		 * @return Chaining interface to continue building the header
		 */
		@Override
		public HeaderTransactionID messageId(Integer messageId) {
			Objects.requireNonNull(messageId);
			if (messageId.compareTo(0) < 0) {
				throw new IllegalArgumentException("messageId " + messageId.toString() + " is not an acceptable value");
			}
			if (messageId.compareTo(0xFFFF) > 0) {
				throw new IllegalArgumentException("messageId " + messageId.toString() + " is not an acceptable value");
			}
			this.instance.messageId = messageId;
			return this;
		}

		/**
		 * Chained builder method for a DSM-CC message header.  Set the transactionId field
		 * @param transactionId 0 <= transactionId <= 4294967295
		 * @return Chaining interface to continue building the header
		 */
		@Override
		public HeaderAdaptationLength transactionId(BigInteger transactionId) {
			Objects.requireNonNull(transactionId);
			if (transactionId.compareTo(BigInteger.ZERO) < 0) {
				throw new IllegalArgumentException("transactionId " + transactionId.toString() + " is not an acceptable value");
			}
			if (transactionId.compareTo(BigInteger.valueOf(0xFFFFFFFFL)) > 0) {
				throw new IllegalArgumentException("transactionId " + transactionId.toString() + " is not an acceptable value");
			}
			this.instance.transactionId = transactionId;
			return this;
		}
		
		/**
		 * Chained builder method for a DSM-CC message header.  Set the adaptationLength field
		 * @param adaptationLength 0 <= adaptationLength <= 255
		 * @return Chaining interface to continue building the header
		 */
		@Override
		public HeaderMessageLength adaptationLength(Integer adaptationLength) {
			Objects.requireNonNull(adaptationLength);
			if (adaptationLength.compareTo(0) < 0) {
				throw new IllegalArgumentException("adaptationLength " + adaptationLength.toString() + " is not an acceptable value");
			}
			if (adaptationLength.compareTo(0xFF) > 0) {
				throw new IllegalArgumentException("adaptationLength " + adaptationLength.toString() + " is not an acceptable value");
			}
			this.instance.adaptationLength = adaptationLength;
			return this;
		}

		/**
		 * Chained builder method for a DSM-CC message header.  Set the messageLength field
		 * @param messageLength 0 <= messageLength <= 65535
		 * @return Chaining interface to continue building the header
		 */
		@Override
		public HeaderBuild messageLength(Integer messageLength) {
			Objects.requireNonNull(messageLength);
			if (messageLength.compareTo(0) < 0) {
				throw new IllegalArgumentException("messageLength " + messageLength.toString() + " is not an acceptable value");
			}
			if (messageLength.compareTo(0xFFFF) > 0) {
				throw new IllegalArgumentException("messageLength " + messageLength.toString() + " is not an acceptable value");
			}
			this.instance.messageLength = messageLength;
			return this;
		}

		@Override
		public DSMCCMessageHeader build() {
			return this.instance;
		}

	}
	
	public BigInteger getTransactionId() {
		return transactionId;
	}

	protected byte[] toBytes() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bytes.write(PROTOCOL_DISCRIMINATOR);
		bytes.write(this.dsmccType.intValue() & 0xFF);
		bytes.write((this.messageId.intValue() >> 8) & 0xFF);
		bytes.write(this.messageId.intValue() & 0xFF);
		bytes.write((int)((this.transactionId.longValue() >> 24) & 0xFF));
		bytes.write((int)((this.transactionId.longValue() >> 16) & 0xFF));
		bytes.write((int)((this.transactionId.longValue() >> 8) & 0xFF));
		bytes.write((int)(this.transactionId.longValue() & 0xFF));
		bytes.write(0xFF); // reserved
		bytes.write(this.adaptationLength.intValue() & 0xFF);
		bytes.write((this.messageLength.intValue() >> 8) & 0xFF);
		bytes.write(this.messageLength.intValue() & 0xFF);
		return bytes.toByteArray();
	}

	/**
	 * Parse a given sequence of bytes to read the DSM-CC message header.
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	protected static DSMCCMessageHeader fromBytes(byte[] bytes) throws IOException, IllegalArgumentException {
		if (bytes.length < MESSAGE_HEADER_SIZE) {
			throw new IllegalArgumentException("byte buffer is too short to contain a DSM-CC header");
		}

		InputStream is = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(is);
		
		// Protocol Discriminator
		if (dis.readByte() != PROTOCOL_DISCRIMINATOR) {
			throw new IllegalArgumentException("alleged DSM-CC header does not contain the correct protocol discriminator");
		}
		
		int dsmccType = dis.readUnsignedByte();
		int messageId = dis.readUnsignedShort();
		long transactionId = dis.readUnsignedShort();
		transactionId <<= 16;
		transactionId |= dis.readUnsignedShort();
		
		if (dis.readByte() != (byte)0xFF) {
			throw new IllegalArgumentException("alleged DSM-CC header does not contain proper reserved field value");
		}
		int adaptationLength = dis.readUnsignedByte();
		int messageLength = dis.readUnsignedShort();
		
		return DSMCCMessageHeader
				.dsmccType(Integer.valueOf(dsmccType))
				.messageId(Integer.valueOf(messageId))
				.transactionId(BigInteger.valueOf(transactionId))
				.adaptationLength(adaptationLength)
				.messageLength(Integer.valueOf(messageLength))
				.build();
	}
}
