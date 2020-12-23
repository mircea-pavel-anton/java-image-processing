package objects.filters.zoom;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public abstract class AbstractZoomFilter extends GenericFilter {
	protected int zoomLevel;

	/** The actual zooming algorithm, applied on the pixel matrix.
	 * 
	 * The implemented algorithms are:
	 *   - pixel replication
	 *   - zero order hold
	 *   - k times zooming
	 * 
	 * @param input -> the original matrix
	 * @return -> the zoomed matrix
	 */
	protected abstract Pixel[][] zoom(Pixel[][] input);

	/** Applies the zoom algorithm on a given image.
	 * 
	 * @param image -> the image to be processed
	 * @return -> the zoomed image
	 */
	@Override
	public Image filter(final Image image) {
		return new Image( zoom(image.getPixels()) );
	}
	
	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "( " + zoomLevel + " )"; }
}
