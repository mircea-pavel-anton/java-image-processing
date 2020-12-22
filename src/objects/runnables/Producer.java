package objects.runnables;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private String path;
    private BlockingQueue<String> bQueue;
    private BufferedReader reader;

    public Producer(String path, BlockingQueue<String> queue) throws FileNotFoundException {
        this.path = path;
        this.bQueue = queue;
        this.reader = new BufferedReader( new FileReader(path) );
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }
}
