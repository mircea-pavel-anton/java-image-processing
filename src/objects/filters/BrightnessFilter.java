package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class BrightnessFilter extends GenericFilter {
	private int brightness_delta = 0;

	public BrightnessFilter(int brightness_delta) {
		if (brightness_delta < 256 && brightness_delta > -256) {
			this.brightness_delta = brightness_delta;
		} else {
			throw new IllegalArgumentException();
		}
	}

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
					limit(pixel.getRedChannel() + brightness_delta),
					limit(pixel.getGreenChannel() + brightness_delta),
					limit(pixel.getBlueChannel() + brightness_delta)
				);
				image.setPixelAt( x, y, pixel);
			}
		}
		return image;
	}

	@Override
	public String getType() { return BRIGHTNESS_FILTER; }
}
