package objects.filters;

import objects.GenericJob;
import objects.image.Image;
import objects.image.Pixel;
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
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				int grayscale = 0;
				grayscale += ( pixel.getRedChannel() / 3 );
				grayscale += ( pixel.getGreenChannel() / 3 );
				grayscale += ( pixel.getBlueChannel() / 3 );
				image.setPixelAt(x, y, new Pixel(grayscale));
			}
		}
		return image;
	}

	@Override
	public String getType() { return GRAYSCALE_FILTER; }

	public GrayscaleFilter() {}
}
