package objects;

public abstract class GenericJob {
	protected double jobIdentifier;

	/** 
	 * Generates a random number used as an identifier for the Timer object
	 */
	protected GenericJob() {
		jobIdentifier = Math.random();
	}
}
