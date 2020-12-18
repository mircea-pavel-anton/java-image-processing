package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class GrayscaleFilter extends GenericFilter {

	/** Applies the grayscale filter, using the average method.
	 * Loops through all pixels of the image, and sets the colors R=G=B=(R+G+B)/3
	 * 
	 * @param image -> the image to be processed
	 * @return -> the grayscale image
	 */
	@Override
	public Image filter(Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				int grayscale = 0;
				grayscale += ( pixel.getRedChannel() / 3 );
				grayscale += ( pixel.getGreenChannel() / 3 );
				grayscale += ( pixel.getBlueChannel() / 3 );
				image.setPixelAt(x, y, new Pixel(grayscale));
			}
		}
		return image;
	}

	/** Returns the type of filter. grayscale, in this case */
	@Override
	public String getType() { return GRAYSCALE_FILTER; }
}
