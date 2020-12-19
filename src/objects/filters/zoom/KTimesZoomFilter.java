package objects.filters.zoom;

import objects.image.Pixel;

public class KTimesZoomFilter extends AbstractZoomFilter {

	public KTimesZoomFilter(int zoomLevel) {
		if (zoomLevel > 0) {
			this.zoomLevel = zoomLevel;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected Pixel[][] zoom(Pixel[][] input) {
		int oldWidth  = input.length;
		int oldHeight = input[0].length;
		int newWidth  = zoomLevel * (oldWidth  - 1) + 1;
		int newHeight = zoomLevel * (oldHeight - 1) + 1;
		Pixel[][] output = new Pixel[newWidth][newHeight];

		// Row wise zooming
		for (int y = 0; y < oldHeight; y++) {
			for (int x = 0; x < oldWidth; x++) {
				output[zoomLevel*x][zoomLevel*y] = input[x][y];

				for (int i = 0; i < zoomLevel -1 && zoomLevel * x + i < newWidth && x + 1 < oldWidth; i++) {
					double redX   = input[x][y].getRed();
					double greenX = input[x][y].getGreen();
					double blueX  = input[x][y].getBlue();
					double redY   = input[x+1][y].getRed();
					double greenY = input[x+1][y].getGreen();
					double blueY  = input[x+1][y].getBlue();
					output[zoomLevel*x + i + 1][zoomLevel*y] = new Pixel(
						( Math.min(redX, redY) + (Math.max(redX, redY) - Math.min(redX, redY)) / zoomLevel ) * (i+1),
						( Math.min(greenX, greenY) + (Math.max(greenX, greenY) - Math.min(greenX, greenY)) / zoomLevel ) * (i+1),
						( Math.min(blueX, blueY) + (Math.max(blueX, blueY) - Math.min(blueX, blueY)) / zoomLevel ) * (i+1)
					);
				}

			}
		}

		// Column wise zooming
		for (int x = 0; x < newWidth; x++) {
			for (int y = 0; y < newHeight; y++) {
				if (output[x][y] == null) {
					for (int i = 0; i < zoomLevel -1 && y + zoomLevel - 1 < newHeight; i++) {
						double redX   = output[x][y-1].getRed();
						double greenX = output[x][y-1].getGreen();
						double blueX  = output[x][y-1].getBlue();
						double redY   = output[x][y + zoomLevel - 1].getRed();
						double greenY = output[x][y + zoomLevel - 1].getGreen();
						double blueY  = output[x][y + zoomLevel - 1].getBlue();
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
	
}
