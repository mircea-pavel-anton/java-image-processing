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

	@Override
	public Image filter(Image image) {
		Pixel[][] pixels = image.getPixels();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				pixels[x][y] = linearTransform(pixels[x][y]);
			}
		}
		return new Image(pixels);
	}

	@Override
	public String getType() { return GRAY_LEVEL_FILTER; }
	
}
