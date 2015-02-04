import java.util.Stack;

import linearDataStructures.VectorStack;

public class Main {

	public static void main(String[] args) {
		
		VectorStack myStack = new VectorStack();

		System.out.println("Stack empty? " + myStack.empty());

		for (int i = 0; i < 50; i++) {
			myStack.push(i);
		}

		for (int i = 0; i < 10; i++) {

		}
		try {
			System.out.println(myStack.pop());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Stack empty? " + myStack.empty());

	}

}
