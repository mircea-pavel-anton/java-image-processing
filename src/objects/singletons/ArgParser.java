package objects.singletons;

import java.io.File;

public class ArgParser {
	// Singleton Instance
	private static ArgParser instance = null;
	public static ArgParser getInstance() {
		if (instance == null) {
			instance = new ArgParser();
		}
		return instance;
	}

	// Constructor
	private ArgParser() {} // private constructor, meant to hide the default, public one

	// Arguments that can be parsed
	private String inputFilePath = null;
	private String outputFilePath = null;
	private String filterName = null;

	// Getters
	public String getInputFilePath() { return inputFilePath; }
	public String getOutputFilePath() { return outputFilePath; }
	public String getFilterType() { return filterName; }

	// Setters
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
	public void setOutputFilePath(String outputFilePath) throws Exception {
		File outputFile = new File(outputFilePath);

		if (!outputFile.exists()) {
			this.outputFilePath = outputFile.getAbsolutePath();
		} else {
			this.outputFilePath = null;
			throw new Exception("File already exists: '" + outputFilePath + "'");
		}
	}
	public void setFilterType(String filter) {
		// TODO check if filter name is valid
		this.filterName = filter;
	}


	// Actual argument parsing
	public void parse(String[] args) throws Exception {
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
	
	private String getArgValue(String[] args, int index) {
		if (index + 1 < args.length) {
			return args[index+1];
		} else {
			throw new IllegalArgumentException("Insufficient input arguments");
		}
	}

}
