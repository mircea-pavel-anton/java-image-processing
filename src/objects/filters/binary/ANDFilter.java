package objects.filters.binary;

import objects.image.Pixel;

public class ANDFilter extends AbstractBinaryFilter {
	/** Performs a bitwise AND operation between each color channel of pixels a and b */
	@Override
	protected Pixel binOp(final Pixel a, final Pixel b) {
		return a.and(b);
	}

	@Override
	public String toString() { return AND_FILTER; }
}
