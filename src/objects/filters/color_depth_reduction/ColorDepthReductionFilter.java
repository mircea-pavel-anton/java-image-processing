package objects.filters.color_depth_reduction;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class ColorDepthReductionFilter extends GenericFilter {
	private static final byte MSB1 = 0b1000000;
	private static final byte MSB2 = 0b1100000;
	private static final byte MSB3 = 0b1110000;
	private static final byte MSB4 = 0b1111000;
	private static final byte MSB5 = 0b1111100;
	private static final byte MSB6 = 0b1111110;
	private static final byte LSB1 = 0b0000001;
	private static final byte LSB2 = 0b0000011;
	private static final byte LSB3 = 0b0000111;
	private static final byte LSB4 = 0b0001111;
	private static final byte LSB5 = 0b0011111;
	private static final byte LSB6 = 0b0111111;
	private byte value;

	public ColorDepthReductionFilter(int bits, boolean isMSB) { 
		switch (bits) {
			case 0:
				this.value = 0b00000000;
				break;
			case 1:
				this.value = (isMSB) ? MSB1 : LSB1;
				break;
			case 2:
				this.value = (isMSB) ? MSB2 : LSB2;
				break;
			case 3:
				this.value = (isMSB) ? MSB3 : LSB3;
				break;
			case 4:
				this.value = (isMSB) ? MSB4 : LSB4;
				break;
			case 5:
				this.value = (isMSB) ? MSB5 : LSB5;
				break;
			case 6:
				this.value = (isMSB) ? MSB6 : LSB6;
				break;
			default:
				throw new IllegalArgumentException();
		}
	 }

	@Override
	public Image filter(Image[] image) {
		Pixel[][] pixels = image[0].getPixels();

		for (int x = 0; x < image[0].getWidth(); x++) {
			for (int y = 0; y < image[0].getHeight(); y++) {
				pixels[x][y] = pixels[x][y].and(value);
			}
		}

		return new Image(pixels);
	}

	@Override
	public String getType() { return COLOR_DEPTH_REDUCTION_FILTER; }
}
