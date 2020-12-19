package objects.filters.translate;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class TranslateFilter extends GenericFilter {
	private int xDelta = 0; // number of pixels to translate horizontally
	private int yDelta = 0; // number of pixels to translate vertically
	private Pixel fillColor; // the color useed to fill in the blanks

	/** Constructor
	 * Ensures the given deltas for the x and y axis are positive integers.
	 * The color used to fill in the blanks is automatically set to black.
	 * 
	 * @param x -> number of pixels to translate on the x axis
	 * @param y -> number of pixels to translate on the y axis
	 */
	public TranslateFilter(int x, int y) {
		if (x >= 0 && y >= 0) {
			xDelta = x;
			yDelta = y;
			fillColor = new Pixel(0); // black
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** Constructor
	 * Ensures the given deltas for the x and y axis are positive integers.
	 * 
	 * @param x -> number of pixels to translate on the x axis
	 * @param y -> number of pixels to translate on the y axis
	 * @param color -> the Pixel used to fill in the blanks
	 */
	public TranslateFilter(int x, int y, Pixel color) {
		if (x >= 0 && y >= 0) {
			xDelta = x;
			yDelta = y;
			fillColor = color;
		}else {
			throw new IllegalArgumentException();
		}
	}

	/** Translates the image xDelta pixels to the right and yDelta pixels down
	 * All the newly created pixels are then set to the given color (fillColor)
	 * 
	 * @param image -> the image to be processed
	 * @return -> the translated image
	*/
	@Override
	public Image filter(Image[] images) {
		Image image = images[0];
		Pixel[][] translated = new Pixel[image.getWidth() + xDelta][image.getHeight() + yDelta];
		
		// Fill the gaps with the specified color
		for (int x = 0; x <  xDelta + image.getWidth(); x++) {
			for (int y = 0; y < yDelta + image.getHeight(); y++) {
				translated[x][y] = fillColor;
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

	/** Returns the type of filter. translate, in this case */
	@Override
	public String getType() { return TRANSLATE_FILTER; }
}
