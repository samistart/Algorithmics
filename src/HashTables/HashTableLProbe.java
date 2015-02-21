package HashTables;

class KeyNotFoundInTableException extends Exception {
	KeyNotFoundInTableException() {
		super();
	}

	KeyNotFoundInTableException(String s) {
		super(s);
	}
}

// Overflow for resize is handle in a try catch within the method
class TableOverflowException extends Exception {
	TableOverflowException() {
		super();
	}

	TableOverflowException(String s) {
		super(s);
	}
}

/**
 * This is an implementation of a hash table with linear probing with an array
 * as the underlying data structure. delete() replaces deleted entries with a
 * tomb stone and delete2() sets them to null and rehashes succeeding entries.
 * There is a resize function which rehashes the entire table to an area of size m.
 * 
 * @date 20th February 2015
 * @author samistart
 *
 */
public class HashTableLProbe {

	/**
	 * Entries in the hash table arry.
	 */
	protected class Entry {
		protected String key;
		protected Object value;

		public Entry(String k, Object v) {
			key = k;
			value = v;
		}
	}

	/**
	 * The hash table is implemented by an array of entries.
	 */
	protected Entry[] entries;

	/**
	 * Compute a hash over a string and map it into a given range.
	 */
	private int h1(String key, int M) {
		int n = 0;

		for (int i = 0; i < key.length(); i++) {
			n += (int) key.charAt(i);
		}

		return n % M;
	}

	/**
	 * Default constructor.
	 */
	public HashTableLProbe() {
		entries = new Entry[50];
	}

	/**
	 * Constructor for a given hash table size.
	 */
	public HashTableLProbe(int size) {
		entries = new Entry[size];
	}

	/**
	 * Map a key to a value (insert into the hash table).
	 */
	public void insert(String key, Object value) throws TableOverflowException {
		// Compute the hash value
		int index = h1(key, entries.length);

		// Probe linearly to find empty slot.
		int count = 0;

		while (entries[index] != null
				&& (!entries[index].key.equals("Tombstone"))
				&& count != entries.length) {
			index = (index + 1) % entries.length;
			count += 1;
		}

		if (count == entries.length) {
			throw new TableOverflowException();
		} else {
			entries[index] = new Entry(key, value);
		}
	}

	/**
	 * Find the value which is mapped to a key.
	 */
	public Object retrieve(String key) throws KeyNotFoundInTableException {
		int index = find(key);

		return entries[index].value;
	}

	/**
	 * Delete a mapping for a key.
	 */
	public void delete(String key) throws KeyNotFoundInTableException {
		// Have extracted this find method that is common to both delete
		// functions
		int index = find(key);
		// Mark the key of the entry to be deleted with a tombstone
		entries[index].key = "Tombstone";
	}

	private int find(String key) throws KeyNotFoundInTableException {
		// Compute the hash value
		int index = h1(key, entries.length);

		// Probe linearly looking for match.
		int count = 0;

		while (entries[index] != null && (!entries[index].key.equals(key))
				&& count != entries.length) {
			index = (index + 1) % entries.length;
			count += 1;
		}

		if (entries[index] == null || count == entries.length) {
			throw new KeyNotFoundInTableException();
		}
		return index;
	}

	/**
	 * Delete a mapping for a key without creating a tombstone. This sets the
	 * entry to null and rehashes the succeeding entries instead.
	 */
	public void delete2(String key) throws KeyNotFoundInTableException {
		// Like delete, except that the entry is not replaced by a tombstone.
		// Instead, the entry will be deleted (set to null).
		// This requires a cleanup and rehashing all succeeding entries.
		int index = find(key);

		entries[index] = null;

		for (int i = index + 1; i < entries.length; i++) {
			Entry temp;
			if (entries[i] != null) {
				temp = entries[i];
				entries[i] = null;
				try {
					insert(temp.key, temp.value);
				} catch (TableOverflowException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return;
			}
		}

	}

	/**
	 * Resize the current hash table to a different size.
	 *
	 * Resizing can be done to enlarge or to shrink the hash table. If the
	 * resized hash table cannot hold all elements of the previous one, an
	 * exception will be thrown.
	 */
	public void resize(int m) {
		// Replaces the entries array with a new one in which all
		// old entries are inserted fresh (`rehashing').
		Entry[] oldTable = entries;

		entries = new Entry[m];
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] != null) {
				try {
					insert(oldTable[i].key, oldTable[i].value);
				} catch (TableOverflowException e) {
					entries = oldTable;
					System.err
							.println("Cannot rehash due to table overflow. Table was not resized and remains unchanged.");
					e.printStackTrace();
					return;
				}
			}
		}
	}

	/**
	 * Return a textual representation of the hash table.
	 */
	public String toString() {
		String str = "";
		for (int i = 0; i < entries.length; ++i) {
			Entry e = entries[i];
			str += i + ": ";
			if (e != null) {
				str += e.key + " " + e.value + "\n";
			} else {
				str += null + "\n";
			}
		}
		return str;
	}
}
