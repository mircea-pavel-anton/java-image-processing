package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class GrayscaleFilter extends GenericFilter {
	// Available grayscale algorithms
	public static final int AVERAGE_METHOD = 1;
	public static final int WEIGHTED_METHOD = 2;

	// Selected algorithm
	private int method;

	/** No param constructor -> default method = average */
	public GrayscaleFilter() { this.method = AVERAGE_METHOD; }
	
	/** Constructor that sets the desired method
	 * Ensures the given method is one of the available ones.
	 * 
	 * @param method -> the desired grayscale alogirthm
	*/
	public GrayscaleFilter(int method) {
		if (method == AVERAGE_METHOD || method == WEIGHTED_METHOD) {
			this.method = method;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** Loops through all pixels of the image, and multiplies each color channel with its bias.
	 * 
	 * @param image -> the image to be processed
	 * @param redBias -> the amplification value for the red channel
	 * @param greenBias -> the amplification value for the green channel
	 * @param blueBias -> the amplification value for the blue channel
	 * @return -> the grayscale image
	 */
	private Image weightedFilter(Image image, double redBias, double greenBias, double blueBias) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				int grayscale = (int) (
					pixel.getRedChannel() * redBias +
					pixel.getGreenChannel() * greenBias +
					pixel.getBlueChannel() * blueBias
				
				);
				image.setPixelAt(x, y, new Pixel(grayscale));
			}
		}
		return image;
	}

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
		if (method == AVERAGE_METHOD) {
			return weightedFilter(image, (double) 1/3, (double) 1/3, (double) 1/3);
		}
		if (method == WEIGHTED_METHOD) {
				return weightedFilter(image, 0.21, 0.72, 0.07);
		}
		return null;
	}

	/** Returns the type of filter. grayscale, in this case */
	@Override
	public String getType() { return GRAYSCALE_FILTER; }
}
