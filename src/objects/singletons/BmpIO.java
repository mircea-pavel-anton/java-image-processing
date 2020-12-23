package objects.singletons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import objects.image.BitmapImage;
import objects.image.Image;
import objects.runnables.Consumer;
import objects.runnables.Producer;

public class BmpIO {
	// Singleton Instance
	private static BmpIO instance = null;

	/**
	 * If no instance has been created yet, create one and return it, otherwise,
	 * return the already existing one
	 * 
	 * @return an instance of BmpIO
	 * @throws FileNotFoundException
	 */
	public static BmpIO getInstance() throws FileNotFoundException {
		if (instance == null) {
			instance = new BmpIO();
		}
		return instance;
	}

	/**
	 * Private constructor, to hide the default public one
	 * 
	 */
	private BmpIO() {
		this.path = ArgParser.getInstance().getInputFiles().getAbsolutePath();
		this.queue = new ArrayBlockingQueue<>(100);
	}

	private String path;
	private BlockingQueue<Integer> queue;


	public BitmapImage read() throws FileNotFoundException, InterruptedException {
		BitmapImage image = new BitmapImage();
		Producer producer = new Producer(path, queue);
		Consumer consumer = new Consumer(queue, image);

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);
		producerThread.start();
		consumerThread.start();

		producerThread.join();
		consumerThread.join();
		return image;
	}

	/** A wrapper for ImageIO.write()
	 * 
	 * The assignment only required us to read the raw binary data, not to write it too, so ohwell :D
	 * 
	 * @param image -> the image to be written on disk
	 * @param path -> the path to the file
	 * @throws IOException -> rethrows any ImageIO exceptions
	 */
	public void write(Image image, String path) throws IOException {
		ImageIO.write(image.toBufferedImage(), "bmp", new File(path));
	}
}
