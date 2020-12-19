package objects.filters.binary;

import objects.image.Pixel;

public class XORFilter extends AbstractBinaryFilter {
	/** Performs a bitwise XOR operation between each color channel of pixels a and b */
	@Override
	protected Pixel binOp(Pixel a, Pixel b) {
		return new Pixel(
			a.getRedChannel() ^ b.getRedChannel(),
			a.getGreenChannel() ^ b.getGreenChannel(),
			a.getBlueChannel() ^ b.getBlueChannel()
		);
	}
}
