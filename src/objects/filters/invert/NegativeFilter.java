package objects.filters.invert;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class NegativeFilter extends GenericFilter {

	/** Applies the negative color filter by subtracting each color channel value from the max
	 * possible valu (255) for each pixel.
	 * 
	 * Basically, for each pixel:
	 *     R = 255 - R,
	 *     G = 255 - G,
	 *     B = 255 - B
	 * 
	 * @param image -> the image to be processed
	 * @return -> the inverted image
	 */
	@Override
	public Image filter(final Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				final Pixel pixel = image.getPixelAt(x, y);
				image.setPixelAt(
					x,
					y,
					new Pixel(255).subtract(pixel)
				);
			}
		}
		return image;
	}

	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "()"; }

	@Override
	public String toString() { return NEGATIVE_FILTER; }
	
}
