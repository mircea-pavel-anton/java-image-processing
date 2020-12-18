package objects.filters.zoom;

import objects.image.Pixel;

public class PixelReplicationZoomFilter extends AbstractZoomFilter {


	/** Pixel replication zooming algorithm.
	 * 
	 * Each pixel is transformed into a zoomLevel x zoomLevel matrix of identical pixels.
	 * No fancy logic is needed, as x/n and y/n only actually increment when x and y are
	 * have already traversed the n x n matrix.
	 * 
	 * @param original -> the original matrix
	 * @return -> the zoomed matrix
	 */
	@Override
	protected Pixel[][] zoom(Pixel[][] original) {
		int width  = original.length;
		int height = original[0].length;
		Pixel[][] pixels = new Pixel[width * zoomLevel][height * zoomLevel];

		for (int x = 0; x < width * zoomLevel; x++) {
			for (int  y = 0; y < height * zoomLevel; y++) {
				pixels[x][y] = original[x / zoomLevel][y / zoomLevel];
			}
		}

		return pixels;
	}
}
