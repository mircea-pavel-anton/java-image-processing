package objects.runnables;

import java.util.concurrent.BlockingQueue;

import objects.image.BitmapImage;
import objects.image.Pixel;

public class Consumer implements Runnable {
	private BlockingQueue<Integer> bQueue;
	private BitmapImage bitmapImage;
	private int charCount;
	private String temp;
	private int x;
	private int y;
	private int character;

	public Consumer(BlockingQueue<Integer> queue, BitmapImage image) {
		this.bQueue = queue;
		this.bitmapImage = image;
		charCount = 0;
		temp = "";
	}

	@Override
	public void run() {
		while (true) {
			synchronized(this) {
				try {
					// while (bQueue.size() == 0) wait();

					charCount++;
					System.out.println("Char count: " + charCount);
					character = bQueue.take();
	
					switch (charCount) {
						case 1: temp = String.format("%c", character); break;
						case 2: 
							temp += String.format("%c", character);
							bitmapImage.setFileType(temp);
							break;
	
						case 3: temp = String.format("%02X", character); break;
						case 4: temp = String.format("%02X", character) + temp; break;
						case 5: temp = String.format("%02X", character) + temp; break;
						case 6:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setFileSize( Integer.parseInt(temp, 16) );
							break;
	
						case 7: temp = String.format("%c", character); break;
						case 8: 
							temp += String.format("%c", character);
							bitmapImage.setReserved1(temp);
							break;
						case 9: temp = String.format("%c", character); break;
						case 10: 
							temp += String.format("%c", character);
							bitmapImage.setReserved2(temp);
							break;
	
						case 11: temp = String.format("%02X", character); break;
						case 12: temp = String.format("%02X", character) + temp; break;
						case 13: temp = String.format("%02X", character) + temp; break;
						case 14:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setPixelDataOffset( Integer.parseInt(temp, 16) );
							break;
	
						case 15: temp = String.format("%02X", character); break;
						case 16: temp = String.format("%02X", character) + temp; break;
						case 17: temp = String.format("%02X", character) + temp; break;
						case 18:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setHeaderSize( Integer.parseInt(temp, 16) );
							break;
	
						case 19: temp = String.format("%02X", character); break;
						case 20: temp = String.format("%02X", character) + temp; break;
						case 21: temp = String.format("%02X", character) + temp; break;
						case 22:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setImageWidth( Integer.parseInt(temp, 16) );
							break;
	
						case 23: temp = String.format("%02X", character); break;
						case 24: temp = String.format("%02X", character) + temp; break;
						case 25: temp = String.format("%02X", character) + temp; break;
						case 26:
							temp = String.format("%02X", character) + temp;
							y = Integer.parseInt(temp, 16) - 1;
							bitmapImage.setImageHeight( y+1 );
							break;
	
						case 27: temp = String.format("%02X", character); break;
						case 28:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setPlanes( Integer.parseInt(temp, 16) );
							break;
	
						case 29: temp = String.format("%02X", character); break;
						case 30:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setBitsPerPixel( Integer.parseInt(temp, 16) );
							break;
	
						case 31: temp = String.format("%02X", character); break;
						case 32: temp = String.format("%02X", character) + temp; break;
						case 33: temp = String.format("%02X", character) + temp; break;
						case 34:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setCompression( Integer.parseInt(temp, 16) );
							break;
	
						case 35: temp = String.format("%02X", character); break;
						case 36: temp = String.format("%02X", character) + temp; break;
						case 37: temp = String.format("%02X", character) + temp; break;
						case 38:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setImageSize( Integer.parseInt(temp, 16) );
							break;
	
						case 39: temp = String.format("%02X", character); break;
						case 40: temp = String.format("%02X", character) + temp; break;
						case 41: temp = String.format("%02X", character) + temp; break;
						case 42:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setXpixelsPerMeter( Integer.parseInt(temp, 16) );
							break;
	
						case 43: temp = String.format("%02X", character); break;
						case 44: temp = String.format("%02X", character) + temp; break;
						case 45: temp = String.format("%02X", character) + temp; break;
						case 46:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setYpixelsPerMeter( Integer.parseInt(temp, 16) );
							break;
	
						case 47: temp = String.format("%02X", character); break;
						case 48: temp = String.format("%02X", character) + temp; break;
						case 49: temp = String.format("%02X", character) + temp; break;
						case 50:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setTotalColors( Integer.parseInt(temp, 16) );
							break;
	
						case 51: temp = String.format("%02X", character); break;
						case 52: temp = String.format("%02X", character) + temp; break;
						case 53: temp = String.format("%02X", character) + temp; break;
						case 54:
							temp = String.format("%02X", character) + temp;
							bitmapImage.setImportantColors( Integer.parseInt(temp, 16) );
							break;
	
						default: // for any charCount over 54, we are in the Raw Pixel Data
							// if we just entered the raw pixel data, then we surely have
							// read the width and height of our image, so we can init the
							// pixel matrix
							if (charCount == bitmapImage.getPixelDataOffset() + 1) {
								bitmapImage.initPixels();
							}

							// This next bit of code attempts to simulate a finite state machine
							// The 3 available states are:
							//   - state 1: (we're doing a %3, so it's represented by case 1)
							//      - init the pixel at x,y
							//      - put the value from the producer into the blue channel
							//   - state 2: (we're doing a %3, so it's represented by case 1)
							//      - put the value from the producer into the green channel
							//   - state 3:
							if (charCount >= bitmapImage.getPixelDataOffset()) {
								if (bitmapImage.getBitsPerPixel() == 24) {
									int position = charCount - bitmapImage.getPixelDataOffset();
									switch ( position % bitmapImage.getBytesPerPixel() ) {
										case 1: // the first byte represents the blue color
											// since it's the first time we read, we need
											// to create the pixel as well we can initialize
											// the pixel to have this value on all channels,
											// and go from there, by replacing the green and
											//red channels in the next 2 iterations
											bitmapImage.setPixelAt(x, y, new Pixel(character));
											break;

										case 2:  // the second byte represents the green color
											bitmapImage.getPixelAt(x, y).setGreen(character);
											break;

										case 0: // the third and last byte represents the red color
											bitmapImage.getPixelAt(x, y).setRed(character);

											// Since we finished reading a pixel, we have to move to the next
											// one in our matrix.
											// We are simulating a nested for loop, with:
											// x from 0 to width -1
											// y from height -1 to 0
											if ( x == bitmapImage.getImageWidth() - 1 ) {
												y--;
												x = 0;
											} else {
												x++;
											}

											// If we passed the y = 0 mark, then we're
											// basically out of the nested loop
											// and we can exit this thread
											if ( y == -1 ) { return; }
											break;
									}
								}
							}
							break;
					}

					notify();
				} catch (InterruptedException e) {
					System.out.println("Such is life... All must die eventually...");
					// e.printStackTrace();
				}
			}
		}
	}
	
}
