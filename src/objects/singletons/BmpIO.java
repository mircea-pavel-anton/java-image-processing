package objects.singletons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import objects.GenericJob;
import objects.image.BitmapImage;
import objects.image.Image;
import objects.runnables.Consumer;
import objects.runnables.Producer;

public class BmpIO extends GenericJob {
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

	private final String path;
	private final BlockingQueue<Integer> queue;

	public BitmapImage read() throws Exception {
		final BitmapImage image = new BitmapImage();
		final Producer producer = new Producer(path, queue);
		final Consumer consumer = new Consumer(queue, image);

		final Thread producerThread = new Thread(producer);
		final Thread consumerThread = new Thread(consumer);
		Timer.getInstance().startJob("BmpIO" + getUID());
		producerThread.start();
		consumerThread.start();

		producerThread.join();
		consumerThread.join();
		duration = Timer.getInstance().stopJob("BmpIO" + getUID());
		return image;
	}

	/**
	 * A wrapper for ImageIO.write()
	 * 
	 * The assignment only required us to read the raw binary data, not to write it
	 * too, so ohwell :D
	 * 
	 * @param image -> the image to be written on disk
	 * @param path  -> the path to the file
	 * @throws IOException -> rethrows any ImageIO exceptions
	 */
	public void write(final Image image, final String path) throws IOException {
		ImageIO.write(image.toBufferedImage(), "bmp", new File(path));
	}

	@Override
	public String toString() { return "Bitmap image parser"; }
}
