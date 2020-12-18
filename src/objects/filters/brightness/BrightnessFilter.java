package objects.filters.brightness;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class BrightnessFilter extends GenericFilter {
	private int brightness = 0;

	// Constructor
	public BrightnessFilter(int brightness) { this.brightness = brightness; }

	/** Applies a brightness filter by adding the given brightness value to each color channel
	 * of each pixel.
	 * Basically, each pixel = 255 + pixel, for each color channel
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image
	 */
	@Override
	public Image filter(Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				pixel = new Pixel(
					limit(pixel.getRedChannel() + brightness),
					limit(pixel.getGreenChannel() + brightness),
					limit(pixel.getBlueChannel() + brightness)
				);
				image.setPixelAt( x, y, pixel);
			}
		}
		return image;
	}

	/** Returns the type of filter. brightness, in this case */
	@Override
	public String getType() { return BRIGHTNESS_FILTER; }
}
