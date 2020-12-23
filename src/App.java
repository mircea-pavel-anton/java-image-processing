import java.io.File;
import java.util.ArrayList;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.singletons.ArgParser;
import objects.singletons.BmpIO;
import objects.singletons.FilterBuilder;

public class App {
	public static void main(String[] args) {
		ArgParser argParser = ArgParser.getInstance();
		try {
			argParser.parseArguments(args);
			File input = argParser.getInputFiles();
			File output = argParser.getOutputFile();

			// If the input and output files have been set after parsing the arguments
			// The only way the code can reach this point and fail this condition, is
			// if the argument was '-h' or '--help'
			// otherwise, some exception would have been thrown
			if (input != null && output != null) {
				BmpIO bmpIO = BmpIO.getInstance();
				Image temp = bmpIO.read();

				FilterBuilder fBuilder = FilterBuilder.getInstance();
				fBuilder.run(); // run the builder

				// Retrieve the filter list from the builder
				ArrayList<GenericFilter> filters = fBuilder.getFilters();

	
				// Run each filter from the list, in order
				// Supply each filter with the image produced by the previous one
				for (int i = 0; i < filters.size(); i++) {
					temp = filters.get(i).apply(temp);
				}

				// After all the processing was done, save the image in bmp format
				bmpIO.write(temp, output.getAbsolutePath());

				// Show a summary of the execution process
				System.out.println("\nProcessing finished!");
				System.out.println("You can find the processed image at: " + output.getAbsolutePath() );
				System.out.println("\n\n---- SUMMARY ----");
				System.out.println("Parsing the input arguments took: " + argParser.getDuration() + " ms");
				System.out.println("Parsing the input file took: " + bmpIO.getDuration() + " ms");
				System.out.println("Building the filter list took: " + fBuilder.getDuration() + " ms");
				for (int i = 0; i < filters.size(); i++) {
					System.out.println("Applying the " + filters.get(i).getType() + " filter took: " + filters.get(i).getDuration() + " ms");
				}
				System.out.println("-----------------\n");
				return;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
	}
}