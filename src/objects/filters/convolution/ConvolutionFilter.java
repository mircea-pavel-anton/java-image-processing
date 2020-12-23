package objects.filters.convolution;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class ConvolutionFilter extends GenericFilter {
	// The kernel used for matrix convolution
	private final double[][] kernel;
	private final String maskName;


	/** Constructor, sets the convolution kernel */
	public ConvolutionFilter(final double[][] kernel, final String maskName) {
		this.kernel = kernel;
		this.maskName = maskName;
	}

	/**
	 * Effectively calculates the convolution, by centering the matrix on each pixel
	 * and performing the multiplications and sums
	 * 
	 * For a better explanation on what matrix convolution is, see:
	 * https://docs.gimp.org/2.8/en/plug-in-convmatrix.html
	 * 
	 * @param input -> the pixel matrix, lhs of the convolution
	 * @return -> the result of the convolution
	 */
	private Pixel[][] conv(final Pixel[][] input) {
		final int width = input.length;
		final int height = input[0].length;
		final Pixel[][] output = new Pixel[width][height];
		final int kernelSize = kernel.length;

		// loop through each pixel
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Pixel newPixel = new Pixel(0);

				// If the kernel can't be centered on the pixel without getting out of the
				// matrix
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					newPixel = input[x][y].clone();
				} else {
					// This for basically puts the center of the kernel and the current pixel
					// on the same x and y value, and then loops through them, performing the
					// matrix[x][y] * kernel[x][y] and adding up all the values
					for (int i = -1 * kernelSize / 2; i <= kernelSize / 2; i++) {
						for (int j = -1 * kernelSize / 2; j <= kernelSize / 2; j++) {
							if (x + i >= 0 && x + i < width && y + j >= 0 && y + j < height) {
								// the pixel from the matrix
								final Pixel pixel = input[x + i][y + j].clone();

								// the value from the kernel
								final double coef = kernel[kernelSize / 2 - i][kernelSize / 2 - j];

								// pixel = pixel * value
								newPixel = newPixel.add(pixel.multiply(coef));
							}
						}
					}
				}
				// make sure colors stay within the 0,255 range
				output[x][y] = newPixel.clip();
			}
		}

		return output;
	}

	/**
	 * Applies the convolution mask on the given image
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image
	 */
	@Override
	public Image filter(final Image image) {
		return new Image( conv( image.getPixels() ) );
	}

	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "( " + maskName + " )"; }
	
	@Override
	public String toString() { return CONVOLUTION_FILTER; }
}
