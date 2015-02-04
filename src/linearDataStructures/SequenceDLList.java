package linearDataStructures;




/**
 * <dl>
 * <dt>Purpose: Implementation of Sequence ADT.
 * <dd>
 *
 * <dt>Description:
 * <dd>This class is an implementation of the Sequence using a double linked list as
 * the underlying data structure. The capacity is therefore unlimited and
 * overflow does not need to be checked.
 * </dl>
 * 
 * @author SamiStart
 */

public class SequenceDLList {
	
	public class SequenceDLListException extends Exception {

		public SequenceDLListException() {
			super();
		}

		public SequenceDLListException(String s) {
			super(s);
		}
	}
	
	/**
	 * Member class Node encapsulates the nodes of the linked list in which the
	 * stack is stored. Each node contains a data item and a reference to
	 * another node - the next in the linked list.
	 */
	protected class Node {

		public Node(Object o) {
			this(o, null, null);
		}

		public Node(Object o, Node nNext, Node nPrevious) {
			datum = o;
			next = nNext;
			previous = nPrevious;
		}

		// The Node data structure consists of three object references.
		// One for the datum contained in the node, one for the previous node in
		// the list and the other for
		// the next node in the list.

		protected Object datum;
		protected Node next;
		protected Node previous;
	}

	// We use object references to the head and tail of the list (the head
	// and tail of the sequence, respectively).
	private Node listHead;
	private Node listTail;

	// Only require a single constructor, which sets both object
	// references to null.
	/**
	 * Constructs an empty sequence object.
	 */
	public SequenceDLList() {
		listHead = null;
		listTail = null;
	}

	/**
	 * Adds a new item at the beginning of the sequence.
	 */
	public void insertFirst(Object o) {
		// There is a special case when the sequence is empty.
		// Then the both the head and tail pointers needs to be
		// initialised to reference the new node.
		if (listHead == null) {
			initialise(o);
		}

		// In the general case, we simply add a new node at the beginning
		// of the list via the head pointer.
		else {
			listHead = new Node(o, listHead, null);
		}
	}

	//Extract this method to initialise head and tail when list is empty
	//Used in insert last and insert first
	private void initialise(Object o) {
		listHead = new Node(o, listHead, listTail);
		listTail = listHead;
	}

	/**
	 * Adds a new item at the end of the sequence.
	 */
	public void insertLast(Object o) {
		// There is a special case when the sequence is empty.
		// Then the both the head and tail pointers needs to be
		// initialised to reference the new node.
		if (listHead == null) {
			initialise(o);
		}

		// In the general case, we simply add a new node to the end
		// of the list via the tail pointer.
		else {
			listTail.next = new Node(o, listTail.next, listTail);
			listTail = listTail.next;
		}
	}

	/**
	 * Adds a new item at a specified position in the sequence.
	 */
	public void insert(Object o, int index) throws SequenceDLListException {
		// Check the index is positive.
		if (index < 0) {
			throw new SequenceDLListException("Indexed Element out of Range");
		}

		// There is a special case when the sequence is empty.
		// Then the both the head and tail pointers needs to be
		// initialised to reference the new node.
		if (listHead == null) {
			if (index == 0) {
				initialise(o);
			} else {
				throw new SequenceDLListException(
						"Indexed element is out of range");
			}
		}

		// There is another special case for insertion at the head of
		// the sequence.
		else if (index == 0) {
			insertFirst(o);
		}

		// In the general case, we need to chain down the linked list
		// from the head until we find the location for the new
		// list node. If we reach the end of the list before finding
		// the specified location, we know that the given index was out
		// of range and throw an exception.
		else {
			Node nodePointer = listHead;
			int i = 1;
			while (i < index) {
				nodePointer = nodePointer.next;
				i += 1;
				if (nodePointer == null) {
					throw new SequenceDLListException(
							"Indexed Element out of Range");
				}
			}

			// Now we've found the node before the position of the
			// new one, so we 'hook in' the new Node.

			// Finally we need to check that the tail pointer is
			// correct. Another special case occurs if the new
			// node was inserted at the end, in which case, we need
			// to update the tail pointer.
			if (nodePointer == listTail) {
				insertLast(o);
			}
			else {
				nodePointer.next = new Node(o, nodePointer.next, nodePointer);
			    nodePointer.next.next.previous = nodePointer.next;
			}
		}
	}

	/**
	 * Removes the item at the beginning of the sequence.
	 */
	public void deleteFirst() throws SequenceDLListException {
		// Check there is something in the sequence to delete.
		if (listHead == null) {
			throw new SequenceDLListException("Sequence Underflow");
		}

		// There is a special case when there is just one item in the
		// sequence. Both pointers then need to be reset to null.
		if (listHead.next == null) {
			listHead = null;
			listTail = null;
		}

		// In the general case, we just unlink the first node of the
		// list.
		else {
			listHead = listHead.next;
			listHead.previous=null;
		}
	}

	/**
	 * Removes the item at the end of the sequence.
	 */
	public void deleteLast() throws SequenceDLListException {
		// Check there is something in the sequence to delete.
		if (listHead == null) {
			throw new SequenceDLListException("Sequence Underflow");
		}

		// There is a special case when there is just one item in the
		// sequence. Both pointers then need to be reset to null.
		if (listHead.next == null) {
			listHead = null;
			listTail = null;
		}

		// In the general case, we need to reset the link of the second to last
		// element to null.
		else {
			listTail = listTail.previous;
			listTail.next=null;
		}
	}

	/**
	 * Removes the item at the specified position in the sequence.
	 */
	public void delete(int index) throws SequenceDLListException {
		// Check there is something in the sequence to delete.
		if (listHead == null) {
			throw new SequenceDLListException("Sequence Underflow");
		}

		// Check the index is positive.
		if (index < 0) {
			throw new SequenceDLListException("Indexed Element out of Range");
		}

		// There is a special case when there is just one item in the
		// sequence. Both pointers then need to be reset to null.
		if (listHead.next == null) {
			if (index == 0) {
				listHead = null;
				listTail = null;
			} else {
				throw new SequenceDLListException(
						"Indexed element is out of range.");
			}
		}

		// There is also a special case when the first element has to
		// be removed.

		else if (index == 0) {
			deleteFirst();
		}

		// In the general case, we need to chain down the list to find
		// the node in the indexed position.
		else {
			Node nodePointer = listHead;
			int i = 1;
			while (i < index) {
				nodePointer = nodePointer.next;
				i += 1;
				if (nodePointer.next == null) {
					throw new SequenceDLListException(
							"Indexed Element out of Range");
				}

			}

			// Unlink the node and reset the tail pointer if that
			// node was the last one.
			if (nodePointer.next == listTail) {
				listTail = nodePointer;
				listTail.next=null;
			}
			nodePointer.next = nodePointer.next.next;
			nodePointer.next.previous=nodePointer;
		}
	}

	/**
	 * Returns the item at the beginning of the sequence.
	 */
	public Object first() throws SequenceDLListException {
		if (listHead != null) {
			return listHead.datum;
		} else {
			throw new SequenceDLListException("Indexed Element out of Range");
		}
	}

	/**
	 * Returns the item at the end of the sequence.
	 */
	public Object last() throws SequenceDLListException {
		if (listTail != null) {
			return listTail.datum;
		} else {
			throw new SequenceDLListException("Indexed Element out of Range");
		}
	}

	/**
	 * Returns the item at the specified position in the sequence.
	 */
	public Object element(int index) throws SequenceDLListException {
		// Check the index is positive.
		if (index < 0) {
			throw new SequenceDLListException("Indexed Element out of Range");
		}

		// We need to chain down the list until we reach the indexed
		// position

		Node nodePointer = listHead;
		int i = 0;
		while (i < index) {
			if (nodePointer.next == null) {
				throw new SequenceDLListException(
						"Indexed Element out of Range");
			} else {
				nodePointer = nodePointer.next;
				i += 1;
			}
		}

		return nodePointer.datum;
	}

	/**
	 * Tests whether there are any items in the sequence.
	 */
	public boolean empty() {
		return (listHead == null);
	}

	/**
	 * Returns the number of items in the sequence.
	 */
	public int size() {
		// Chain down the list counting the elements

		Node nodePointer = listHead;
		int size = 0;
		while (nodePointer != null) {
			size += 1;
			nodePointer = nodePointer.next;
		}
		return size;
	}

	/**
	 * Empties the sequence.
	 */
	public void clear() {
		listHead = null;
		listTail = null;
	}
}
