package objects.filters.zoom;

import objects.image.Pixel;

public class KTimesZoomFilter extends AbstractZoomFilter {
	/** Constructor, ensures the zoom level is correct. Throws an exception otherwise */
	public KTimesZoomFilter(final int zoomLevel) {
		if (zoomLevel > 0) {
			this.zoomLevel = zoomLevel;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * The actual zooming algorithm. How it works: Let K be the zoom level:
	 * 
	 * for each adjacent pixel, we call the bigger value max and smaller min: for i
	 * = 0 : K-1 pixel[x*K+i+1][y*K] = min + (max - min) / K * i
	 * 
	 * The algorithm is then repeated, looping through each column
	 * 
	 * For a more detailed explanation, see:
	 * https://www.tutorialspoint.com/dip/zooming_methods.htm
	 * 
	 * @param input -> the matrix we want to zoom
	 * @return -> the zoomed matrix
	 */
	@Override
	protected Pixel[][] zoom(final Pixel[][] input) {
		final int oldWidth = input.length;
		final int oldHeight = input[0].length;
		final int newWidth = zoomLevel * (oldWidth - 1) + 1;
		final int newHeight = zoomLevel * (oldHeight - 1) + 1;
		final Pixel[][] output = new Pixel[newWidth][newHeight];

		// Row wise zooming
		for (int y = 0; y < oldHeight; y++) {
			for (int x = 0; x < oldWidth; x++) {
				output[zoomLevel * x][zoomLevel * y] = input[x][y];

				for (int i = 0; i < zoomLevel - 1 && zoomLevel * x + i < newWidth && x + 1 < oldWidth; i++) {
					final double redX = input[x][y].getRed();
					final double greenX = input[x][y].getGreen();
					final double blueX = input[x][y].getBlue();
					final double redY = input[x + 1][y].getRed();
					final double greenY = input[x + 1][y].getGreen();
					final double blueY = input[x + 1][y].getBlue();
					output[zoomLevel * x + i + 1][zoomLevel * y] = new Pixel(
							(Math.min(redX, redY) + (Math.max(redX, redY) - Math.min(redX, redY)) / zoomLevel)
									* (i + 1),
							(Math.min(greenX, greenY)
									+ (Math.max(greenX, greenY) - Math.min(greenX, greenY)) / zoomLevel) * (i + 1),
							(Math.min(blueX, blueY) + (Math.max(blueX, blueY) - Math.min(blueX, blueY)) / zoomLevel)
									* (i + 1));
				}

			}
		}

		// Column wise zooming
		for (int x = 0; x < newWidth; x++) {
			for (int y = 0; y < newHeight; y++) {
				if (output[x][y] == null) {
					for (int i = 0; i < zoomLevel - 1 && y + zoomLevel - 1 < newHeight; i++) {
						final double redX = output[x][y - 1].getRed();
						final double greenX = output[x][y - 1].getGreen();
						final double blueX = output[x][y - 1].getBlue();
						final double redY = output[x][y + zoomLevel - 1].getRed();
						final double greenY = output[x][y + zoomLevel - 1].getGreen();
						final double blueY = output[x][y + zoomLevel - 1].getBlue();
						output[x][y+i] = new Pixel(
							( Math.min(redX, redY) + (Math.max(redX, redY) - Math.min(redX, redY)) / zoomLevel ) * (i+1),
							( Math.min(greenX, greenY) + (Math.max(greenX, greenY) - Math.min(greenX, greenY)) / zoomLevel ) * (i+1),
							( Math.min(blueX, blueY) + (Math.max(blueX, blueY) - Math.min(blueX, blueY)) / zoomLevel ) * (i+1)
						);
					}
				}
			}
		}
		
		return output;
	}
	
	@Override
	public String toString() { return K_TIMES_ZOOM_FILTER; }
}
