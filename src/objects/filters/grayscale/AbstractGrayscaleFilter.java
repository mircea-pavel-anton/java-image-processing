package objects.filters.grayscale;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public abstract class AbstractGrayscaleFilter extends GenericFilter {	
	/** The actual grayscale algorithm applied on a per-pixel basis 
	 * 
	 * @param input -> the pixel to be processed
	 * @return -> the grayscaled pixel
	*/
	protected abstract Pixel toGrayscale(Pixel input);

	/** Applies one of the available grayscale algorithms on the image, by setting different values
	 * for each color channel bias.
	 * 
	 * For the average method, all bias values are 1/3
	 * For the weighted method:
	 *     red_bias = 0.21
	 *     green_bias = 0.72
	 *     blue_bias = 0.07
	 * 
	 * @param image -> the image to be processed
	 * @return -> the grayscale image
	 */
	@Override
	public Image filter(Image image) {
		Pixel[][] pixels = image.getPixels();

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixels[x][y] = toGrayscale(pixels[x][y]);
			}
		}
		
		return new Image(pixels);
	}

	/** Returns the type of filter. grayscale, in this case */
	@Override
	public String getType() { return GRAYSCALE_FILTER; }
}
