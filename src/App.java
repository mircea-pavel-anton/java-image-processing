import java.io.File;
import java.util.ArrayList;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.singletons.ArgParser;
import objects.singletons.BmpIO;
import objects.singletons.FilterBuilder;

public class App {
	public static void main(final String[] args) {
		try {
			// Get singleton instances
			final ArgParser argParser = ArgParser.getInstance();
			final BmpIO bmpIO = BmpIO.getInstance();
			final FilterBuilder fBuilder = FilterBuilder.getInstance();

			// Parse input arguments
			argParser.parseArguments(args);
			final File input = argParser.getInputFiles();
			final File output = argParser.getOutputFile();

			// If the input and output files have been set after parsing the arguments
			// The only way the code can reach this point and fail this condition, is
			// if the argument was '-h' or '--help'
			// otherwise, some exception would have been thrown
			if (input != null && output != null) {
				// Read the input image into a Image object
				Image temp = bmpIO.read();

				// Run the interactive builder
				fBuilder.run();

				// Retrieve the filter list from the builder
				final ArrayList<GenericFilter> filters = fBuilder.getFilters();

				// Run each filter from the list, in order
				// Supply each filter with the image produced by the previous one
				for (int i = 0; i < filters.size(); i++) {
					temp = filters.get(i).apply(temp);
				}

				// After all the processing was done, save the image in bmp format
				bmpIO.write(temp, output.getAbsolutePath());

				// Show a summary of the execution process
				System.out.println("\nProcessing finished!");
				System.out.println("You can find the processed image at: " + output.getAbsolutePath());
				System.out.println("\n\n---- SUMMARY ----");
				System.out.println("Parsing the input arguments took: " + argParser.getDuration() + " ms");
				System.out.println("Parsing the input file took: " + bmpIO.getDuration() + " ms");
				System.out.println("Building the filter list took: " + fBuilder.getDuration() + " ms");
				for (int i = 0; i < filters.size(); i++) {
					System.out.println(
						"Applying the " + 
						filters.get(i).describe() + 
						" filter took: " + 
						filters.get(i).getDuration() + 
						" ms"
					);
				}
				System.out.println("-----------------\n");
				return;
			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
	}
}