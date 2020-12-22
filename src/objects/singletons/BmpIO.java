package objects.singletons;

public class BmpIO {
	// Singleton Instance
	private static BmpIO instance = null;

	/** If no instance has been created yet, create one and return it, otherwise, return the
	 * already existing one
	 * 
	 * @return an instance of BmpIO
	 */
	public static BmpIO getInstance() {
		if (instance == null) {
			instance = new BmpIO();
		}
		return instance;
	}

	/** Private constructor, to hide the default public one */
	private BmpIO() { }
}
