package objects.singletons;

import java.util.Calendar;
import java.util.HashMap;

public class Timer {
	// Singletion Instance
	private static Timer instance = null;

	/** If no instance has been created yet, create one and return it, otherwise, return the
	 * already existing one
	 * 
	 * @return an instance of Timer
	 */
	public static Timer getInstance() {
		if (instance == null) {
			instance = new Timer();
		}
		return instance;
	}
	
	// Private constructor, hides the public default one
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

	/** Adds a key-value pair into the jobStart hashmap, associating the current time in millis
	 * to the job identifier
	 * 
	 * @param identifier = the identifier of the job to end
	 * @throws Exception if thee job was never started to begin with
	 */
	public void startJob(String identifier) throws Exception{
		if ( jobStart.containsKey(identifier) ) {
			throw new Exception("Another job with the same identifier is running.");
		}
		jobStart.put(identifier, Calendar.getInstance().getTimeInMillis());
	}

	/** A wrapper function that calls @endJob, getJobDuration and deleteJobRecord
	 * 
	 * @param identifier = the identifier of the job to stop
	 * @return the job duration in millis
	 * @throws Exception rethrows all exceptions from all the functions it calls
	 */
	public long stopJob(String identifier) throws Exception {
		endJob(identifier);
		long duration = getJobDuration(identifier);
		deleteJobRecord(identifier);
		return duration;
	}

	/** Adds a key-value pair into the jobEnd hashmap, associating the current time in millis
	 * to the job identifier
	 * 
	 * @param identifier = the identifier of the job to end
	 * @throws Exception if thee job was never started to begin with
	 */
	private void endJob(String identifier) throws Exception{
		if ( !jobStart.containsKey(identifier) ) {
			throw new Exception("No such job is running");
		}
		jobEnd.put(identifier, Calendar.getInstance().getTimeInMillis());
	}
	
	/** Removes the entries associated with the given key from both hashmaps
	 * 
	 * @param identifier = the identifier of the job to remove
	 */
	private void deleteJobRecord(String identifier) {
		jobStart.remove(identifier);
		jobEnd.remove(identifier);
	}

	/** Will calculate the duration of the job by subtracting jobEnd[identifier] - jobStart[identifier]
	 * 
	 * @param identifier = the identifier of the job
	 * @return the job duration, in milliseconds
	 * @throws Exception if the job wasn't even started, or if it was started but never ended
	 */
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
