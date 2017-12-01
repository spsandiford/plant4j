package cable.toolkit.plant4j.snmp;

import java.util.Optional;

/**
 * TruthValue ::= TEXTUAL-CONVENTION
 *     STATUS       current
 *     DESCRIPTION
 *             "Represents a boolean value."
 *     SYNTAX       INTEGER { true(1), false(2) }
 *
 */
public class TruthValue {

	/**
	 * Return an optional Boolean object given an integer
	 */
	public static Optional<Boolean> toBoolean(int value) {
		Boolean result = null;
		if (value == 1) {
			result = Boolean.valueOf(true);
		} else if (value == 2) {
			result = Boolean.valueOf(false);
		}
		return Optional.ofNullable(result);
	}
}
