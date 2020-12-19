package objects.filters.rotate;

import objects.filters.GenericFilter;
import objects.image.Image;
import objects.image.Pixel;

public class RotateFilter extends GenericFilter {
	private int rotationCount = 0;
	private boolean isClockwise;

	/** Constructor
	 * Minimizes the number of rotations necessary by taking the modulo of 4, since 4 rotations
	 * make us go dull circle.
	 * 
	 * @param rotationCount -> number of rotations
	 * @param isClockwise -> direction of rotations
	 */
	public RotateFilter(int rotationCount, boolean isClockwise) {
		this.rotationCount = rotationCount % 4;
		this.isClockwise = isClockwise;
	}

	/** Rotates the matrix 90 degrees clockwise
	 * 	It creates a new matrix with 
	 *     newMatrix.width = matrix.height
	 *     newMatrix.height = matrix.width
	 * 
	 * Then, it start copying items from the top right corner down (from matrix) to thme
	 * top left corner to the right (in new matrix)
	 * 
	 * The direction of writing:
	 *     matrix   => newMatrix
	 *     * * * *     | * * * *
	 *     * * * *     v * * * *
	 *     * * * *  => * * * * *
	 *     * * * *     * * * * *
	 *     - > * *
	 * Then, it goes to the second-to-last row in matrix, and second column in newMatrix, and so on
	 * 
	 * @param matrix -> the matrix to be rotated
	 * @return -> the rotated matrix
	 */
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

	/** Rotates the matrix 90 degrees counter-clockwise
	 * 	It creates a new matrix with 
	 *     newMatrix.width = matrix.height
	 *     newMatrix.height = matrix.width
	 * 
	 * Then, it start copying items from the top right corner down (from matrix) to thme
	 * top left corner to the right (in new matrix)
	 * 
	 * The direction of writing:
	 *     matrix   => newMatrix
	 *     * * < -     | * * * *
	 *     * * * *     v * * * *
	 *     * * * *  => * * * * *
	 *     * * * *     * * * * *
	 *     * * * *
	 * Then, it goes to the second row in matrix, and second column in newMatrix, and so on
	 * 
	 * @param matrix -> the matrix to be rotated
	 * @return -> the rotated matrix
	 */
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

	/** Applies the rotation filter on the image, by spinning it 90 degrees, either clockwise or
	 * counter clockwise, oncer per rotationCount
	 * 
	 * @param image -> the image to be processed
	 * @return -> the rotated image
	 */
	@Override
	public Image filter(Image[] images) {
		Image image = images[0];
		Pixel[][] pixels = image.getPixels();
		for (int i = 0; i < rotationCount; i++) {
			pixels = (isClockwise) ? rotate90Clockwise(pixels) : rotate90CounterClockwise(pixels);
		}
		return new Image(pixels);
	}

	/** Returns the type of filter. rotate, in this case */
	@Override
	public String getType() { return ROTATE_FILTER; }
}
