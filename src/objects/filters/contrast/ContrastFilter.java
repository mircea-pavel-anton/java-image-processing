package objects.filters.contrast;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class ContrastFilter extends GenericFilter {
	private double factor;

	/** Constructor
	 * Calculates the contrast factor via the formula:
	 *           259 * (level + 255)
	 *factor =  ----------------------
	 *           (255 * (259 - level)
	 * 
	 * @param contrastLevel -> level, from the above mentioned formula
	 */
	public ContrastFilter(double contrastLevel) {
		this.factor = 259 * (contrastLevel + 255) / ( 255 * (259 - contrastLevel) );
	}

	/** Applies the contrast filter on the image, by:
	 * - subtracting 128 from each color channel,
	 * - multiplying by the factor calculated in the constructor
	 * - adding back the 128
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image
	 */
	@Override
	public Image filter(Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				image.setPixelAt(
					x,
					y,
					pixel.subtract(128).multiply(factor).add(128)
				);
			}
		}
		return image;
	}

	/** Returns the type of filter. contrast, in this case */
	@Override
	public String getType() { return CONTRAST_FILTER; }
	
}
