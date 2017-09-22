package cable.toolkit.plant4j.snmp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
	
	class ColumnRetrieverThread {
		CompletableFuture<?> threadFuture;
		final Queue<VariableBinding> queue;
		final Lock queueLock;
		final Condition queueNotEmpty;
		
		ColumnRetrieverThread() {
			this.queueLock = new ReentrantLock();
			this.queueNotEmpty = this.queueLock.newCondition();
			this.queue = new ConcurrentLinkedQueue<VariableBinding>();
		}
		public CompletableFuture<?> getThreadFuture() {
			return threadFuture;
		}
		public void setThreadFuture(CompletableFuture<?> threadFuture) {
			this.threadFuture = threadFuture;
		}
		
		public void start(Runnable runnable, Executor executor) {
			assert(this.threadFuture == null);
			this.threadFuture = CompletableFuture.runAsync(runnable, executor);
		}
		
		/**
		 * Atomically add a variable binding to the queue
		 * @param vb
		 */
		public void enqueue(VariableBinding vb) {
			this.queueLock.lock();
			try {
				this.queue.add(vb);
				this.queueNotEmpty.signal();
			} finally {
				this.queueLock.unlock();
			}
		}
		
		public VariableBinding dequeue() {
			this.queueLock.lock();
			try {
				return this.queue.poll();
			} finally {
				this.queueLock.unlock();
			}
		}
		
		public VariableBinding peek() {
			this.queueLock.lock();
			try {
				return this.queue.peek();
			} finally {
				this.queueLock.unlock();
			}
		}
		
		/**
		 * Check whether the thread has finished execution and the queue
		 * has been completely drained
		 * @return
		 */
		public boolean isCompletelyDone() {
			this.queueLock.lock();
			try {
				if (this.threadFuture.isDone() && this.queue.isEmpty()) {
					return true;
				} else {
					return false;
				}
			} finally {
				this.queueLock.unlock();
			}
		}
		
		/**
		 * Wait for this thread's queue to receive at least one VariableBinding
		 * or for the thread to finish
		 */
		public void waitForNonEmptyQueueOrFinish() {
			this.queueLock.lock();
			try {
				while (!this.threadFuture.isDone() && this.queue.isEmpty()) {
					logger.trace("Waiting for queue to receive a value");
					try {
						this.queueNotEmpty.await(5,TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						logger.warn("Interrupted while waiting for non-empty queue",e);
					}
				}
			} finally {
				this.queueLock.unlock();
			}
		}
		
		public void join() {
			this.threadFuture.join();
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
	public void getRows(int maxRepetitions, List<OID> columnOIDs, Consumer<List<VariableBinding>> rowConsumer) {
		
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
		
		// Start the thread for each column
		List<ColumnRetrieverThread> threadList = new ArrayList<ColumnRetrieverThread>();
		mibTableColumns.forEach(column -> {
			ColumnRetrieverThread columnThread = new ColumnRetrieverThread();
			threadList.add(columnThread);

			Consumer<VariableBinding> c = vb -> {
				logger.trace("Adding variable binding to queue " + vb.getOid().toString());
				columnThread.enqueue(vb);
			};
			
			columnThread.start(() -> column.bulkGetTableColumn(maxRepetitions, c), workerThreadPool);
		});

		boolean done = false;
		while (!done) {
			
			// Wait for all queues to be non-empty or finished threads
			threadList.stream().forEach(t -> t.waitForNonEmptyQueueOrFinish());

			// If any thread is completely done and the queue has been drained
			// then we will not get any more complete rows so we're finished
			if (threadList.stream().filter(t -> t.isCompletelyDone()).findAny().isPresent()) {
				logger.info("A thread has finished and its queue has been drained");
				done = true;
				break;
			}
			
			// Peek at the head of every queue and get its sub-index
			List<OID> subIndexes = threadList.stream()
					.map(q -> q.peek())
					.map(vb -> new OID(Arrays.copyOfRange(vb.getOid().toIntArray(), firstColumnOIDSize, vb.getOid().size())))
					.collect(Collectors.toList());
			
			// Compare all sub-indexes to the sub-index from the first queue
			OID toCompare = subIndexes.get(0);
			List<Integer> comparisons = subIndexes.stream().map(oid -> oid.compareTo(toCompare)).collect(Collectors.toList());

			// Unless all of the results match the sub-index of the first queue we have
			// an inconsistency to correct
			if (comparisons.stream().filter(c -> c.compareTo(NumberUtils.INTEGER_ZERO) != 0).findAny().isPresent()) {
				// Remove the element from the queue that got the lowest score
				// in the comparison
				int minIndex = comparisons.indexOf(Collections.min(comparisons));
				VariableBinding vb = threadList.get(minIndex).dequeue();
				logger.warn("Table inconsistency");
				logger.warn("Removed " + vb.getOid());
			} else {
				// All of the sub-indexes match, accept the row results
				rowConsumer.accept(threadList.stream().map(t -> t.dequeue()).collect(Collectors.toList()));
			}
			
			
		}
		
		// Join all of the column threads
		threadList.stream().forEach(t -> t.join());
		this.closeThreadPool(workerThreadPool);
	}

}
