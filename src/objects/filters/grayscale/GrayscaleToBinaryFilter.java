package objects.filters.grayscale;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class GrayscaleToBinaryFilter extends GenericFilter {
	private int threshold;

	/** Consructor
	 * Ensures the given threshold is within the bounds of a 1 byte integer,
	 * as the filter would otherwise produce all black or all white images
	 * 
	 * @param threshold -> the threshold for converting colors to either blac or white
	 */
	public GrayscaleToBinaryFilter(final int threshold) {
		if (threshold < 256 && threshold >= 0) {
			this.threshold = threshold;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Applies the grayscale to binary filter, by comparing all pixels to the
	 * threshold if (pixel < threshold for any color channel) pixel = black else
	 * pixel = white
	 * 
	 * @param image -> the image to be processed
	 * @return -> the binary image
	 */
	@Override
	public Image filter(final Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				final Pixel pixel = image.getPixelAt(x, y);
				if (pixel.getBlue() < threshold) {
					image.setPixelAt(x, y, new Pixel(0));
				} else {
					image.setPixelAt(x, y, new Pixel(255));
				}
			}
		}
		return image;
	}

	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "( " + threshold + " )"; }

	@Override
	public String toString() { return GRAYSCALE_TO_BINARY_FILTER; }
	
}
