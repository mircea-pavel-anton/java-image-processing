package objects.filters.zoom;

import objects.image.Pixel;

public class ZeroOrderHoldZoomFilter extends AbstractZoomFilter {

	/** Constructor, sets zoomlevel to 2, as ZOH can only zoom to 2x */
	public ZeroOrderHoldZoomFilter() { this.zoomLevel = 2; }

	/** The actual zooming algorithm.
	 * For each 2 adjacent pixels, we place their half-sum in between
	 * 
	 * @param input -> the matrix to be zoomed
	 * @return -> the zoomed matrix
	 */
	@Override
	protected Pixel[][] zoom(final Pixel[][] input) {
		final int oldWidth = input.length;
		final int oldHeight = input[0].length;
		final int newWidth = 2 * oldWidth - 1;
		final int newHeight = 2 * oldHeight - 1;
		final Pixel[][] output = new Pixel[newWidth][newHeight];

		// Row wise zooming
		for (int y = 0; y < oldHeight; y++) {
			for (int x = 0; x < oldWidth; x++) {
				output[2*x][2*y] = input[x][y];

				if (2*x + 1 < newWidth) {
					output[2*x + 1][2*y] = input[x][y].add( input[x+1][y] ).divide(2);
				}
			}
		}

		// Column wise zooming
		for (int x = 0; x < newWidth; x++) {
			for (int y = 1; y < newHeight; y+=2) {
				output[x][y] = (output[x][y] == null) ? 
									output[x][y-1].add( output[x][y+1] ).divide(2) : 
									output[x][y];
			}
		}

		return output;
	}
	
	@Override
	public String toString() { return ZERO_ORDER_HOLD_ZOOM_FILTER; }
}
