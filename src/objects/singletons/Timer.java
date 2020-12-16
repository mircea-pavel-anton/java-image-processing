package objects.singletons;

import java.util.HashMap;

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
	private Timer() {
		jobStart = new HashMap<>();
		jobEnd = new HashMap<>();
	}

	// Fields
	// This hashmap will store a key-value pair, the key being the name of the job and the
	// value being the time in millis at which it was started
	private HashMap<String, Long> jobStart;
	// This hashmap will store a key-value pair, the key being the name of the job and the
	// value being the time in millis at which it was ended
	private HashMap<String, Long> jobEnd;
	// In order to get the job duration, we can subtract jobEnd[jobName] - jobStart[jobName]
	// To get the status of a job, we can see if it exists in either of the hashmaps
}
