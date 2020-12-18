package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class GrayscaleToBinaryFilter extends GenericFilter {
	private int threshold;

	public GrayscaleToBinaryFilter(int threshold) {
		if (threshold < 256 && threshold >= 0) {
			this.threshold = threshold;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Image filter(Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				if (pixel.getBlueChannel() < threshold) {
					image.setPixelAt(x, y, new Pixel(0));
				} else {
					image.setPixelAt(x, y, new Pixel(255));
				}
			}
		}
		return image;
	}

	@Override
	public String getType() { return GRAYSCALE_TO_BINARY_FILTER; }
	
}
