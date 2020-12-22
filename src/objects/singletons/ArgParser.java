package objects.singletons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.GenericJob;
public class ArgParser extends GenericJob{
	private static final String[] HELP = {
		"Usage: simp --input=/path/to/file --output=/path/to/file2",
		"And then follow the on-screen prompts in order to choose your filters."
	};


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
	private ArgParser() { } // private constructor, meant to hide the default, public one

	// Arguments that can be parsed
	private List<File> inputFiles = new ArrayList<File>(); // path to the images that will be filtered
	private File outputFile = null; // path to the processed image

	// Getters
	public List<File> getInputFiles() { return inputFiles; }
	public File getOutputFile() { return outputFile; }

	// Setters
	/** Checks the validity of the given path and assigns it to inputFilePath if it is valid
	 * 
	 * @param inputFilePath = the path to the image that will be filtered
	 * @throws Exception:
	 *   - if the file does not exist
	 *   - if a directory has been given, instead of a file
	 *   - if read access is denied to the file
	 */
	public void addInputFilePath(String inputFilePath, boolean force) throws Exception {
		if (this.inputFiles.size() != 0 || force == false) {
			throw new Exception("Input file has already been set.");
		}
		File inputFile = new File(inputFilePath);

		if (inputFile.exists()) {
			if (inputFile.isFile()) {
				if (inputFile.canRead()) {
					this.inputFiles.add( inputFile );
				} else {
					throw new Exception("Cannot read from file: '" + inputFilePath + "'");
				}
			} else {
				throw new Exception("File is directory: '" + inputFilePath + "'");
			}
		} else {
			throw new Exception("No such file exists: '" + inputFilePath + "'");
		}
	}

	/** Checks the validity of the given path and assigns it to outputFilePath if it is valid
	 * 
	 * @param outputFilePath = the path to the processed image
	 * @throws Exception if the file already exists
	 */
	public void setOutputFilePath(String outputFilePath) throws Exception {
		if (this.outputFile != null) {
			throw new Exception("Output file has already been set.");
		}

		File outputFile = new File(outputFilePath);

		if (!outputFile.exists()) {
			this.outputFile = outputFile;
		} else {
			char input = ' ';
			Scanner scan = new Scanner(System.in);
			do {
				System.out.println("File already exists. Overwrite? [y/N]");
				input = scan.nextLine().charAt(0);
			} while (input != 'y' || input != 'Y' || input != 'n' || input != 'N');
			scan.close();

			if (input == 'Y' || input == 'y') {
				if ( outputFile.delete() ) {
					this.outputFile = new File(outputFilePath);
				} else {
					throw new Exception("Unable to delete file: '" + outputFilePath + "'");
				}
			} else {
				throw new Exception("File already exists: '" + outputFilePath + "'");
			}
		}
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
		switch (args.length) {
			case 0:
				throw new Exception("Insufficient arguments.\nSee '-h' for help");
			
			case 1:
				if (args[0].equals("-h") || args[0].equals("--help")) {
					for (int i = 0; i < HELP.length; i++)
						System.out.println(HELP[i]);
				} else {
					throw new Exception("Unknown argument.\nSee '-h' for help");
				}
			
			case 2:
				for (int i = 0; i < 2; i++) {
					if (args[i].contains("--input=")) {
						addInputFilePath( args[i].substring(8), false );
					} else if (args[i].contains("-i=")) {
						addInputFilePath( args[i].substring(3), false );
					} else if (args[i].contains("--output=")) {
						setOutputFilePath( args[i].substring(9) );
					} else if (args[i].contains("-o=")) {
						setOutputFilePath( args[i].substring(3) );
					} else {
						throw new Exception("Unknown argument.\nSee '-h' for help");
					}
				}
			default:
				throw new Exception("Simp only ccepts 2 arguments.\nSee '-h' for help");
		}
	}
	
	/** A wrapper for the @parse function, that implements the functionality of the Timer class
	 * 
	 * @param args = the list of arguments that will be passed to @parse
	 * @throws Exception - rethrows all exceptions from @parse
	 */
	public void parseArguments(String[] args) throws Exception {
		Timer timer = Timer.getInstance();
		timer.startJob(String.valueOf(uID));
		parse(args);
		duration = timer.stopJob(String.valueOf(uID));
	}
}
