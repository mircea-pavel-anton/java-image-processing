package objects.filters.histogram;

import java.util.Arrays;

import objects.image.Image;
import objects.image.Pixel;

public class GrayLevelHistogram extends AbstractHistogramFilter {
	private int width;
	private int height;
	private int samples;

	/** Constructor
	 * 
	 * @param width -> the width of the histogram image, in pixels
	 * @param height -> the height of the histogram image, in pixels
	 * @param samples -> the number of bars in the bar graph
	 */
	public GrayLevelHistogram(int width, int height, int samples) {
		super(samples, false);

		this.width = width;
		this.height = height;
		this.samples = samples;
	}

	/** Creates the histogram image
	 * Loops through all the pixels of the image, and draws the bars based on the y coordinate and the
	 * number of encounters, as reported by grayLevelHistogram
	 * 
	 * @param image -> the image we want the histogram of
	 * @return -> the histogram
	 */
	@Override
	public Image filter(Image image) {
		generate(image.getPixels(), false);
		int maxValue = Arrays.stream(grayLevelHistogram).max().getAsInt();
		Pixel[][] histogram = new Pixel[width][height];
		double xSize = (double)(width) / samples;
		double ySize = (double)maxValue / (height - 10);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ( y < grayLevelHistogram[(int)(x/xSize)] / ySize ) {
					int color = (int)Math.round(255.0 / samples * x/xSize);
					histogram[x][height-y-1] = new Pixel(color);
				} else {
					histogram[x][height-y-1] = new Pixel(255);
				}
			}
		}

		return new Image(histogram);
	}
}
