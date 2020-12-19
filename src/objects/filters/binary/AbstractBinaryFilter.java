package objects.filters.binary;

import javax.naming.InsufficientResourcesException;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public abstract class AbstractBinaryFilter extends GenericFilter {


	private boolean imageSizeMatch(Image a, Image b) {
		return a.getHeight() == b.getHeight() && a.getWidth() == b.getWidth();
	}

	protected abstract Pixel binOp(Pixel a, Pixel b);

	@Override
	public Image filter(Image[] images) {
		if (images.length < 2 || !imageSizeMatch(images[0], images[1]) ) {
			throw new IllegalArgumentException();
		} else {
			int width = images[0].getWidth();
			int height = images[0].getHeight();
			Pixel[][] pixels = new Pixel[width][height];

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					pixels[x][y] = binOp(
						images[0].getPixelAt(x, y),
						images[1].getPixelAt(x, y)
					);
				}
			}

			return new Image(pixels);
		}
	}

	@Override
	public String getType() { return BINARY_FILTER; }
}
