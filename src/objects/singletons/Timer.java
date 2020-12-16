package objects.singletons;

import java.util.Calendar;
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

	public void startJob(String identifier) throws Exception{
		if ( jobStart.containsKey(identifier) ) {
			throw new Exception("Another job with the same identifier is running.");
		}
		jobStart.put(identifier, Calendar.getInstance().getTimeInMillis());
	}
	public long stopJob(String identifier) throws Exception {
		endJob(identifier);
		long duration = getJobDuration(identifier);
		deleteJobRecord(identifier);
		return duration;
	}

	private void endJob(String identifier) throws Exception{
		if ( !jobStart.containsKey(identifier) ) {
			throw new Exception("No such job is running");
		}
		jobEnd.put(identifier, Calendar.getInstance().getTimeInMillis());
	}
	private void deleteJobRecord(String identifier) {
		jobStart.remove(identifier);
		jobEnd.remove(identifier);
	}
	private long getJobDuration(String identifier) throws Exception {
		if ( !jobStart.containsKey(identifier) ) {
			throw new Exception("No such job is running");
		}
		if ( !jobEnd.containsKey(identifier) ) {
			throw new Exception("The job has not ended yet.");
		}
		return jobEnd.get(identifier) - jobStart.get(identifier);
	}
}
