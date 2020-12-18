package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class ZoomFilter extends GenericFilter {

	private int zoom;
	
	public ZoomFilter(int zoom) {
		if (zoom > 0) {
			this.zoom = zoom;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Pixel[][] pixelReplication(Pixel[][] original, int width, int height) {
		Pixel[][] pixels = new Pixel[width * zoom][height * zoom];

		for (int x = 0; x < width * zoom; x++) {
			for (int  y = 0; y < height * zoom; y++) {
				pixels[x][y] = original[x / zoom][y / zoom];
			}
		}
		return pixels;
	}

	@Override
	public Image filter(Image image) {
		return new Image(pixelReplication(image.getPixels(), image.getWidth(), image.getHeight()));
	}

	@Override
	public String getType() { return ZOOM_FILTER; }
}
