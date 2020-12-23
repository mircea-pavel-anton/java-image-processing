package objects.filters;

import objects.GenericJob;
import objects.image.Image;
import objects.singletons.Timer;

public abstract class GenericFilter extends GenericJob implements IFilter {
	/** 
	 * Runs the `filter` method from the interface, and stores the execution time into the
	 * duration field, provided by GenericJob.
	 * 
	 * @return -> the filtered image, as returned by the `filter` method from IFilter
	 */
	@Override
	public Image apply(Image image) throws Exception{
		String identifier = describe() + getUID();
		Timer timer = Timer.getInstance();
		timer.startJob( identifier );
		Image filteredImage = filter(image);
		duration = timer.stopJob( identifier );
		return filteredImage;
	}

}
