package objects.filters;

import objects.image.Image;

public class GrayscaleToBinaryFilter extends GenericFilter {)
	private int threshold;

	public GrayscaleToBinaryFilter(int threshold) {
		if (threshold < 256 && threshold >= 0) {
			this.threshold = threshold;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Image filter(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() { return GRAYSCALE_TO_BINARY_FILTER; }
	
}
