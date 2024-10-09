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
            while (queue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            currentRunnable = queue.remove(0);
            currentRunnable.run();
        }
    }

    public synchronized void post(Runnable r) {
        System.out.println("Posting new task to EventPump: " + r);
        queue.add(r);
        notify();
    }

    public static EventPump getSelf() {
        return instance;
    }

    public EventTask getCurrentRunnable() {
        return (EventTask) currentRunnable;
    }
}
