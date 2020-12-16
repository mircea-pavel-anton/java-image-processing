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
	public Image(Pixel[][] pixels) {
		this.pixels = pixels.clone();
		this.width = this.pixels.length;
		this.height = this.pixels[0].length;
	}
	public Image(String path) throws IOException{
		BufferedImage image = ImageIO.read( new FileInputStream(path) );
		width = image.getWidth();
		height = image.getHeight();
		pixels = new Pixel[width][height];

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
