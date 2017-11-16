package cable.toolkit.plant4j.equipment;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * The interface for objects that can produce a list of Cable 
 * Modem objects.  The implementations of this interface
 * may be producing the list by asynchronous or synchronous means.
 * The Cable Modems that are produced are not guaranteed to be on
 * plant depending on the implementation.
 * 
 * Examples:
 *  - Query a CMTS for all attached Cable Modems
 *  - Query a back office database for all known Cable Modems
 *  - Read a file of Cable Modem definitions
 * 
 * @author psandiford
 *
 */
public interface CableModemListProducer {

	/**
	 * Start producing Cable Modem objects for delivery to the consumers.  All callbacks
	 * will happen in the caller thread context.
	 */
	void produceCableModemsList(Consumer<CableModem> consumer);
	
	/**
	 * Start producing Cable Modem objects.  Deliver the objects to the consumers by submitting
	 * a job to the given thread pool for each object that is produced.
	 * @param threadPool
	 */
	void produceCableModemsList(ExecutorService threadPool, Consumer<CableModem> consumer);
}
