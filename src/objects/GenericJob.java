package objects;

public abstract class GenericJob {
	protected double uID = 0; // unique identifier
	protected long duration = 0; // job duration, in ms

	// if not already created, generate a new unique identifier
	protected double getUID() { 
		if (uID == 0) {
			uID = Math.random();
		}
		return uID;
	}

	// Getter
	public long getDuration() { return this.duration; }

	/** Returns a summary that specifies execution time */
	public String getSummary() { return this.toString() + " took " + duration + " ms"; }
}
