package cable.toolkit.plant4j.snmp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

public class MibTable {

	OID tableOID;
	Snmp snmp;
	Target target;
	Logger logger;
	
	public MibTable(OID tableOID, Snmp snmp, Target target) {
		this.tableOID = Objects.requireNonNull(tableOID);
		this.snmp = Objects.requireNonNull(snmp);
		this.target = Objects.requireNonNull(target);
		this.logger = LoggerFactory.getLogger(MibTable.class);
	}
	
	private void closeThreadPool(ExecutorService pool) {
		if (pool != null) {
			pool.shutdown(); // Disable new tasks from being submitted
			try {
				// Wait a while for existing tasks to terminate
				if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
					pool.shutdownNow(); // Cancel currently executing tasks
					// Wait a while for tasks to respond to being cancelled
					if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
						logger.error("Worker thread pool did not terminate cleanly");
					}
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				pool.shutdownNow();
				// Preserve interrupt status
				Thread.currentThread().interrupt();
			}
		}
	}
	
	/**
	 * Traverse a set of MIB table columns using a different thread for each column.
	 * Uses SNMP BULKGET requests with a given repetition size.  Calls the given consumer
	 * every time a fully intact row of the table has been discovered.
	 * 
	 * @param maxRepetitions
	 * @param columnOIDs
	 * @param rowConsumer
	 */
	public void bulkGetRowsQueue(int maxRepetitions, List<OID> columnOIDs, Consumer<List<VariableBinding>> rowConsumer) {
		
		Objects.requireNonNull(columnOIDs);
		Objects.requireNonNull(rowConsumer);
		if (maxRepetitions < 0) {
			throw new IllegalArgumentException();
		}
		if (columnOIDs.size() < 1) {
			throw new IllegalArgumentException();
		}
		
		// All of the column OIDs need to be the same length
		int firstColumnOIDSize = columnOIDs.get(0).size();
		
		// Create MibTableColumn objects for all requested columns that are part
		// of this table
		List<MibTableColumn> mibTableColumns = columnOIDs.stream()
				.filter(oid -> oid.startsWith(this.tableOID))
				.filter(oid -> oid.size() == firstColumnOIDSize)
				.map(oid -> new MibTableColumn(oid,this.snmp,this.target))
				.collect(Collectors.toList());

		// Create a thread pool to handle each column in a separate thread
		ExecutorService workerThreadPool = Executors.newFixedThreadPool(mibTableColumns.size());
		
		// Start the thread for each column and collect the Future(s)
		List<CompletableFuture<?>> columnFutures = new ArrayList<CompletableFuture<?>>();
		List<Queue<VariableBinding>> queueList = new ArrayList<Queue<VariableBinding>>();
		mibTableColumns.forEach(column -> {
			Queue<VariableBinding> queue = new ConcurrentLinkedQueue<VariableBinding>();
			queueList.add(queue);

			Consumer<VariableBinding> c = vb -> {
				logger.trace("Adding variable binding " + vb.getOid().toString());
				queue.add(vb);
			};
			
			CompletableFuture<?> columnFuture = CompletableFuture.runAsync(() -> column.bulkGetTableColumn(maxRepetitions, c), workerThreadPool); 
			columnFutures.add(columnFuture);
		});

		boolean done = false;
		Long queueListSize = new Long(queueList.size());
		Long threadListSize = new Long(columnFutures.size());
		while (!done) {
			List<Boolean> threadCompletions = columnFutures.stream().map(f -> new Boolean(f.isDone())).collect(Collectors.toList());
			Long numFinishedThreads = threadCompletions.stream().filter(b -> b.equals(Boolean.TRUE)).collect(Collectors.counting());
			List<VariableBinding> heads = queueList.stream().map(q -> q.peek()).collect(Collectors.toList());
			Long numEmptyQueues = heads.stream().filter(vb -> vb == null).collect(Collectors.counting());
			logger.trace("Number of finished threads " + numFinishedThreads.toString());
			logger.trace("Number of empty queues " + numEmptyQueues.toString());
			
			// If all of the queues are empty and all of the threads are done, then we are done
			if (numEmptyQueues.equals(queueListSize) && numFinishedThreads.equals(threadListSize)) {
				done = true;
				break;
			}
			
			// If there are no empty queues, check for a complete row
			if (numEmptyQueues.equals(NumberUtils.LONG_ZERO)) {
				// Get the subindexes of all of the results
				List<OID> subIndexes = heads.stream()
						.map(vb -> new OID(Arrays.copyOfRange(vb.getOid().toIntArray(), firstColumnOIDSize, vb.getOid().size())))
						.collect(Collectors.toList());

				// If all of the results have the same subindex,
				// We have a complete row
				List<Integer> comparisons = subIndexes.stream().map(oid -> oid.compareTo(subIndexes.get(0))).collect(Collectors.toList());
				if (comparisons.stream().filter(c -> c.equals(NumberUtils.INTEGER_ZERO)).collect(Collectors.counting()).equals(queueListSize)) {
					rowConsumer.accept(queueList.stream().map(q -> q.poll()).collect(Collectors.toList()));
				} else {
					// We see an inconsistency in the columns
					// Remove the element from the queue that got the lowest score
					// in the comparison
					int minIndex = comparisons.indexOf(Collections.min(comparisons));
					logger.warn("Table inconsistency");
					logger.warn(heads.stream().map(vb -> vb.getOid()).collect(Collectors.toList()).toString());
					logger.warn("Removing " + heads.get(minIndex).getOid());
					queueList.get(minIndex).remove();
				}
				
			} else {
				if (numFinishedThreads.equals(NumberUtils.LONG_ZERO)) {

					// If all of the threads are still running, wait a bit
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else {
				
					// If the queue that is empty corresponds to a thread
					// that has finished, we're not going to get any more
					// complete rows
				}
				
			}
		}
		
		// Join all of the column threads
		CompletableFuture<?>[] cfs = columnFutures.toArray(new CompletableFuture<?>[columnFutures.size()]);
		CompletableFuture.allOf(cfs).join();

		this.closeThreadPool(workerThreadPool);
	}

}
