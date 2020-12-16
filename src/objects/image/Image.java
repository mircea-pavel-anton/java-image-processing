package objects.image;

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
}
