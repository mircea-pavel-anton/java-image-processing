package objects.filters;

import objects.GenericJob;
import objects.image.Image;
import objects.singletons.Timer;

public abstract class GenericFilter extends GenericJob implements IFilter {
	/** Ensures the given value stays within the bounds of a 1 byte int */
	protected int limit(int value) {
		if (value > 255) return 255;
		if (value < 0) return 0;
		return value;
	}
	protected int limit(float value) { return limit( (int)value ); }
	protected int limit(double value) { return limit( (int)value ); }

	/** 
	 * Runs the `filter` method from the interface, and stores the execution time into the
	 * duration field, provided by GenericJob.
	 * 
	 * @return -> the filtered image, as returned by the `filter` method from IFilter
	 */
	@Override
	public Image apply(Image image) throws Exception{
		String identifier = getType() + getUID();
		Timer timer = Timer.getInstance();
		timer.startJob( identifier );
		Image filteredImage = filter(image);
		duration = timer.stopJob( identifier );
		return filteredImage;
	}

}
