package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class NegativeFilter extends GenericFilter {

	@Override
	public Image filter(Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				pixel = new Pixel(
					255 - pixel.getRedChannel(),
					255 - pixel.getGreenChannel(),
					255 - pixel.getBlueChannel()
				);
				image.setPixelAt( x, y, pixel);
			}
		}
		return image;
	}

	@Override
	public String getType() { return NEGATIVE_FILTER; }
	
}
