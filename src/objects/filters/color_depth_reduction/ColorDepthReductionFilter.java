package objects.filters.color_depth_reduction;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class ColorDepthReductionFilter extends GenericFilter {
	// Values for the right hand side of the bitwise AND
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

	/** Constructor
	 * Selects the value used for the & operation, based on the number of bits we want to keep
	 * and based on the direction (MSB to LSB or LSB to MSB)
	 */
	public ColorDepthReductionFilter(final int bits, final boolean isMSB) {
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

	/**
	 * Applies the color depth reduction filter on the given image It performs a
	 * bitwise AND operation between each pixels color channel and the selected
	 * value from the constructor
	 * 
	 * @param image -> the image to be processed
	 * @return -> the processed image
	 */
	@Override
	public Image filter(final Image image) {
		final Pixel[][] pixels = image.getPixels();

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixels[x][y] = pixels[x][y].and(value);
			}
		}

		return new Image(pixels);
	}


	/** Returns a human-readable filter description */
	@Override
	public String describe() { return toString() + "( " + value + " )"; }

	@Override
	public String toString() { return COLOR_DEPTH_REDUCTION_FILTER; }
}
