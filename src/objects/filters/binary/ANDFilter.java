package objects.filters.binary;

import objects.image.Pixel;

public class ANDFilter extends AbstractBinaryFilter {

	@Override
	protected Pixel binOp(Pixel a, Pixel b) {
		return new Pixel(
			a.getRedChannel() & b.getRedChannel(),
			a.getGreenChannel() & b.getGreenChannel(),
			a.getBlueChannel() & b.getBlueChannel()
		);
	}
}
