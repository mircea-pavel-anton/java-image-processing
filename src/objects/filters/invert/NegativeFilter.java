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
	public Image filter(Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				pixel = new Pixel(
					255 - pixel.getRedChannel(),
					255 - pixel.getGreenChannel(),
					255 - pixel.getBlueChannel()
				);
				image.setPixelAt( x, y, pixel);
			}
		}
		return image;
	}

	/** Returns the type of filter. negative, in this case */
	@Override
	public String getType() { return NEGATIVE_FILTER; }
	
}
