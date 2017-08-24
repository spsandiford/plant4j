package cable.toolkit.plant4j.equipment;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * The interface for objects that can produce a list of Customer 
 * Premise Equipment objects.  The implementations of this interface
 * may be producing the list by asynchronous or synchronous means.
 * The CPE that are produced are not guaranteed to be on plant depending
 * on the implementation.
 * 
 * Examples:
 *  - Query a CMTS for all attached CPE
 *  - Query a back office database for all known CPE
 *  - Read a file of CPE definitions
 * 
 * @author psandiford
 *
 */
public interface CpeListProducer {
	
	/**
	 * Start producing CPE for delivery to the consumers.  All callbacks
	 * will happen in the caller thread context.
	 */
	void produceCpeList(Consumer<Cpe> consumer);
	
	/**
	 * Start producing CPE.  Deliver the Cpe objects to the consumers by submitting
	 * a job to the given thread pool for each Cpe object that is produced.
	 * @param threadPool
	 */
	void produceCpeList(ExecutorService threadPool, Consumer<Cpe> consumer);

}
