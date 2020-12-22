package objects.singletons;

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

	// Constructor
	private Prompter() { } // private constructor, meant to hide the default, public one
}
