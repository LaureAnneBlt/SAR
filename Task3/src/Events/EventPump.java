package Events;

import java.util.List;
import java.util.LinkedList;

abstract class EventPump extends Thread {
 
	List<Runnable> queue;
	
	private static EventPump instance;
	
	EventPump() {
		queue = new LinkedList<Runnable>();
	}
 
	public synchronized void run() {
		Runnable r;
		while(true) {
			r = queue.remove(0);
			while (r!=null) {
				r.run();
				r = queue.remove(0);
			}
			sleep();
		}
	}
	
	public synchronized void post(Runnable r) {
		queue.add(r); // at the end…
		notify();
	}
	
	private void sleep() {
		try {
			wait();
		} catch (InterruptedException ex){
			// nothing to do here.
		}
	}
	
	public static EventPump getSelf() {
		return instance;
	}
}