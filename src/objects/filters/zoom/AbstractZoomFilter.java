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
	public Image filter(Image[] images) {
		Image image = images[0];
		return new Image( zoom(image.getPixels()) );
	}

	/** Returns the type of filter. zoom, in this case */
	@Override
	public String getType() { return ZOOM_FILTER; }
}
