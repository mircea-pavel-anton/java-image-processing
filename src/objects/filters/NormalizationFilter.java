package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class NormalizationFilter extends GenericFilter {


	private double[] getColorAverages(Pixel[][] pixels, int width, int height) {
		double[] average = {0, 0, 0}; // average = {red_avg, green_avg, blue_avg}
		double n = (double)width * height;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				average[0] += pixels[x][y].getRedChannel() / n;
				average[1] += pixels[x][y].getGreenChannel() / n;
				average[2] += pixels[x][y].getBlueChannel() / n;
			}
		}

		return average;
	}

	private double[] getStandardDeviation(Pixel[][] pixels, int width, int height, double[] average) {
		double[] stdDev = {0, 0, 0}; // average = {red_avg, green_avg, blue_avg}
		double n = (double)width * height;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				stdDev[0] += Math.pow( pixels[x][y].getRedChannel() - average[0], 2 );
				stdDev[1] += Math.pow( pixels[x][y].getGreenChannel() - average[1], 2 );
				stdDev[2] += Math.pow( pixels[x][y].getBlueChannel() - average[2], 2 );
			}
		}
		stdDev[0] = Math.sqrt( 1/n * stdDev[0] );
		stdDev[1] = Math.sqrt( 1/n * stdDev[1] );
		stdDev[2] = Math.sqrt( 1/n * stdDev[2] );

		return stdDev;
	}

	@Override
	public Image filter(Image image) {
		double[] average = getColorAverages(
			image.getPixels(),
			image.getWidth(),
			image.getHeight()
		); // red, green, blue
		double[] stdDev = getStandardDeviation(
			image.getPixels(),
			image.getWidth(),
			image.getHeight(),
			average
		); // red, green, blue

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				pixel = new Pixel(
					(int) (pixel.getRedChannel() - average[0] / stdDev[0]),
					(int) (pixel.getGreenChannel() - average[1] / stdDev[1]),
					(int) (pixel.getBlueChannel() - average[2] / stdDev[2])
				);
				image.setPixelAt(x, y, pixel);
			}
		}

		return image;
	}

	@Override
	public String getType() { return NORMALIZATION_FILTER; }
	
}
