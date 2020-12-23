package objects.filters.gray_level;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public abstract class AbstractGrayLevelFilter extends GenericFilter {
	/** The actual transformation function applied to the pixels.
	 * This function is meant to be overridden in any class that inherits from this one.
	 * 
	 * The implemented transforms are:
	 *    - linear
	 *    - logarithmic
	 *    - power-law 
	 * @param input -> the input pixel
	 * @return -> the transformed pixel
	 */
	protected abstract Pixel transform(Pixel input);

	/** Applies a given gray-level transform on the given image, by looping through all of the
	 * pixels and calling the transform function on them
	 * 
	 * @param image -> the image to be processed
	 * @return -> the gray-level adjusted image
	 */
	@Override
	public Image filter(final Image image) {
		final Pixel[][] pixels = image.getPixels();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixels[x][y] = transform(pixels[x][y]);
			}
		}
		return new Image(pixels);
	}
}
