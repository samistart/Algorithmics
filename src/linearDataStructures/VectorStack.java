package linearDataStructures;

import java.util.Vector;

/**
 * <dl>
 * <dt>Purpose: Implementation of Stack ADT.
 * <dd>
 *
 * <dt>Description:
 * <dd>This class is an implementation of the Stack using a vector as the
 * underlying data structure. Implementing the Stack with a vector rather than
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

public class VectorStack extends Vector<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int stackSize;

	public VectorStack() {
		super();
	}

	public int getStackSize() {
		return stackSize;
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void push(Object o) {
		this.addElement(o);
		stackSize++;
	}

	public Object pop() throws VectorStackException {
		if (stackSize != 0) {
			stackSize--;
			return this.get(stackSize);
		} else {
			throw new VectorStackException();
		}

	}

	public Object top() {
		return this.get(stackSize - 1);
	}

	public boolean empty() {
		return stackSize == 0;
	}

	public void clear() {
		stackSize = 0;
	}

	public class VectorStackException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public VectorStackException() {
			super();
		}

		VectorStackException(String s) {
			super(s);
		}
	}

}
