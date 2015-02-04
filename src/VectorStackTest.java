import linearDataStructures.VectorStack;
import linearDataStructures.VectorStack.VectorStackException;

public class VectorStackTest {

	public static void main(String[] args) {
		VectorStack s = new VectorStack();
		for (int i = 0; i < 20; i++) {
			s.push(new String(i + "th Entry"));
		}
		
		System.out.println("Current Stack size is: " + s.getStackSize());

		System.out.println("Peeking at the top element returns: " + s.top());

		for (int i = 0; i < 15; i++) {
			try {
				System.out.println(s.pop());
			} catch (VectorStackException e) {
				System.out.println(e);
			}
		}
		
		System.out.println("Current Stack size is: " + s.getStackSize());
		
		s.clear();
		
		System.out.println("Stack size after clear() is: " + s.getStackSize());


	}

}
