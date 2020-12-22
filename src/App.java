import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.singletons.ArgParser;
import objects.singletons.FilterBuilder;

public class App {
	public static void main(String[] args) {
		ArgParser argParser = ArgParser.getInstance();
		try {
			argParser.parseArguments(args);
			File input = argParser.getInputFiles();
			File output = argParser.getOutputFile();

			if (input != null && output != null) {
				FilterBuilder fBuilder = FilterBuilder.getInstance();
				fBuilder.run();
				ArrayList<GenericFilter> filters = fBuilder.getFilters();
				Image temp = new Image(input.getAbsolutePath());
	
				for (int i = 0; i < filters.size(); i++) {
					temp = filters.get(i).apply(temp);
				}
				ImageIO.write(temp.toBufferedImage(), "bmp", output);
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}