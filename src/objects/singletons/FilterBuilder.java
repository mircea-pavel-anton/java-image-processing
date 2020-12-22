package objects.singletons;

import java.util.ArrayList;

import objects.GenericJob;
import objects.filters.GenericFilter;

public class FilterBuilder extends GenericJob {
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

	// Fields
	private ArrayList<GenericFilter> filters = new ArrayList<GenericFilter>();

	/** Getter for the list of filters */
	public ArrayList<GenericFilter> getFilters() { return this.filters; }

	/** Interactive shell sequence that prompts the user to build up filters */
	private void build() {

	}

	/** A wrapper for the @build function that implements the Timer singleton to track execution
	 * duration
	 * 
	 * @throws Exception -> rethrows all exceptions
	 */
	public void run() throws Exception {
		Timer timer = Timer.getInstance();
		timer.startJob(String.valueOf(uID));
		build();
		duration = timer.stopJob(String.valueOf(uID));
	}
}
