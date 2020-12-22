package objects.filters.gray_level;

import objects.image.Pixel;

public class LogarithmicGrayLevelFilter extends AbstractGrayLevelFilter {
	private double arg;

	/** Constructor */
	public LogarithmicGrayLevelFilter(double arg) { this.arg = arg; }

	/** Applies the logarithmic transformation onto a pixel.
	 * The formula used is s = c * log(r+1), where
	 *   - s = the new color
	 *   - c = a constant
	 *   - r = the old color
	 * A 1 is added to the old color since log(0) is infinity
	 * 
	 * @param input -> the pixel to be transformed
	 * @return -> the transformed pixel
	 */
	@Override
	protected Pixel transform(Pixel input) {
		return input.add(1).log().multiply(arg).clip();
	}
}
