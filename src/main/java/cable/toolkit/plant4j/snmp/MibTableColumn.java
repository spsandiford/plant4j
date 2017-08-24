package cable.toolkit.plant4j.snmp;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

public class MibTableColumn {

	// prevent a runaway process
	private static final int MAX_BULK_GET_ITERATIONS = 10000;

	OID columnOID;
	Snmp snmp;
	Target target;
	Logger logger;
	
	public MibTableColumn(OID columnOID, Snmp snmp, Target target) {
		this.columnOID = Objects.requireNonNull(columnOID);
		this.snmp = Objects.requireNonNull(snmp);
		this.target = Objects.requireNonNull(target);
		this.logger = LoggerFactory.getLogger(MibTableColumn.class);
	}

	/**
	 * Traverse a MIB table column using SNMP BULKGET requests with a given repetition size.
	 * Call the given consumer for each PDU that is returned by the SNMP agent.  This method
	 * is blocking.
	 * 
	 * @param startingOID
	 * @param maxRepetitions
	 * @param vbConsumer
	 */
	public void bulkGetTableColumn(int maxRepetitions, Consumer<VariableBinding> vbConsumer) {
		Objects.requireNonNull(vbConsumer);
		if (maxRepetitions < 0) {
			throw new IllegalArgumentException();
		}

		OID currentOID = this.columnOID;
		
		for (int i=0; i<MAX_BULK_GET_ITERATIONS; i++) {
			PDU getBulkRequest = new PDU();
			getBulkRequest.add(new VariableBinding(currentOID));
			getBulkRequest.setMaxRepetitions(maxRepetitions);
			getBulkRequest.setType(PDU.GETBULK);
			
			try {
				this.logger.trace("Sending GETBULK request [" + i +  "] to " + this.target.getAddress().toString() 
						+ " " + getBulkRequest.toString());
				
				// Send the BULKGET PDU and block for the response
				ResponseEvent responseEvent = this.snmp.send(getBulkRequest, this.target);
				if (responseEvent == null) {
					throw new IOException("No response from " + this.target.getAddress().toString());
				}
				
				PDU responsePDU = responseEvent.getResponse();
				if (responsePDU == null) {
					throw new IOException("Empty response from " + this.target.getAddress().toString());
				}
				
				// Get all of the variable bindings that start with the column OID
				List<VariableBinding> variableBindings = responsePDU.getBindingList(this.columnOID);
				if (variableBindings.size() > 0) {
					Iterator<VariableBinding> variableBindingIterator = variableBindings.iterator();
					while (variableBindingIterator.hasNext()) {
						VariableBinding variableBinding = variableBindingIterator.next();
						vbConsumer.accept(variableBinding);
					}
					
					// If all of the variable bindings that are returned in the PDU are in the table
					// column then we need to continue sending BULKGET requests, otherwise we've
					// reached the end of the table.  If the number of rows in the table is a multiple
					// of maxRepetitions, then this method will ask for one more iteration that
					// will have no variable bindings that belong to the table column
					if (responsePDU.getVariableBindings().size() == variableBindings.size()) {
						currentOID = variableBindings.get(variableBindings.size() - 1).getOid();
					} else {
						break;
					}
				} else {
					break;
				}
			} catch (IOException e) {
				this.logger.error("Failure during table column traversal " + this.columnOID.toString() + " " + this.target.getAddress().toString());
				break;
			}
		}
		
	}

}
