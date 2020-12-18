package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class BrightnessFilter extends GenericFilter {
	private int brightness = 0;

	// Constructor
	public BrightnessFilter(int brightness) { this.brightness = brightness; }

	private int limit(int value) {
		if (value > 255) return 255;
		if (value < 0) return 0;
		return value;
	}

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

	@Override
	public String getType() { return BRIGHTNESS_FILTER; }
}
