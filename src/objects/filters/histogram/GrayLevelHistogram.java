package objects.filters.histogram;

import java.util.Arrays;

import objects.image.Image;
import objects.image.Pixel;

public class GrayLevelHistogram extends AbstractHistogramFilter {
	private int width;
	private int height;
	private int samples;

	public GrayLevelHistogram(int width, int height, int samples) {
		super(samples, false);

		this.width = width;
		this.height = height;
		this.samples = samples;
	}

	@Override
	public Image filter(Image[] image) {
		generate(image[0].getPixels(), false);
		int maxValue = Arrays.stream(grayLevelHistogram).max().getAsInt();
		Pixel[][] histogram = new Pixel[width][height];
		double xSize = (double)(width) / samples;
		double ySize = (double)maxValue / (height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ( y < blueLevelHistogram[(int)(x/xSize)] / ySize ) {
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
