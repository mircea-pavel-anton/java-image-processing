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

	private final Scanner sc;

	/** Private constructor, meant to hide the public default one */
	private Prompter() {
		sc = new Scanner(System.in);
	}

	/** Closes the Scanner */
	public void close() {
		sc.close();
	}

	/**
	 * Prompts the user to enter an integer, and keeps asking until it is within the
	 * given bounds
	 * 
	 * @param prompt     -> text to be displayed
	 * @param leftBound  -> start of accepted interval
	 * @param rightBound -> end of accepted interval
	 * @return -> user provided value
	 */
	public int getBoundedInt(final String prompt, final int leftBound, final int rightBound) {
		int input = 0;
		do {
			System.out.print(prompt);
			input = sc.nextInt();
		} while (input < leftBound || input > rightBound);

		return input;
	}

	/**
	 * Prompts the user for a double value
	 * 
	 * @param prompt -> text to be displayed
	 * @return -> the value from stdin
	 */
	public double getDouble(final String prompt) {
		double input = 0;
		System.out.print(prompt);
		input = sc.nextDouble();
		return input;
	}

	/**
	 * Prompt the user for a double value, and keep asking until it is within the
	 * given bounds
	 * 
	 * @param prompt     -> text to be displayed
	 * @param leftBound  -> start of accepted interval
	 * @param rightBound -> end of accepted interval
	 * @return -> user provided value
	 */
	public double getBoundedDouble(final String prompt, final double leftBound, final double rightBound) {
		double input = 0;
		do {
			System.out.print(prompt);
			if (sc.hasNext())
				input = sc.nextDouble();
			sc.nextLine();
		} while (input <= leftBound || input >= rightBound);
		return input;
	}

	/**
	 * Prompts the user for a string value. Does not accept an empty string.
	 * 
	 * @param prompt -> text to be displayed
	 * @return -> the value from stdin
	 */
	public String getString(final String prompt) {
		String input = "";
		do {
			System.out.print(prompt);
			input = sc.nextLine();
		} while (input.length() == 0);
		return input;
	}
}
