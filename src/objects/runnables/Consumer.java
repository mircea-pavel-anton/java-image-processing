package objects.runnables;

import java.util.concurrent.BlockingQueue;

import objects.image.BitmapImage;
import objects.image.Pixel;

public class Consumer implements Runnable {
	private final BlockingQueue<Integer> bQueue;
	private final BitmapImage bitmapImage;
	private int charCount; // byte counter
	private String temp; // temporary string, used to convert multi-byte values to String/Int
	private int x; // x axis within the pixel matrix
	private int y; // y axis within the pixel matrix
	private int character; // the character from the queue
	private boolean isDone; // whether this thread is done or not

	public Consumer(final BlockingQueue<Integer> queue, final BitmapImage image) {
		this.bQueue = queue;
		this.bitmapImage = image;

		// initialize values
		charCount = 0;
		temp = "";
		isDone = false;
	}

	/**
	 * The first state of the modelled FSM that handles parsing the Raw Pixel Data
	 * The FSM models a nested for loop, that executes one iteration per extraction
	 * from the BlockingQueue
	 * 
	 * Since this is the first state, we also need to initailize the pixel in our
	 * matrix. In this step, we read the first of 3 (or 4) bytes that make up our
	 * pixel data This first byte represents the inensity of the blue color As such,
	 * we create a new pixel, and set the blue channel = character
	 */
	private void FSM_State1() {
		bitmapImage.setPixelAt(x, y, new Pixel(character));
	}

	/**
	 * The second state of the modelled FSM that handles parsing the Raw Pixel Data
	 * The FSM models a nested for loop, that executes one iteration per extraction
	 * from the BlockingQueue
	 * 
	 * The second state handles the second byte, which represents the intensity
	 * value for the green color No further actions are required, other than setting
	 * the green color channel of our pixel = character
	 */
	private void FSM_State2() {
		bitmapImage.getPixelAt(x, y).setGreen(character);
	}

	/**
	 * The third state of the modelled FSM that handles parsing the Raw Pixel Data
	 * The FSM models a nested for loop, that executes one iteration per extraction
	 * from the BlockingQueue
	 * 
	 * The third state handles the third byte, which represents the intensity value
	 * for the red color, so we set the pixel's red channel value to be equal to
	 * character
	 * 
	 * Other than that, if the image we are reading has 24 bits-per-pixel (3 bytes
	 * per pixel), we need to increment our matrix coordinates in order to move to
	 * the next pixel
	 */
	private void FSM_State3() {
		bitmapImage.getPixelAt(x, y).setRed(character);
		if (bitmapImage.getBytesPerPixel() == 3)
			increment();
	}

	/**
	 * The fourth state of the modelled FSM that handles parsing the Raw Pixel Data
	 * The FSM models a nested for loop, that executes one iteration per extraction
	 * from the BlockingQueue
	 * 
	 * The fourth state handles the fourth byte, which represents the intensity
	 * value for the alpha channel, so we set the pixel's alpha value to be equal to
	 * character
	 * 
	 * This state is only used in the case of a 32-bit BMP file If we reach this
	 * state, we need to increment our matrix coordinates to move to the next pixel,
	 * since state 3 did not pass the condition for incrementing
	 */
	private void FSM_State4() {
		bitmapImage.getPixelAt(x, y).setAlpha(character);
		increment();
	}

	/**
	 * Handles matrix coordinate incrementation, based on the modelled nested for
	 * loop: for (int x = 0; x < width -1; x++) { for (int y = height-1; y > -1;
	 * y--){ // read pixel data } } isDone keeps track wheter or not the loop has
	 * finished all its iterations
	 */
	private void increment() {
		if (x == bitmapImage.getImageWidth() - 1) {
			y--;
			x = 0;
		} else {
			x++;
		}
		if (y == -1) {
			isDone = true;
		}
	}

	/**
	 * Handles parsing the bits from the BMP header, based on their index See this
	 * medium.com article for more details on header format:
	 * https://medium.com/sysf/bits-to-bitmaps-a-simple-walkthrough-of-bmp-image-format-765dc6857393
	 */
	private void readBmpHeader() {
		switch (charCount) {
			case 1:
				temp = String.format("%c", character);
				break;
			case 2:
				temp += String.format("%c", character);
				bitmapImage.setFileType(temp);
				break;

			case 3:
				temp = String.format("%02X", character);
				break;
			case 4:
				temp = String.format("%02X", character) + temp;
				break;
			case 5:
				temp = String.format("%02X", character) + temp;
				break;
			case 6:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setFileSize(Integer.parseInt(temp, 16));
				break;

			case 7:
				temp = String.format("%c", character);
				break;
			case 8:
				temp += String.format("%c", character);
				bitmapImage.setReserved1(temp);
				break;
			case 9:
				temp = String.format("%c", character);
				break;
			case 10:
				temp += String.format("%c", character);
				bitmapImage.setReserved2(temp);
				break;

			case 11:
				temp = String.format("%02X", character);
				break;
			case 12:
				temp = String.format("%02X", character) + temp;
				break;
			case 13:
				temp = String.format("%02X", character) + temp;
				break;
			case 14:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setPixelDataOffset(Integer.parseInt(temp, 16));
				break;
		}
	}

	/**
	 * Handles parsing the bits from the DIB header, based on their index See this
	 * medium.com article for more details on header format:
	 * https://medium.com/sysf/bits-to-bitmaps-a-simple-walkthrough-of-bmp-image-format-765dc6857393
	 */
	private void readDibHeader() {
		switch (charCount) {
			case 15:
				temp = String.format("%02X", character);
				break;
			case 16:
				temp = String.format("%02X", character) + temp;
				break;
			case 17:
				temp = String.format("%02X", character) + temp;
				break;
			case 18:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setHeaderSize(Integer.parseInt(temp, 16));
				break;

			case 19:
				temp = String.format("%02X", character);
				break;
			case 20:
				temp = String.format("%02X", character) + temp;
				break;
			case 21:
				temp = String.format("%02X", character) + temp;
				break;
			case 22:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setImageWidth(Integer.parseInt(temp, 16));
				break;

			case 23:
				temp = String.format("%02X", character);
				break;
			case 24:
				temp = String.format("%02X", character) + temp;
				break;
			case 25:
				temp = String.format("%02X", character) + temp;
				break;
			case 26:
				temp = String.format("%02X", character) + temp;
				y = Integer.parseInt(temp, 16) - 1;
				bitmapImage.setImageHeight(y + 1);
				break;

			case 27:
				temp = String.format("%02X", character);
				break;
			case 28:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setPlanes(Integer.parseInt(temp, 16));
				break;

			case 29:
				temp = String.format("%02X", character);
				break;
			case 30:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setBitsPerPixel(Integer.parseInt(temp, 16));
				break;

			case 31:
				temp = String.format("%02X", character);
				break;
			case 32:
				temp = String.format("%02X", character) + temp;
				break;
			case 33:
				temp = String.format("%02X", character) + temp;
				break;
			case 34:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setCompression(Integer.parseInt(temp, 16));
				break;

			case 35:
				temp = String.format("%02X", character);
				break;
			case 36:
				temp = String.format("%02X", character) + temp;
				break;
			case 37:
				temp = String.format("%02X", character) + temp;
				break;
			case 38:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setImageSize(Integer.parseInt(temp, 16));
				break;

			case 39:
				temp = String.format("%02X", character);
				break;
			case 40:
				temp = String.format("%02X", character) + temp;
				break;
			case 41:
				temp = String.format("%02X", character) + temp;
				break;
			case 42:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setXpixelsPerMeter(Integer.parseInt(temp, 16));
				break;

			case 43:
				temp = String.format("%02X", character);
				break;
			case 44:
				temp = String.format("%02X", character) + temp;
				break;
			case 45:
				temp = String.format("%02X", character) + temp;
				break;
			case 46:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setYpixelsPerMeter(Integer.parseInt(temp, 16));
				break;

			case 47:
				temp = String.format("%02X", character);
				break;
			case 48:
				temp = String.format("%02X", character) + temp;
				break;
			case 49:
				temp = String.format("%02X", character) + temp;
				break;
			case 50:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setTotalColors(Integer.parseInt(temp, 16));
				break;

			case 51:
				temp = String.format("%02X", character);
				break;
			case 52:
				temp = String.format("%02X", character) + temp;
				break;
			case 53:
				temp = String.format("%02X", character) + temp;
				break;
			case 54:
				temp = String.format("%02X", character) + temp;
				bitmapImage.setImportantColors(Integer.parseInt(temp, 16));
				break;
		}
	}

	/**
	 * Handles parsing the bits from the RPD, based on their index See this
	 * medium.com article for more details on header format:
	 * https://medium.com/sysf/bits-to-bitmaps-a-simple-walkthrough-of-bmp-image-format-765dc6857393
	 */
	private void readRawPixelData() throws Exception {
		// init pixel matrix if needed
		if (bitmapImage.getPixels() == null)
			bitmapImage.initPixels();

		// position = byte index inside the raw pixel data
		final int position = charCount - bitmapImage.getPixelDataOffset();

		switch (bitmapImage.getBytesPerPixel()) {
			case 3: // 24 bit BMP
				switch (position % 3) {
					case 1:
						FSM_State1();
						break; // read blue
					case 2:
						FSM_State2();
						break; // read green
					case 0:
						FSM_State3();
						break; // read red & increment
				}
				if (isDone)
					return; // if we run out of pixels
				break;
			case 4:
				switch (position % 4) {
					case 1:
						FSM_State1();
						break; // read blue
					case 2:
						FSM_State2();
						break; // read green
					case 3:
						FSM_State3();
						break; // read red
					case 4:
						FSM_State4();
						break; // read alpha & increment
				}
				if (isDone)
					return; // if we run out of pixels
				break;
			default: // if the image is 16bpp or less
				throw new Exception("BmpIO only supports 24 and 32 bit BMP images");
		}
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				try {
					charCount++;
					character = bQueue.take();
					System.out.println("Char count: " + charCount);

					if (charCount > 0 && charCount < 15) {
						readBmpHeader();
					} else if (charCount < 55) {
						readDibHeader();
					} else {
						readRawPixelData();
					}

					if (isDone)
						return;

					notify();
				} catch (final InterruptedException e) {
					System.out.println("Such is life... All must die eventually...");
					e.printStackTrace();
				} catch (final Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
}
