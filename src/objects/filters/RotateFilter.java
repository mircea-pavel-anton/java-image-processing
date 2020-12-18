package objects.filters;

import objects.image.Image;
import objects.image.Pixel;

public class RotateFilter extends GenericFilter {
	private int rotationCount = 0;
	private boolean isClockwise;

	public RotateFilter(int rotationCount, boolean isClockwise) {
		this.rotationCount = rotationCount % 4;
		this.isClockwise = isClockwise;
	}


	private Pixel[][] rotate90Clockwise(Pixel[][] matrix) {
		int width = matrix.length;
		int height = matrix[0].length;
		
		Pixel[][] newMatrix = new Pixel[height][width];
		int newX = 0;
		int newY = 0;

		for (int y = height - 1; y >= 0; y--) {
			newY = 0;
			for (int x = 0; x < width; x++) {
				newMatrix[newX][newY++] = matrix[x][y];
			}
			newX++;
		}
		
		return newMatrix;
	}

	private Pixel[][] rotate90CounterClockwise(Pixel[][] matrix) {
		int width = matrix.length;
		int height = matrix[0].length;
		
		Pixel[][] newMatrix = new Pixel[height][width];
		int newX = 0;
		int newY = 0;

		for (int y = 0; y < height; y++) {
			newY = 0;
			for (int x = width-1; x >= 0; x--) {
				newMatrix[newX][newY++] = matrix[x][y];
			}
			newX++;
		}
		
		return newMatrix;
	}

	@Override
	public Image filter(Image image) {
		Pixel[][] pixels = image.getPixels();
		for (int i = 0; i < rotationCount; i++) {
			pixels = (isClockwise) ? rotate90Clockwise(pixels) : rotate90CounterClockwise(pixels);
		}
		return new Image(pixels);
	}

	@Override
	public String getType() { return ROTATE_FILTER; }
}
