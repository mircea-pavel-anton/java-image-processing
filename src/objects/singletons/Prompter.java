package objects.singletons;

import java.util.Scanner;

public class Prompter {
	// Singleton Instance
	private static Prompter instance = null;

	/** If no instance has been created yet, create one and return it, otherwise, return the
	 * already existing one
	 * 
	 * @return an instance of Prompter
	 */
	public static Prompter getInstance() {
		if (instance == null) {
			instance = new Prompter();
		}
		return instance;
	}

	private Scanner sc;
	// Constructor
	private Prompter() { sc = new Scanner(System.in); } // private constructor, meant to hide the default, public one
	public void close() { sc.close(); }

	public int getBoundedInt(String prompt, int leftBound, int rightBound) {
		int input = 0;
		do {
			System.out.print(prompt);
			input = sc.nextInt();
		} while (input < leftBound || input > rightBound);

		return input;
	}
	public double getDouble(String prompt) {
		double input = 0;
		System.out.print(prompt);
		input = sc.nextDouble();
		return input;

	}
	public double getBoundedDouble(String prompt, double leftBound, double rightBound) {
		double input = 0;
		do {
			System.out.print(prompt);
			if (sc.hasNext()) input = sc.nextDouble();
			sc.nextLine();
		} while (input <= leftBound || input >= rightBound);
		return input;

	}
	public String getString(String prompt) {
		String input = "";
		do {
			System.out.print(prompt);
			input = sc.nextLine();
		} while (input.length() == 0);
		return input;
	}
}
