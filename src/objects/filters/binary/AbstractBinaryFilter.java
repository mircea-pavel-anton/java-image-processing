package objects.filters.binary;

import objects.filters.GenericFilter;
import objects.image.Image;

public abstract class AbstractBinaryFilter extends GenericFilter {

	@Override
	public Image filter(Image[] image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() { return BINARY_FILTER; }
}
