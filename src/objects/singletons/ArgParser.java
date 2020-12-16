package objects.singletons;

public class ArgParser {
	// Singleton Instance
	private static ArgParser instance = null;
	public static ArgParser getInstance() {
		if (instance == null) {
			instance = new ArgParser();
		}
		return instance;
	}

	// Constructor
	private ArgParser() {} // private constructor, meant to hide the default, public one
}
