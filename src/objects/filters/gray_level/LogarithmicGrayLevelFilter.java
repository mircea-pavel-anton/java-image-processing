package objects.filters.gray_level;

import objects.image.Pixel;

public class LogarithmicGrayLevelFilter extends AbstractGrayLevelFilter {
	private final double arg;

	/** Constructor */
	public LogarithmicGrayLevelFilter(final double arg) {
		this.arg = arg;
	}

	/**
	 * Applies the logarithmic transformation onto a pixel. The formula used is s =
	 * c * log(r+1), where - s = the new color - c = a constant - r = the old color
	 * A 1 is added to the old color since log(0) is infinity
	 * 
	 * @param input -> the pixel to be transformed
	 * @return -> the transformed pixel
	 */
	@Override
	protected Pixel transform(final Pixel input) {
		return input.add(1).log().multiply(arg).clip();
	}

	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "( " + arg + " )"; }

	@Override
	public String toString() { return LOGARITHMIC_GRAY_LEVEL_FILTER; }
}
