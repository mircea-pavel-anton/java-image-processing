package objects.filters.normalize;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class NormalizationFilter extends GenericFilter {

	/** Calculates the average value of each color channel
	 * Each channel gets its average value calculated with the following formula:
	 * R = (pixel[i][j].getRedChannel() for each i,j) / N
	 * G = (pixel[i][j].getGreenChannel() for each i,j) / N
	 * B = (pixel[i][j].getBlueChannel() for each i,j) / N
	 * where N is the number of pixels in the image
	 * 
	 * @param pixels -> the image, as a matrix of pixels
	 * @param width -> the width of the image, in pixels
	 * @param height -> the height of the image in pixels
	 * @return -> an array, containing the averages: {red_avg, green_avg, blue_avg}
	 */
	private double[] getColorAverages(Pixel[][] pixels, int width, int height) {
		double[] average = {0, 0, 0}; // average = {red_avg, green_avg, blue_avg}
		double n = (double)width * height;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				average[0] += pixels[x][y].getRed() / n;
				average[1] += pixels[x][y].getGreen() / n;
				average[2] += pixels[x][y].getBlue() / n;
			}
		}

		return average;
	}

	/** Calculates the standard deviation for each color channel
	 * Each channel gets its standard deviation calculated with the following formula:
	 * R = sqrt( 1/(N-1) * Sum(pixel[i][j].getRedChannel() - average[red])^2 ) for each i,j
	 * G = sqrt( 1/(N-1) * Sum(pixel[i][j].getGreenChannel() - average[red])^2 ) for each i,j
	 * B = sqrt( 1/(N-1) * Sum(pixel[i][j].getBlueChannel() - average[red])^2 ) for each i,j
	 * where N is the number of pixels in the image
	 * 
	 * @param pixels -> the image, as a matrix of pixels
	 * @param width -> the width of the image, in pixels
	 * @param height -> the height of the image in pixels
	 * @param average -> the average value for each color channel ( @see getColorAverages() )
	 * @return -> an array, containing the deviations: {red_dev, green_dev, blue_dev}
	 */
	private double[] getStandardDeviation(Pixel[][] pixels, int width, int height, double[] average) {
		double[] stdDev = {0, 0, 0}; // average = {red_avg, green_avg, blue_avg}
		double n = (double)width * height;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				stdDev[0] += Math.pow( pixels[x][y].getRed() - average[0], 2 );
				stdDev[1] += Math.pow( pixels[x][y].getGreen() - average[1], 2 );
				stdDev[2] += Math.pow( pixels[x][y].getBlue() - average[2], 2 );
			}
		}
		stdDev[0] = Math.sqrt( 1/n * stdDev[0] );
		stdDev[1] = Math.sqrt( 1/n * stdDev[1] );
		stdDev[2] = Math.sqrt( 1/n * stdDev[2] );

		return stdDev;
	}

	/** Applies the normalization filter on the image, by setting each color channel value to 
	 * (value - average_value) / value_standard_deviation
	 * 
	 * For each color channel, we calculate the average value (@see getColorAverage)
	 * and standard deviation (@see getStandardDeviation)
	 * 
	 * And then, for each pixel:
	 *     R = (R - R_avg) / R_std_dev,
	 *     G = (G - G_avg) / G_std_dev,
	 *     B = (B - B_avg) / B_std_dev
	 * 
	 * @param image -> the image to be processed
	 * @return -> the normalized image
	 */
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

		// Set each pixel = (pixel - average) / standard_deviation
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				pixel = new Pixel(
					(int) (pixel.getRed() - average[0] / stdDev[0]),
					(int) (pixel.getGreen() - average[1] / stdDev[1]),
					(int) (pixel.getBlue() - average[2] / stdDev[2])
				);
				image.setPixelAt(x, y, pixel);
			}
		}

		return image;
	}

	/** Returns the type of filter. normalize, in this case */
	@Override
	public String getType() { return NORMALIZATION_FILTER; }
	
}
