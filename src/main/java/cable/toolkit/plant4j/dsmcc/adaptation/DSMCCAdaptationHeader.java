package cable.toolkit.plant4j.dsmcc.adaptation;

/**
 * 
 * 
 * | Adaptation Type | Description                                  |
 * |:---------------:|----------------------------------------------|
 * |       0x00      | ISO/IEC 13818-6 Reserved                     |
 * |       0x01      | DSM-CC Conditional Access adaptation format  |
 * |       0x02      | DSM-CC User ID adaptation format             |
 * |       0x03      | DIImsgNumber adaptation format               |
 * |   0x04 - 0x7F   | ISO/IEC 13818-6 Reserved                     |
 * |   0x80 - 0xFF   | User Defined adaptation type                 |
 * 
 * @author psandiford
 *
 */
public abstract class DSMCCAdaptationHeader {

	public abstract int getAdaptationTypeId();
	public abstract byte[] toBytes();
	public abstract int size();

}
