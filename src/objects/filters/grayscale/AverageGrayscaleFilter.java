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
	protected Pixel toGrayscale(final Pixel input) {
		double grayscale = 0;
		grayscale += input.getRed()   / 3;
		grayscale += input.getGreen() / 3;
		grayscale += input.getBlue()  / 3;
		return new Pixel(grayscale);
	}

	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "()"; }

	@Override
	public String toString() { return AVERAGE_GRAYSCALE_FILTER; }
}
