package objects.runnables;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
	private final BlockingQueue<Integer> bQueue;
	private final BufferedInputStream inputStream;

	public Producer(final String path, final BlockingQueue<Integer> queue) throws FileNotFoundException {
		this.bQueue = queue;
		this.inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
	}

	@Override
	public void run() {
		int character = -2;

		while (true) {
			synchronized (this) {
				try {
					// if the queue is full, wait for the consumer to read from it
					// some more
					while (bQueue.size() == 100)
						wait();

					// Keep reading from th file, until either the queue is full, or we reached EOF
					while ((character = inputStream.read()) != -1 && bQueue.size() <= 100) {
						bQueue.put(character);
						notify();
					}

					// If we reached EOF
					if (character == -1) { return; }
				} catch (final IOException e) {
					System.out.println("Encountered some error reading the file!");
					e.printStackTrace();
				} catch (final InterruptedException e) {
					System.out.println("Such is life... All must die eventually...");
					e.printStackTrace();
				}
			}
		}
	}
}
