package objects.filters.binary;

import objects.image.Pixel;

public class ANDFilter extends AbstractBinaryFilter {
	/** Performs a bitwise AND operation between each color channel of pixels a and b */
	@Override
	protected Pixel binOp(Pixel a, Pixel b) { return a.and(b); }
}
