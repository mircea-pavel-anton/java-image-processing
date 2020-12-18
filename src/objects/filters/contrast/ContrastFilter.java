package objects.filters.contrast;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class ContrastFilter extends GenericFilter {
	private double factor;

	public ContrastFilter(double contrastLevel) {
		this.factor = 259 * (contrastLevel + 255) / ( 255 * (259 - contrastLevel) );
	}

	@Override
	public Image filter(Image image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Pixel pixel = image.getPixelAt(x, y);
				pixel = new Pixel(
					limit( factor * (pixel.getRedChannel() - 128) + 128 ),
					limit( factor * (pixel.getGreenChannel() - 128) + 128 ),
					limit( factor * (pixel.getBlueChannel() - 128) + 128 )
				);
				image.setPixelAt( x, y, pixel);
			}
		}
		return image;
	}

	@Override
	public String getType() { return CONTRAST_FILTER; }
	
}
