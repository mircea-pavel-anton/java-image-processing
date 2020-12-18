package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class MirrorFilter extends GenericFilter {
	// Mirroring options
	public static final int MIRROR_VERTICAL = 1; // mirrors on x axis
	public static final int MIRROR_HORIZONTAL = 2; // mirrors on y axis
	public static final int MIRROR_DIAGONAL = 3; // mirrors on both

	private int mirroring = 0;

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

	@Override
	public String getType() { return MIRROR_FILTER; }
	
}
