package objects.filters.histogram;

import java.util.Arrays;

import objects.filters.GenericFilter;
import objects.image.Pixel;

public abstract class AbstractHistogramFilter extends GenericFilter {
	private final double sampleSize;
	protected int[] redLevelHistogram;
	protected int[] greenLevelHistogram;
	protected int[] blueLevelHistogram;
	protected int[] grayLevelHistogram;
	private String color;

	/**
	 * Generates the 1d arrays containing the number of times each shade has been
	 * encountered
	 * 
	 * @param pixels -> the matrix representing the image
	 * @param isRGB  -> whether the image is grayscaled or not
	 */
	protected void generate(final Pixel[][] pixels, final boolean isRGB) {
		final int width = pixels.length;
		final int height = pixels[0].length;

		// for each pixel in the image
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int index = 0;
				if (isRGB) {
					// set array[color_shade]++;
					// for each color channel
					index = (int) (pixels[x][y].getRed() / sampleSize);
					redLevelHistogram[index]++;

					index = (int) (pixels[x][y].getGreen() / sampleSize);
					greenLevelHistogram[index]++;

					index = (int) (pixels[x][y].getBlue() / sampleSize);
					blueLevelHistogram[index]++;
				} else {
					// if the image is grayscaled, all channels are equal
					// We can just pick one at random
					index = (int) (pixels[x][y].getRed() / sampleSize);
					grayLevelHistogram[index]++;
				}
			}
		}
	}

	/**
	 * Constructor Initializes the 1d arrays accordingly, if the image is grayscaled
	 * or not Sets the size of each sample, based on the number of samples required,
	 * such that all 256 possible values are accounted for
	 * 
	 * @param samples -> the number of bars to be shown in the histogram (bar graph)
	 * @param isRGB   -> whether the image is grayscaled or not
	 */
	protected AbstractHistogramFilter(final int samples, final boolean isRGB) {
		this.color = "";
		this.sampleSize = 256.0 / samples;
		if (isRGB) {
			redLevelHistogram = new int[samples];
			Arrays.fill(redLevelHistogram, 0);
			greenLevelHistogram = new int[samples];
			Arrays.fill(greenLevelHistogram, 0);
			blueLevelHistogram = new int[samples];
			Arrays.fill(blueLevelHistogram, 0);
			grayLevelHistogram = null;
		} else {
			this.redLevelHistogram = null;
			this.greenLevelHistogram = null;
			this.blueLevelHistogram = null;
			this.grayLevelHistogram = new int[samples];
			Arrays.fill(grayLevelHistogram, 0);
		}
	}

	/**
	 * Constructor, calls the previous one and also sets the color name for the
	 * describe method
	 */
	public AbstractHistogramFilter(final int samples, final boolean isRGB, final String color) {
		this(samples, isRGB);
		this.color = color;
	}

	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "( " + color + " )"; }

	@Override
	public String toString() { return HISTOGRAM_FILTER; }
}
