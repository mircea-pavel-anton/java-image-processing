package objects.image;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	// Fields
	private int width; 			// image width in no. of pixels
	private int height;			// image height in no. of pixels
	private Pixel[][] pixels;	// image itself, as pixel matrixx

	// Getters
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public Pixel[][] getPixels() { return pixels; }
	public Pixel getPixelAt(int lin, int col) { return pixels[lin][col]; }

	// Setters
	public void setPixelAt(int lin, int col, Pixel pixel) { pixels[lin][col] = pixel; }

	// Constructors
	/** Creates a new Image object from an existing matrix of pixels
	 * 
	 * @param pixels = the matrix of pixels
	 */
	public Image(Pixel[][] pixels) {
		this.pixels = pixels.clone();
		this.width = this.pixels.length;
		this.height = this.pixels[0].length;
	}

	/** Creates a new Image object by parsing the image at the given path on disk.
	 * 
	 * @param path = the path to the image that will be read
	 * @throws IOException rethrows the exceptions from ImageIO.read()
	 */
	public Image(String path) throws IOException{
		// Read image from disk
		BufferedImage image = ImageIO.read( new FileInputStream(path) );

		// Get image dimensions
		width = image.getWidth();
		height = image.getHeight();

		// Create a blank matrix of pixels, with the given dimensions
		pixels = new Pixel[width][height];

		// Loop through all pixels and extract rgb values
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				pixels[i][j] = new Pixel(
					(rgb>>16)&0xff,
					(rgb>>8)&0xff,
					(rgb)&0xff
				);
			}
		}
	}
}
