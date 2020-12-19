package objects.filters.convolution;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public abstract class AbstractConvolutionFilter extends GenericFilter {
	double[][] kernel;

	private Pixel[][] conv(Pixel[][] input) {
		int width = input.length;
		int height = input[0].length;
		Pixel[][] output = new Pixel[width][height];
		int kernelSize = kernel.length;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Pixel newPixel = new Pixel(0);

				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					newPixel = input[x][y].clone();
				} else {
					for (int i = -1 * kernelSize/2; i <= kernelSize / 2; i++) {
						for (int j = -1 * kernelSize / 2; j <= kernelSize / 2; j++) {
							if (x + i >= 0 && x + i < width && y + j >= 0 && y + j < height) {
								Pixel pixel = input[x+i][y+j].clone();
								double coef = kernel[kernelSize/2 - i][kernelSize/2 - j];
								newPixel = newPixel.add( pixel.multiply(coef) );
								newPixel.multiply(1);
							}
						}
					}
				}

				output[x][y] = newPixel.clip();
			}
		}

		return output;
	}

	@Override
	public Image filter(Image[] images) {
		if (images.length < 1) {
			throw new IllegalArgumentException();
		} else {
			return new Image( conv( images[0].getPixels() ) );
		}
	}

	@Override
	public String getType() { return CONVOLUTION_FILTER; }
}