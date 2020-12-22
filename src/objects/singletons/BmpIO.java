package objects.singletons;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import objects.image.Image;
import objects.image.Pixel;

public class BmpIO {
	// Singleton Instance
	private static BmpIO instance = null;

	/** If no instance has been created yet, create one and return it, otherwise, return the
	 * already existing one
	 * 
	 * @return an instance of BmpIO
	 */
	public static BmpIO getInstance() {
		if (instance == null) {
			instance = new BmpIO();
		}
		return instance;
	}

	/** Private constructor, to hide the default public one */
	private BmpIO() { }

	/** A function that reads the raw binary data from a BMP file
	 * 
	 * For a comprehensive explanation of the BMP file format, see:
	 * https://medium.com/sysf/bits-to-bitmaps-a-simple-walkthrough-of-bmp-image-format-765dc6857393
	 * 
	 * Long story short:
	 *   - first 2 bits specify the file type
	 *   - bits 28 and 29 represent the number of bits-per-pixel
	 *   - bits 10-13 represent the offset in bits to where the raw pixel data starts
	 *   - bits 18-21 contain the image width, in pixels
	 *   - bits 22-25 contain the image height, in pixels
	 *   - from offset to the end of the file we have the actual pixels, in BGR format
	 * 
	 * @param path -> path to the file
	 * @return -> the image as an Image object
	 * @throws Exception -> if the file is not readable, inexistent or otherwise inaccesible
	 */
	public Image read(String path) throws Exception{
		try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(path), 1024)) {
			int ch; // one byte worth of data from the file

			// A list of all the bytes
			ArrayList<Integer> fileHeader = new ArrayList<>();
			
			// Read all the bytes from the file and store them into the list
			while ((ch = reader.read()) != -1) { fileHeader.add( ch ); }

			// The first 2 bits in the bmp file format represent the File Type. They should be "BM" for BitMap
			String fileType = String.format("%c", fileHeader.get(0)) + String.format("%c", fileHeader.get(1));

			// The 28th and 29th bits represent the number of bits-per-pixel (bpp). We want 24
			String bppStr = String.format("%02X", fileHeader.get(29)) + String.format("%02X", fileHeader.get(28));
			int bpp = Integer.parseInt(bppStr, 16);

			// If the file is in the wrong format, throw an exception
			if (!fileType.equals("BM") || bpp != 24) {
				throw new Exception("File is not in 24-bit BMP format. BmpIO can't process it.");
			}

			// Bits 10-13 represent the offset in bits to where the raw pixel data starts
			String offsetStr = String.format("%02X", fileHeader.get(13)) + String.format("%02X", fileHeader.get(12)) + String.format("%02X", fileHeader.get(11)) + String.format("%02X", fileHeader.get(10));
			int offset = Integer.parseInt(offsetStr, 16);

			// Bits 18-21 contain the image width, in pixels
			String widthStr = String.format("%02X", fileHeader.get(21)) + String.format("%02X", fileHeader.get(20)) + String.format("%02X", fileHeader.get(19)) + String.format("%02X", fileHeader.get(18));
			int width = Integer.parseInt(widthStr, 16);

			// Bits 22-25 contain the image height, in pixels
			String heightStr = String.format("%02X", fileHeader.get(25)) + String.format("%02X", fileHeader.get(24)) + String.format("%02X", fileHeader.get(23)) + String.format("%02X", fileHeader.get(22));
			int height = Integer.parseInt(heightStr, 16);

			// The actual pixel matrix for the image
			Pixel[][] pixels = new Pixel[width][height];
			int index = offset; // the current position in the list. We start at the raw pixel data

			// Loop througl all the pixels, and read the bits (LSB first, so BGR, not RGB)
			// from the list
			for (int y = height-1; y >= 0; y--) {
				for (int x = 0; x < width; x++) {
					String blueStr = String.format("%02X", fileHeader.get(index++));
					int blue = Integer.parseInt(blueStr, 16);
					String greenStr = String.format("%02X", fileHeader.get(index++));
					int green = Integer.parseInt(greenStr, 16);
					String redStr = String.format("%02X", fileHeader.get(index++));
					int red = Integer.parseInt(redStr, 16);
					
					pixels[x][y] = new Pixel(red, green, blue);
				}
			}
			return new Image(pixels);
		}
	}
}
