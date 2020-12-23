package objects.filters.binary;

import objects.image.Pixel;

public class ORFilter extends AbstractBinaryFilter {
	/** Performs a bitwise OR operation between each color channel of pixels a and b */
	@Override
	protected Pixel binOp(final Pixel a, final Pixel b) {
		return a.or(b);
	}

	@Override
	public String toString() { return OR_FILTER; }
}
