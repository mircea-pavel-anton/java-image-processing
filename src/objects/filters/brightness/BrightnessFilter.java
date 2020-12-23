package objects.filters.brightness;

import objects.filters.GenericFilter;
import objects.image.Image;

public class BrightnessFilter extends GenericFilter {
	private int brightness = 0;

	// Constructor
	public BrightnessFilter(final int brightness) {
		this.brightness = brightness;
	}

	/**
	 * Applies a brightness filter by adding the given brightness value to each
	 * color channel of each pixel. Basically, each pixel = 255 + pixel, for each
	 * color channel
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image
	 */
	@Override
	public Image filter(final Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				image.setPixelAt(
					x,
					y,
					image.getPixelAt(x, y).add(brightness).clip()
				);
			}
		}
		return image;
	}

	/** Returns the type of filter. brightness, in this case */
	@Override
	public String describe() { return toString() + "( " + brightness + " )"; }

	@Override
	public String toString() { return BRIGHTNESS_FILTER; }

}
