package objects.singletons;

public class FilterBuilder {
	// Singleton Instance
	private static FilterBuilder instance = null;

	/** If no instance has been created yet, create one and return it, otherwise, return the
	 * already existing one
	 * 
	 * @return an instance of ArgParser
	 */
	public static FilterBuilder getInstance() {
		if (instance == null) {
			instance = new FilterBuilder();
		}
		return instance;
	}

	// Constructor
	private FilterBuilder() {} // private constructor, meant to hide the default, public one
}
