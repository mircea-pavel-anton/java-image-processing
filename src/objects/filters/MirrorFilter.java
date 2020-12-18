package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class MirrorFilter extends GenericFilter {
	// Mirroring options
	public static final int MIRROR_VERTICAL = 1; // mirrors on x axis
	public static final int MIRROR_HORIZONTAL = 2; // mirrors on y axis
	public static final int MIRROR_DIAGONAL = 3; // mirrors on both

	// The selected mirroring option
	private int mirroring = 0;

	/** Constructor
	 * Ensures the selected mirroring option is valid.
	 * 
	 * @param mirroring -> the selected mirroring option
	 */
	public MirrorFilter(int mirroring) {
		if (mirroring == MIRROR_VERTICAL || 
			mirroring == MIRROR_HORIZONTAL || 
			mirroring == MIRROR_DIAGONAL) {
			this.mirroring = mirroring;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** Mirrors the image by setting either x = width-x-1 or y=height-y-1, where x and y are pixel
	 * coordinates
	 * 
	 * MIRROR_VERTICAL only mirrors x, so x' = width -x - 1 and y' = y
	 * MIRROR_HORIZONTAL only mirrors y, so x' = x and y' = height - y - 1
	 * MIRROR_DIAGONAL mirrors both x and y, so x' = width -x - 1 and y' = height - y - 1
	 * 
	 * then, pixels'[x'][y'] = pixels[x][y]
	 * 
	 * @param image -> the image to be processed
	 * @return -> the mirrored image
	 */
	@Override
	public Image filter(Image image) {
		Pixel[][] mirrored = new Pixel[image.getWidth()][image.getHeight()];
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				final int newX = (mirroring == MIRROR_VERTICAL   || mirroring == MIRROR_DIAGONAL)
					? image.getWidth() - x - 1 
					: x;
				final int newY = (mirroring == MIRROR_HORIZONTAL || mirroring == MIRROR_DIAGONAL)
					? image.getHeight() - y - 1 
					: y;
				mirrored[newX][newY] = image.getPixelAt(x, y);
			}
		}
		return new Image(mirrored);
	}

	/** Returns the type of filter. mirror, in this case */
	@Override
	public String getType() { return MIRROR_FILTER; }
	
}
