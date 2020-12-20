package objects.filters.histogram;

import objects.filters.GenericFilter;
import objects.image.Pixel;

public abstract class AbstractHistogramFilter extends GenericFilter {
	private double sampleSize;
	protected int[] redLevelHistogram;
	protected int[] greenLevelHistogram;
	protected int[] blueLevelHistogram;
	protected int[] grayLevelHistogram;

	protected void generate(Pixel[][] pixels, boolean isRGB) {
		int width  = pixels.length;
		int height = pixels[0].length;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int index = 0;
				if (isRGB) {
					index = (int) (pixels[x][y].getRed() / sampleSize);
					redLevelHistogram[index]++;
					
					index = (int) (pixels[x][y].getGreen() / sampleSize);
					greenLevelHistogram[index]++;

					index = (int) (pixels[x][y].getBlue() / sampleSize);
					blueLevelHistogram[index]++;
				} else {
					index = (int) (pixels[x][y].getRed() / sampleSize);
					grayLevelHistogram[index]++;
				}
			}
		}
	}

	protected AbstractHistogramFilter(int samples, boolean isRGB) {
		this.sampleSize = 256.0 / samples;
		if (isRGB) {
			this.redLevelHistogram   = new int[samples];
			this.greenLevelHistogram = new int[samples];
			this.blueLevelHistogram  = new int[samples];
			this.grayLevelHistogram  = null;
		} else {
			this.redLevelHistogram   = null;
			this.greenLevelHistogram = null;
			this.blueLevelHistogram  = null;
			this.grayLevelHistogram  = new int[samples];
		}
	}

	@Override
	public String getType() { return HISTOGRAM_FILTER; }
}
