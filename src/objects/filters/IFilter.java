package objects.filters;

import objects.image.Image;

public interface IFilter {
	// All filter names as constants
	public static final String AND_FILTER = "Binary AND filter";
	public static final String OR_FILTER = "Binary OR filter";
	public static final String XOR_FILTER = "Binary XOR filter";
	public static final String BRIGHTNESS_FILTER = "Brightness filter";
	public static final String COLOR_DEPTH_REDUCTION_FILTER = "Color depth reduction filter";
	public static final String CONTRAST_FILTER = "Contrast Filter";
	public static final String CONVOLUTION_FILTER = "Convolution Filter";
	public static final String LINEAR_GRAY_LEVEL_FILTER = "Linear Gray Level Transform Filter";
	public static final String LOGARITHMIC_GRAY_LEVEL_FILTER = "Logarithmic Gray Level Transform Filter";
	public static final String POWER_LAW_GRAY_LEVEL_FILTER = "Power-Law Gray Level Transform Filter";
	public static final String AVERAGE_GRAYSCALE_FILTER = "Average Grayscale Filter";
	public static final String WEIGHTED_GRAYSCALE_FILTER = "Weighted Grayscale Filter";
	public static final String GRAYSCALE_TO_BINARY_FILTER = "Grayscale to Binary Filter";
	public static final String HISTOGRAM_FILTER = "Histogram Generator";
	public static final String NEGATIVE_FILTER = "Negative Filter";
	public static final String MIRROR_FILTER = "Mirror Filter";
	public static final String NORMALIZATION_FILTER = "Color Normalization Filter";
	public static final String ROTATE_FILTER = "Rotation Filter";
	public static final String TRANSLATE_FILTER = "Translation Filter";
	public static final String K_TIMES_ZOOM_FILTER = "K-Times Zoom Filter";
	public static final String PIXEL_REPLICATION_ZOOM_FILTER = "Pixel Replication Zoom Filter";
	public static final String ZERO_ORDER_HOLD_ZOOM_FILTER = "Zero Order Hold Zoom Filter";

	/** Runs the `filter` method and tracks execution time
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image, as returned by `filter`
	 */
	public Image apply(Image image) throws Exception;

	/** Applies the operations associated to the filter on the given image, and returns the result
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image
	 */
	public Image filter(Image image);

	/** Returns a string identifier for each specific kind of filter */
	public String describe();
}
