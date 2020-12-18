package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class TranslateFilter extends GenericFilter {
	private int xDelta = 0; // number of pixels to translate horizontally
	private int yDelta = 0; // number of pixels to translate vertically

	@Override
	public Image filter(Image image) {
		Pixel[][] translated = new Pixel[image.getWidth() + xDelta][image.getHeight() + yDelta];
		
		// Fill the gaps with the specified color
		for (int x = 0; x <  xDelta + image.getWidth(); x++) {
			for (int y = 0; y < yDelta + image.getHeight(); y++) {
				translated[x][y] = new Pixel(0);
			}
		}

		// Copy the image, starting from xDelta, yDelta
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int newX = x + xDelta;
				int newY = y + yDelta;
				translated[newX][newY] = image.getPixelAt(x, y);
			}
		}
		return new Image(translated);
	}

	@Override
	public String getType() { return TRANSLATE_FILTER; }
}
