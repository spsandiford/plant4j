package cable.toolkit.plant4j.osi;

/**
 * A Network Service Access Point Address.  The address consists of
 * two domains:
 * 
 * - Initial Domain Part (IDP) which consists of two sub-parts:
 *   - 1-byte Authority and Format Identifier (AFI)
 *   - variable-length Initial Domain Identifier (IDI) which
 *     depends on the value of the AFI
 * - Domain Specific Part (DSP) which depends on the value of the IDI
 * 
 * @author psandiford
 *
 */
public abstract class NSAPAddress {
	
	public static final int NSAP_ADDR_MAX_BYTES = 20;

	public abstract byte[] toBytes();
}
