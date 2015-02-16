package LookUpTables;

class KeyNotFoundInTableException extends Exception {
}

/**
 * <dl>
 * <dt>Purpose: Implementation of LUT using ordered linear search.
 * <dd>
 *
 * <dt>Description:
 * <dd>This class is an implementation of the look-up table abstract data type
 * that uses a sequence array as the underlying data structure. The capacity of
 * the LUT is thus limited. The elements of the look-up table are stored in
 * reverse alphabetical order and linear search is used for retrieval.
 * </dl>
 *
 *@author Sami Start 
 * @version $Date: 15/02/2015
 */

public class BinaryTreeLUT {

	/**
	 * The member class Key is used for the indexing keys of the LUT. It is a
	 * String with basic comparative methods added.
	 */
	protected class Key {

		public Key(String s) {
			kString = s;
		}

		public boolean equals(Key k) {
			return kString.equals(k.toString());
		}

		public boolean lessThan(Key k) {
			return (kString.compareTo(k.toString()) < 0);
		}

		public boolean greaterThan(Key k) {
			return (kString.compareTo(k.toString()) > 0);
		}

		public String toString() {
			return kString;
		}

		private String kString;
	}

	/**
	 * The member class Entry encapsulates an entry of the LUT and contains a
	 * {key, value} pair.
	 */
	protected class Entry {

		public Entry(Key k, Object v) {
			key = k;
			value = v;
		}

		protected Key key;
		protected Object value;
	}

	/**
	 * The member class BSTreeNode encapsulates node of the binary search tree,
	 * which contains a LUT entry and links to left and right subtrees.
	 */
	protected class BSTreeNode {

		public BSTreeNode(Entry e) {
			kvPair = e;
			left = null;
			right = null;
		}

		public BSTreeNode(Entry e, BSTreeNode l, BSTreeNode r) {
			kvPair = e;
			left = l;
			right = r;
		}

		protected Entry kvPair;
		protected BSTreeNode left;
		protected BSTreeNode right;
	}

	// Single protected data member - the LUT is stored in a sequence.
	protected BSTreeNode root;

	/**
	 * Default constructor - no need to specify capacity of LUT.
	 */
	public BinaryTreeLUT() {
		root = null;
	}

	/**
	 * Inserts a new key-value pair into the look-up table.
	 */
	public void insert(String key, Object value) {

		BSTreeNode newNode = new BSTreeNode(new Entry(new Key(key), value));
		addToTree(newNode, root);
	}

	/**
	 * Removes the key-value pair with the specified key from the look-up table.
	 */
	public void remove(String key) throws KeyNotFoundInTableException {

		Key searchKey = new Key(key);
		removeFromTree(searchKey, root);
	}

	/**
	 * Removes the node containing k from the tree rooted at node.
	 */
	protected void removeFromTree(Key k, BSTreeNode node)
			throws KeyNotFoundInTableException {
		// Special case for empty tree.
		if (node == null) {
			throw new KeyNotFoundInTableException();
		}

		// Special case when deleting the root node.
		else if (root.kvPair.key.equals(k)) {
			root = lrMerge(root);
		}

		// If the key at the current node is less than
		// the search key, go to the left subtree.
		else if (node.kvPair.key.lessThan(k)) {

			// If the left subtree is empty, the tree cannot contain
			// the search key.

			if (node.left == null) {
				throw new KeyNotFoundInTableException();
			}

			// If this is the parent of the node to be removed, do
			// the removal.
			if (node.left.kvPair.key.equals(k)) {
				node.left = lrMerge(node.left);
			}

			// Otherwise, recurse down another level.
			else {
				removeFromTree(k, node.left);
			}
		}

		// Otherwise go to the right subtree.
		else {

			// If the right subtree is empty, the tree cannot contain
			// the search key.

			if (node.right == null) {
				throw new KeyNotFoundInTableException();
			}

			// If this is the parent of the node to be removed, do
			// the removal.
			if (node.right.kvPair.key.equals(k)) {
				node.right = lrMerge(node.right);
			}

			// Otherwise, recurse down another level.
			else {
				removeFromTree(k, node.right);
			}
		}
	}

	/**
	 * Merges the two subtrees of node prior to removal of the node from the
	 * tree.
	 */
	protected BSTreeNode lrMerge(BSTreeNode node) {

		BSTreeNode mergedTrees = null;

		// First two cases occur when one or both
		// subtrees of the node to be deleted are empty.
		if (node.left == null) {
			mergedTrees = node.right;
		} else if (node.right == null) {
			mergedTrees = node.left;
		}

		// Otherwise, merge the left and right subtrees
		// and link the merged structure to the current
		// node.
		else {
			mergedTrees = node;
			// Set node entry to that of the right-most leaf of it's left
			// subtree and delete that leaf
			mergedTrees.kvPair = deleteAndReturnMax(node.left).kvPair;
		}

		return mergedTrees;
	}

	/*
	 * Gets minimum node (rightmost leaf) in a sub-tree
	 */
	private BSTreeNode deleteAndReturnMax(BSTreeNode self) {

		BSTreeNode currentNode = self;

		while (currentNode.right != null) {
			currentNode = currentNode.right;
		}

		BSTreeNode result = currentNode;

		// Replace with child if child exists, otherwise set to null
		try {
			remove(currentNode.kvPair.key.kString);
		} catch (KeyNotFoundInTableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Retrieves the key-value pair with the specified key from the look-up
	 * table.
	 */
	public Object retrieve(String key) throws KeyNotFoundInTableException {

		Key searchKey = new Key(key);
		BSTreeNode treeNode = getFromTree(searchKey, root);
		return treeNode.kvPair.value;
	}

	/**
	 * Updates the key-value pair with the specified key with the new specified
	 * value.
	 */
	public void update(String key, Object value)
			throws KeyNotFoundInTableException {

		Key searchKey = new Key(key);
		BSTreeNode treeNode = getFromTree(searchKey, root);
		treeNode.kvPair.value = value;
	}

	/**
	 * Returns a string listing all the key-entry pairs in the LUT
	 */
	public String toString() {
		return treeString(root);
	}

	// protected methods implementing recursive operations on the tree.

	/**
	 * Adds newNode to the tree rooted at curNode recursively.
	 */
	protected void addToTree(BSTreeNode newNode, BSTreeNode curNode) {

		// Special case for empty tree.
		if (curNode == null) {
			root = newNode;
		}

		// General case: recurse left or right depending on comparison.
		else if (curNode.kvPair.key.lessThan(newNode.kvPair.key)) {
			if (curNode.left == null) {
				curNode.left = newNode;
			} else {
				addToTree(newNode, curNode.left);
			}
		} else {
			if (curNode.right == null) {
				curNode.right = newNode;
			} else {
				addToTree(newNode, curNode.right);
			}
		}
	}

	/**
	 * Returns the node containing k from the tree rooted at node.
	 */
	protected BSTreeNode getFromTree(Key k, BSTreeNode node)
			throws KeyNotFoundInTableException {

		if (node == null) {
			throw new KeyNotFoundInTableException();
		} else if (node.kvPair.key.equals(k)) {
			return node;
		} else if (node.kvPair.key.lessThan(k)) {
			return getFromTree(k, node.left);
		} else {
			return getFromTree(k, node.right);
		}
	}

	/**
	 * Uses in order tree traversal to construct a string containing all the key
	 * value pairs in the binary search tree.
	 */
	protected String treeString(BSTreeNode node) {
		if (node == null) {
			return "";
		}

		Entry lutEntry = node.kvPair;
		String thisNode = "";
		thisNode = lutEntry.key.toString();
		thisNode += ":";
		thisNode += lutEntry.value;
		thisNode += ", ";

		return treeString(node.left)+ thisNode + treeString(node.right) ;
	}
}
