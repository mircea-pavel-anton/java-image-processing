package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class GrayLevelFilter extends GenericFilter {

	private Pixel linearTransform(Pixel input) {
		return new Pixel(
			255 - input.getRedChannel(),
			255 - input.getGreenChannel(),
			255 - input.getBlueChannel()
		);
	}

	private Pixel logarithmicTransform(Pixel input) {
		double c = 50;
		return new Pixel(
			limit( c * Math.log( (double)input.getRedChannel() + 1 ) ),
			limit( c * Math.log( (double)input.getGreenChannel() + 1 ) ),
			limit( c * Math.log( (double)input.getBlueChannel() + 1 ) )
		);
	}

	@Override
	public Image filter(Image image) {
		Pixel[][] pixels = image.getPixels();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixels[x][y] = logarithmicTransform(pixels[x][y]);
			}
		}
		return new Image(pixels);
	}

	@Override
	public String getType() { return GRAY_LEVEL_FILTER; }
	
}
