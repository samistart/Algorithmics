import linearDataStructures.SequenceDLList;
import linearDataStructures.SequenceDLList.SequenceDLListException;




public class SequenceDLListTest {
	public static void main(String[] args) {
		SequenceDLList myList = new SequenceDLList();
		for (int i = 0; i < 26; i++) {
			myList.insertFirst(new Integer(i));
		}
		while (!myList.empty())
			try {
				System.out.print(myList.first() + " ");
				myList.deleteFirst();
			} catch (SequenceDLListException e) {
				System.out.println(e);
			}
		for (int i = 0; i < 26; i++) {
			myList.insertFirst(new Integer(i));
		}
		System.out.println("/n");
		while (!myList.empty())
			try {
				
				System.out.print(myList.last() + " ");
				myList.deleteLast();
			} catch (SequenceDLListException e) {
				System.out.println(e);
			}
	}

}
