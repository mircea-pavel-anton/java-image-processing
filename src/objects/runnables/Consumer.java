package objects.runnables;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private String path;
    private BlockingQueue<String> bQueue;

    public Consumer(String path, BlockingQueue<String> queue) {
        this.path = path;
        this.bQueue = queue;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }
    
}
