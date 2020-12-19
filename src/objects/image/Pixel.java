package objects.image;

public class Pixel {
	// Color values for each channel
	private double red;
	private double green;
	private double blue;

	// Getters
	public double getRed() { return red; }
	public double getGreen() { return green; }
	public double getBlue()	{ return blue; }
	public int getRGB() { return ((int)red << 16 | (int)green << 8 | (int)blue); }

	// Setters
	public void setRed(double red) { this.red = red; }
	public void setGreen(double green) { this.green = green; }
	public void setBlue(double blue) { this.blue = blue; }
	
	// Constructors
	/** Sets each color channel to the specified intensity */
	public Pixel(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/** Sets all color channels to the same intensity => shades of gray */
	public Pixel(double monochrome) {
		this.red = monochrome;
		this.green = monochrome;
		this.blue = monochrome;
	}
	
	/** Creates a black pixel */
	public Pixel() {
		red = 0;
		green = 0;
		blue = 0;
	}

	// Operators overload
	public Pixel multiply(int value) { return multiply((double)value); }
	public Pixel multiply(float value) { return multiply((double)value); }
	public Pixel multiply(double value) { return multiply(new Pixel(value)); }
	public Pixel multiply(Pixel pixel) {
		return new Pixel(
			this.red * pixel.red,
			this.green * pixel.green,
			this.blue * pixel.blue
		);
	}

	public Pixel divide(int value) { return multiply(1 / value); }
	public Pixel divide(float value) { return multiply(1 / value); }
	public Pixel divide(double value) { return multiply(1 / value); }
	public Pixel divide(Pixel pixel) {
		return new Pixel(
			this.red / pixel.red,
			this.green / pixel.green,
			this.blue / pixel.blue
		);
	}

	public Pixel add(int value) { return add((double)value); }
	public Pixel add(float value) { return add((double)value); }
	public Pixel add(double value) {
		return new Pixel(
			red + value,
			green + value,
			blue + value
		);
	}
	public Pixel add(Pixel pixel) {
		return new Pixel(
			this.red + pixel.red,
			this.green + pixel.green,
			this.blue + pixel.blue
		);
	}

	public Pixel subtract(int value) { return add(-1 * value); }
	public Pixel subtract(float value) { return add(-1 * value); }
	public Pixel subtract(double value) { return add(-1 * value); }
	public Pixel subtract(Pixel pixel) {
		return new Pixel(
			this.red - pixel.red,
			this.green - pixel.green,
			this.blue - pixel.blue
		);
	}

	public Pixel log() {
		return new Pixel(
			Math.log( red ),
			Math.log( green ),
			Math.log( blue )
		);
	}
	public Pixel pow(double exp) {
		return new Pixel(
			Math.pow( red, exp ),
			Math.pow( green, exp ),
			Math.pow( blue, exp )
		);
	}

	public Pixel and(Pixel pixel){
		return new Pixel(
			(int)this.getRed()   & (int)pixel.getRed(),
			(int)this.getGreen() & (int)pixel.getGreen(),
			(int)this.getBlue()  & (int)pixel.getBlue()
		);
	}
	public Pixel or(Pixel pixel){
		return new Pixel(
			(int)this.getRed()   | (int)pixel.getRed(),
			(int)this.getGreen() | (int)pixel.getGreen(),
			(int)this.getBlue()  | (int)pixel.getBlue()
		);
	}
	public Pixel xor(Pixel pixel){
		return new Pixel(
			(int)this.getRed()   ^ (int)pixel.getRed(),
			(int)this.getGreen() ^ (int)pixel.getGreen(),
			(int)this.getBlue()  ^ (int)pixel.getBlue()
		);
	}
}
