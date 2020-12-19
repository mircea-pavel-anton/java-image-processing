package objects.filters.zoom;

import objects.image.Pixel;

public class ZeroOrderHoldZoomFilter extends AbstractZoomFilter {

	@Override
	protected Pixel[][] zoom(Pixel[][] input) {
		int oldWidth = input.length;
		int oldHeight = input[0].length;
		int newWidth  = 2 * oldWidth - 1;
		int newHeight = 2 * oldHeight - 1;
		Pixel[][] output = new Pixel[newWidth][newHeight];

		// Row wise zooming
		for (int y = 0; y < oldHeight; y++) {
			for (int x = 0; x < oldWidth; x++) {
				output[2*x][2*y] = input[x][y];

				if (2*x + 1 < newWidth)
					output[2*x + 1][2*y] = new Pixel(
						( input[x][y].getRedChannel() +   input[x+1][y].getRedChannel() )   / 2,
						( input[x][y].getGreenChannel() + input[x+1][y].getGreenChannel() ) / 2,
						( input[x][y].getBlueChannel() +  input[x+1][y].getBlueChannel() )  / 2
					);
			}
		}

		// Column wise zooming
		for (int x = 0; x < newWidth; x++) {
			for (int y = 1; y < newHeight; y+=2) {
				if (output[x][y] == null) {
					output[x][y] = new Pixel(
						( output[x][y-1].getRedChannel() + output[x][y+1].getRedChannel() ) / 2,
						( output[x][y-1].getGreenChannel() + output[x][y+1].getGreenChannel() ) / 2,
						( output[x][y-1].getBlueChannel() + output[x][y+1].getBlueChannel() ) / 2
					);
				}
			}
		}

		return output;
	}
	
}
