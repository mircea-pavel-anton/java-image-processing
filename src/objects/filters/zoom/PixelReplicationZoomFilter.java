package objects.filters.zoom;

import objects.image.Pixel;

public class PixelReplicationZoomFilter extends AbstractZoomFilter {
	public PixelReplicationZoomFilter(int zoomLevel) {
		if (zoomLevel > 0) {
			this.zoomLevel = zoomLevel;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** Pixel replication zooming algorithm.
	 * 
	 * Each pixel is transformed into a zoomLevel x zoomLevel matrix of identical pixels.
	 * No fancy logic is needed, as x/n and y/n only actually increment when x and y are
	 * have already traversed the n x n matrix.
	 * 
	 * @param input -> the original matrix
	 * @return -> the zoomed matrix
	 */
	@Override
	protected Pixel[][] zoom(Pixel[][] input) {
		int width  = input.length;
		int height = input[0].length;
		Pixel[][] pixels = new Pixel[width * zoomLevel][height * zoomLevel];

		for (int x = 0; x < width * zoomLevel; x++) {
			for (int  y = 0; y < height * zoomLevel; y++) {
				pixels[x][y] = input[x / zoomLevel][y / zoomLevel];
			}
		}

		return pixels;
	}
}
