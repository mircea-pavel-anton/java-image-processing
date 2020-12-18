package objects.filters.grayscale;

import objects.image.Pixel;

public class AverageGrayscaleFilter extends AbstractGrayscaleFilter {
	/** Applies the average grayscale method to turn a pixel into a grayscale pixel.
	 * 
	 * The formula used is: G = (R + G + B) / 3
	 * where:
	 *   - G = the grayscale color
	 *   - R,G,B = the red, green, blue color values
	 * 
	 * @param input -> the pixel to be processed
	 * @return -> the grayscaled pixel
	 */
	@Override
	protected Pixel toGrayscale(Pixel input) {
		int grayscale = 0;
		grayscale += input.getRedChannel()   / 3;
		grayscale += input.getGreenChannel() / 3;
		grayscale += input.getBlueChannel()  / 3;
		return new Pixel(grayscale);
	}
}
