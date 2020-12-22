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
	

	/** Ensures the given value stays within the bounds of a 1 byte int */
	private double limit(double value) {
		if (value > 255) return 255;
		if (value < 0) return 0;
		return value;
	}
	
	/** Applies the limit function to each color channel individually */
	public Pixel clip() {
		red = limit(red);
		green = limit(green);
		blue = limit(blue);
		return this;
	}

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

	// The following functions simulate the '*' operator
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
	
	
	// The following functions simulate the '/' operator
	public Pixel divide(int value) { return multiply(1.0 / value); }
	public Pixel divide(float value) { return multiply(1.0 / value); }
	public Pixel divide(double value) { return multiply(1.0 / value); }
	public Pixel divide(Pixel pixel) {
		return new Pixel(
			this.red / pixel.red,
			this.green / pixel.green,
			this.blue / pixel.blue
		);
	}

	// The following functions simulate the '+' operator
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

	// The following functions simulate the '-' operator
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

	// Applies the log function to each channel individualle
	public Pixel log() {
		return new Pixel(
			Math.log( red ),
			Math.log( green ),
			Math.log( blue )
		);
	}

	// Applies the pow(channel, exp) function to each channel individualle
	public Pixel pow(double exp) {
		return new Pixel(
			Math.pow( red, exp ),
			Math.pow( green, exp ),
			Math.pow( blue, exp )
		);
	}

	// Applies the bitwise AND operator between this and pixel
	public Pixel and(Pixel pixel){
		return new Pixel(
			(int)this.getRed()   & (int)pixel.getRed(),
			(int)this.getGreen() & (int)pixel.getGreen(),
			(int)this.getBlue()  & (int)pixel.getBlue()
		);
	}
	
	// Applies the bitwise AND operator between this and value
	public Pixel and(int value){
		return new Pixel(
			(int)this.getRed()   & value,
			(int)this.getGreen() & value,
			(int)this.getBlue()  & value
		);
	}

	// Applies the bitwise OR operator between this and pixel
	public Pixel or(Pixel pixel){
		return new Pixel(
			(int)this.getRed()   | (int)pixel.getRed(),
			(int)this.getGreen() | (int)pixel.getGreen(),
			(int)this.getBlue()  | (int)pixel.getBlue()
		);
	}
	
	// Applies the bitwise XOR operator between this and pixel
	public Pixel xor(Pixel pixel){
		return new Pixel(
			(int)this.getRed()   ^ (int)pixel.getRed(),
			(int)this.getGreen() ^ (int)pixel.getGreen(),
			(int)this.getBlue()  ^ (int)pixel.getBlue()
		);
	}

	// Creates a new instance of Pixel, equal with this one, in terms of color channels
	public Pixel clone() { return new Pixel( this.red, this.green, this.blue); }
}
