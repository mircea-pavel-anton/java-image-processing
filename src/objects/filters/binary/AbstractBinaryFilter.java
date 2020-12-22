package objects.filters.binary;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public abstract class AbstractBinaryFilter extends GenericFilter {
	private Image lhsImage = null;
	
	/** "Caches" the second image */
	protected void loadImage(Image image) { this.lhsImage = image; }

	/** Checks whether or not the 2 images are matching in size */
	private boolean imageSizeMatch(Image a, Image b) {
		return a.getHeight() == b.getHeight() && a.getWidth() == b.getWidth();
	}

	/** The implementation of the actual binary operation needed for thee filter.
	 * 
	 * Implemented binary operations:
	 *   - AND
	 *   - OR
	 *   - XOR
	 * 
	 * @param a -> pixel a
	 * @param b -> pixel b
	 * @return -> a x b, where x is a binary operation
	 */
	protected abstract Pixel binOp(Pixel a, Pixel b);

	/**Applies the binary filter over the 2 images by looping through all of the pixels of each
	 * one, and performing the given binary operation between the corresponding pixels
	 * 
	 * @param images -> the images to be processed
	 * @return -> the resulting image, after the binary operation
	 */
	@Override
	public Image filter(Image rhsImage) {
		if (lhsImage == null || !imageSizeMatch(lhsImage, rhsImage)){
			throw new IllegalArgumentException();
		} else {
			int width = rhsImage.getWidth();
			int height = rhsImage.getHeight();
			Pixel[][] pixels = new Pixel[width][height];

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					pixels[x][y] = binOp(
						lhsImage.getPixelAt(x, y),
						rhsImage.getPixelAt(x, y)
					);
				}
			}

			return new Image(pixels);
		}
	}

	/** Returns the type of filter. binary, in this case */
	@Override
	public String getType() { return BINARY_FILTER; }
}
