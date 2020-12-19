package objects.filters;

import objects.image.Image;

public interface IFilter {
	// All filter type names as constants
	public static final String GRAYSCALE_FILTER = "grayscale";
	public static final String NEGATIVE_FILTER = "negative";
	public static final String MIRROR_FILTER = "mirror";
	public static final String TRANSLATE_FILTER = "translate";
	public static final String GRAYSCALE_TO_BINARY_FILTER = "grayscale-to-binary";
	public static final String NORMALIZATION_FILTER = "normalize";
	public static final String BRIGHTNESS_FILTER = "brightness";
	public static final String ROTATE_FILTER = "rotate";
	public static final String CONTRAST_FILTER = "contrast";
	public static final String ZOOM_FILTER = "zoom";
	public static final String GRAY_LEVEL_FILTER = "gray-level";
	public static final String BINARY_FILTER = "binary";
	public static final String CONVOLUTION_FILTER = "convolution";

	/** Runs the `filter` method and tracks execution time
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image, as returned by `filter`
	 */
	public Image apply(Image[] image) throws Exception;

	/** Applies the operations associated to the filter on the given image, and returns the result
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image
	 */
	public Image filter(Image[] image);

	/** Returns a string identifier for each specific kind of filter */
	public String getType();
}
