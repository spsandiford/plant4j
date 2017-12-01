/**
 * 
 */
package cable.toolkit.plan4j.snmp;

import static org.junit.Assert.*;

import org.junit.Test;

import cable.toolkit.plant4j.snmp.TruthValue;

/**
 * @author psandiford
 *
 */
public class TruthValueTest {

	/**
	 * Test method for {@link cable.toolkit.plant4j.snmp.TruthValue#toBoolean(int)}.
	 */
	@Test
	public void testToBoolean() {
		assertFalse(TruthValue.toBoolean(0).isPresent());
		assertTrue(TruthValue.toBoolean(1).isPresent());
		assertTrue(TruthValue.toBoolean(2).isPresent());
		assertFalse(TruthValue.toBoolean(3).isPresent());
		
		assertTrue(TruthValue.toBoolean(1).get().booleanValue());
		assertFalse(TruthValue.toBoolean(2).get().booleanValue());
	}

}
