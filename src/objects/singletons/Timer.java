package objects.singletons;

public class Timer {
	// Singletion Instance
	private static Timer instance = null;
	public static Timer getInstance() {
		if (instance == null) {
			instance = new Timer();
		}
		return instance;
	}
	
	// Constructor
	private Timer() {}
}
