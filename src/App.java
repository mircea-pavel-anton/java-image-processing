import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import objects.filters.GenericFilter;
import objects.filters.NegativeFilter;
import objects.image.Image;
import objects.singletons.ArgParser;

public class App {

	public static void main(String[] args) {
		ArgParser argParser = ArgParser.getInstance();
		try {
			argParser.parseArguments(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Args:");
		System.out.println(argParser.getInputFilePath());
		System.out.println(argParser.getOutputFilePath());
		System.out.println(argParser.getFilterType());

		try {
			Image image = new Image(argParser.getInputFilePath());
			GenericFilter filter = new NegativeFilter();
			Image filteredImage = filter.filter(image);
			ImageIO.write(filteredImage.toBufferedImage(), "bmp", new File(argParser.getOutputFilePath()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}