package objects.filters.grayscale;

import objects.image.Pixel;

public class WeightedGrayscaleFilter extends AbstractGrayscaleFilter {
	private static final double RED_BIAS = 0.21;
	private static final double GREEN_BIAS = 0.72;
	private static final double BLUE_BIAS = 0.07;

	/** Applies the  weighted grayscale method to turn a pixel into a grayscale pixel.
	 * 
	 * The formula used is: G = R * redBias + G * greenBias + B * blueBias
	 * where:
	 *   - red_bias = 0.21
	 *   - green_bias = 0.72
	 *   - blue_bias = 0.07
	 *   - G = the grayscale color
	 *   - R,G,B = the red, green, blue color values
	 * 
	 * @param input -> the pixel to be processed
	 * @return -> the grayscaled pixel
	 */
	@Override
	protected Pixel toGrayscale(final Pixel input) {
		double grayscale = 0;
		grayscale += input.getRed()   * RED_BIAS;
		grayscale += input.getGreen() * GREEN_BIAS;
		grayscale += input.getBlue()  * BLUE_BIAS;
		return new Pixel(grayscale);
	}

	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "()"; }

	@Override
	public String toString() { return WEIGHTED_GRAYSCALE_FILTER; }
}
