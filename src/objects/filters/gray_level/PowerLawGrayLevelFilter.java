package objects.filters.gray_level;

import objects.image.Pixel;

public class PowerLawGrayLevelFilter extends AbstractGrayLevelFilter {
	private double gamma;

	/** Constructor */
	public PowerLawGrayLevelFilter(double gamma) { this.gamma = gamma; }

	/** Applies the power-law transformation onto a pixel.
	 * The formula used is s = c * r^y, where
	 *   - s = the new color
	 *   - c = a constant
	 *   - r = the old color
	 *   - y = the gamma
	 * if y > 1 -> nth power method
	 * if y < 1 -> nth root method
	 * 
	 * @param input -> the pixel to be transformed
	 * @return -> the transformed pixel
	 */
	@Override
	protected Pixel transform(Pixel input) {
		return new Pixel(
			limit( 255 * Math.pow( input.getRedChannel() 	/ 255.0, gamma) ),
			limit( 255 * Math.pow( input.getGreenChannel()  / 255.0, gamma) ),
			limit( 255 * Math.pow( input.getBlueChannel()   / 255.0, gamma) )
		);
	}
}
