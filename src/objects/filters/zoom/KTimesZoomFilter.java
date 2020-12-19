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
					int redX   = input[x][y].getRedChannel();
					int greenX = input[x][y].getGreenChannel();
					int blueX  = input[x][y].getBlueChannel();
					int redY   = input[x+1][y].getRedChannel();
					int greenY = input[x+1][y].getGreenChannel();
					int blueY  = input[x+1][y].getBlueChannel();
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
						int redX   = output[x][y-1].getRedChannel();
						int greenX = output[x][y-1].getGreenChannel();
						int blueX  = output[x][y-1].getBlueChannel();
						int redY   = output[x][y + zoomLevel - 1].getRedChannel();
						int greenY = output[x][y + zoomLevel - 1].getGreenChannel();
						int blueY  = output[x][y + zoomLevel - 1].getBlueChannel();
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
