package objects.image;

public class Pixel {
	// Color values for each channel
	private int red;
	private int green;
	private int blue;

	// Getters
	public int getRedChannel() { return red; }
	public int getGreenChannel() { return green; }
	public int getBlueChannel()	{ return blue; }
	public int getRGB() { return (red << 16 | green << 8 | blue); }

	// Setters
	public void setRedChannel(int red) { this.red = red; }
	public void setGreenChannel(int green) { this.green = green; }
	public void setBlueChannel(int blue) { this.blue = blue; }
	
}
