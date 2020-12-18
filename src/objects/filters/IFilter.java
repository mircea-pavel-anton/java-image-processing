package objects.filters;

import objects.image.Image;

public interface IFilter {
	// All filter type names as constants
	public static final String GRAYSCALE_FILTER = "grayscale";


	/** Runs the `filter` method and tracks execution time
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image, as returned by `filter`
	 */
	public Image apply(Image image);

	/** Applies the operations associated to the filter on the given image, and returns the result
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image
	 */
	public Image filter(Image image);

	/** Returns a string identifier for each specific kind of filter */
	public String getType();
}
