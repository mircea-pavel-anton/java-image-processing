package objects.filters;

import objects.GenericJob;
import objects.image.Image;

public class GrayscaleFilter extends GenericJob implements IFilter {

	@Override
	public Image apply(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image filter(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() { return GRAYSCALE_FILTER; }

	public GrayscaleFilter() {
		
	}
}
