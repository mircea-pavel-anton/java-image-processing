package objects.filters;

import objects.GenericJob;
import objects.image.Image;
import objects.singletons.Timer;

public class GrayscaleFilter extends GenericJob implements IFilter {

	@Override
	public Image apply(Image image) throws Exception{
		String identifier = getType() + getUID();
		Timer timer = Timer.getInstance();
		timer.startJob( identifier );
		Image filteredImage = filter(image);
		duration = timer.stopJob( identifier );
		return filteredImage;
	}

	@Override
	public Image filter(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() { return GRAYSCALE_FILTER; }

	public GrayscaleFilter() {}
}
