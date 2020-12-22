package objects.filters.normalize;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class NormalizationFilter extends GenericFilter {
	/** Applies the normalization filter on the image, by setting each color channel value to 
	 * (color / channels_sum) * 255
	 * 
	 * For each color channel, we calculate the average value (@see getColorAverage)
	 * and standard deviation (@see getStandardDeviation)
	 * 
	 * And then, for each pixel:
	 *     R = (R / (R+G+B) ) * 255,
	 *     G = (G / (R+G+B) ) * 255,
	 *     B = (B / (R+G+B) ) * 255
	 * 
	 * @param image -> the image to be processed
	 * @return -> the normalized image
	 */
	@Override
	public Image filter(Image image) {
		// Set each pixel = (pixel - average) / standard_deviation
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				double total = pixel.getRed() + pixel.getBlue() + pixel.getGreen();
				pixel = pixel.divide(total).multiply(255).clip();
				image.setPixelAt(x, y, pixel);
			}
		}

		return image;
	}

	/** Returns the type of filter. normalize, in this case */
	@Override
	public String getType() { return NORMALIZATION_FILTER; }
	
}
