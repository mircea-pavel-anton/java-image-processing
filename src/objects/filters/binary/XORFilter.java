package objects.filters.binary;

import objects.image.Pixel;

public class XORFilter extends AbstractBinaryFilter {
	/** Performs a bitwise XOR operation between each color channel of pixels a and b */
	@Override
	protected Pixel binOp(final Pixel a, final Pixel b) {
		return a.xor(b);
	}

	@Override
	public String toString() { return XOR_FILTER; }
}
