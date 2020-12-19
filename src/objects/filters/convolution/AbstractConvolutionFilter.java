package objects.filters.convolution;

import objects.filters.GenericFilter;
import objects.image.Image;

public abstract class AbstractConvolutionFilter extends GenericFilter {

	@Override
	public Image filter(Image[] image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() { return CONVOLUTION_FILTER; }
}
