package objects.singletons;

import java.io.File;

import objects.GenericJob;

public class ArgParser extends GenericJob{
	// Singleton Instance
	private static ArgParser instance = null;

	/** If no instance has been created yet, create one and return it, otherwise, return the
	 * already existing one
	 * 
	 * @return an instance of ArgParser
	 */
	public static ArgParser getInstance() {
		if (instance == null) {
			instance = new ArgParser();
		}
		return instance;
	}

	// Constructor
	private ArgParser() {} // private constructor, meant to hide the default, public one

	// Arguments that can be parsed
	private String inputFilePath = null; // path to the image that will be filtered
	private String outputFilePath = null; // path to the processed image
	private String filterName = null; // the name of the filter to use

	// Getters
	public String getInputFilePath() { return inputFilePath; }
	public String getOutputFilePath() { return outputFilePath; }
	public String getFilterType() { return filterName; }

	// Setters
	/** Checks the validity of the given path and assigns it to inputFilePath if it is valid
	 * 
	 * @param inputFilePath = the path to the image that will be filtered
	 * @throws Exception:
	 *   - if the file does not exist
	 *   - if a directory has been given, instead of a file
	 *   - if read access is denied to the file
	 */
	public void setInputFilePath(String inputFilePath) throws Exception {
		File inputFile = new File(inputFilePath);

		if (inputFile.exists()) {
			if (inputFile.isFile()) {
				if (inputFile.canRead()) {
					this.inputFilePath = inputFile.getAbsolutePath();
				} else {
					this.inputFilePath = null;
					throw new Exception("Cannot read from file: '" + inputFilePath + "'");
				}
			} else {
				this.inputFilePath = null;
				throw new Exception("File is directory: '" + inputFilePath + "'");
			}
		} else {
			this.inputFilePath = null;
			throw new Exception("No such file exists: '" + inputFilePath + "'");
		}
	}

	/** Checks the validity of the given path and assigns it to outputFilePath if it is valid
	 * 
	 * @param outputFilePath = the path to the processed image
	 * @throws Exception if the file already exists
	 */
	public void setOutputFilePath(String outputFilePath) throws Exception {
		File outputFile = new File(outputFilePath);

		if (!outputFile.exists()) {
			this.outputFilePath = outputFile.getAbsolutePath();
		} else {
			this.outputFilePath = null;
			throw new Exception("File already exists: '" + outputFilePath + "'");
		}
	}
	
	/** Checks if the requested filter type exists, and assigns it to filterName if it does
	 * 
	 * @param filter = the type of filter to use
	 */
	public void setFilterType(String filter) {
		// TODO check if filter name is valid
		this.filterName = filter;
	}

	/** Loops through the args array, and attempts to match the values to the appropriate fields,
	 * based on the 'regex'
	 * inputFilePath -> '--input'
	 * outputFilePath -> '--output'
	 * filterName -> '--filtre'
	 * 
	 * @param args = the array of arguments
	 * @throws Exception: if insufficient or illegal arguments are found
	 */
	private void parse(String[] args) throws Exception {
		if (args.length < 6) {
			throw new Exception("Insufficient input arguments");
		} else {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
					case "-if":
					case "--input":
						setInputFilePath( getArgValue(args, i++) );
						break;

					case "-of":
					case "--output":
						setOutputFilePath( getArgValue(args, i++) );
						break;

					case "-f":
					case "--filter":
						setFilterType( getArgValue(args, i++) );
						break;

					default:
						throw new Exception("Unknown input argument: " + args[i]);
				}
			}
		}
	}
	
	/** A wrapper for the @parse function, that implements the functionality of the Timer class
	 * 
	 * @param args = the list of arguments that will be passed to @parse
	 * @return = the job duration
	 * @throws Exception - rethrows all exceptions from @parse
	 */
	public long parseArguments(String[] args) throws Exception {
		Timer timer = Timer.getInstance();
		timer.startJob(String.valueOf(jobIdentifier));
		parse(args);
		return timer.stopJob(String.valueOf(jobIdentifier));
	}

	/** Attempts to extract the value of an argument
	 * 
	 * @param args = the array of arguments
	 * @param index = the index of the found 'regex' ('--input', '--output' or '--filter')
	 * @return the argument value, if it exists
	 */
	private String getArgValue(String[] args, int index) {
		if (index + 1 < args.length) {
			return args[index+1];
		} else {
			throw new IllegalArgumentException("Insufficient input arguments");
		}
	}

}
