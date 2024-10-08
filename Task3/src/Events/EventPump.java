package Events;

import java.util.List;
import java.util.LinkedList;

public class EventPump extends Thread {

    List<Runnable> queue;
    Runnable currentRunnable;

    private static EventPump instance;

    static {
        try {
            instance = new EventPump();
        } catch (Exception e) {
            throw new RuntimeException("Échec de l'initialisation de EventPump.");
        }
    }

    public EventPump() {
        queue = new LinkedList<Runnable>();
    }

    public synchronized void run() {
        while (true) {
            while (queue.isEmpty()) {  // Check if the queue is empty
                try {
                    wait();  // Wait until notified
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); // Restore interrupt status
                    return; // Exit if interrupted
                }
            }
            currentRunnable = queue.remove(0);
            currentRunnable.run();
        }
    }

    public synchronized void post(Runnable r) {
        queue.add(r); // Add to the end of the queue
        notify(); // Notify the waiting thread
    }

    public static EventPump getSelf() {
        return instance;
    }

    public EventTask getCurrentRunnable() {
        return (EventTask) currentRunnable;
    }
}
