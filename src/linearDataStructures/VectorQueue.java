package linearDataStructures;

import java.util.Vector;

/**
 * <dl>
 * <dt>Purpose: Implementation of Queue ADT.
 * <dd>
 *
 * <dt>Description:
 * <dd>This class is an implementation of the Queue using a vector as the
 * underlying data structure. Implementing the Queue with a vector rather than
 * an array has the following implications:
 * <ul>
 * <li>The capacity is unlimited and overflow does not need to be checked</li>
 * <li>Generics can be used</li>
 * <li>You cannot store primitives (Autoboxing can be used in Java 5)</li>
 * </ul>
 * </dl>
 * 
 * @author SamiStart
 * @date 04/02/15
 */
public class VectorQueue extends Vector<Object> {

	private int queueSize;

	public void enQueue(Object o) {
		this.addElement(o);
		queueSize++;
	}

	public Object deQueue() {
		queueSize--;
		return this.get(queueSize);
	}

	public Object head() {
		return this.get(queueSize-1);
	}

	public boolean empty() {
		return queueSize==0;
	}

	public void clear() {
		queueSize=0;
	}

	public class VectorQueueException extends Exception {
		public VectorQueueException() {
			super();
		}

		public VectorQueueException(String s) {
			super(s);
		}
	}

}
