package Events;

public class EventTask extends Thread {
	
	boolean isKilled;
	
	public EventTask() {
		this.isKilled = false;
	}
	
	void post(Runnable r) {
		if(!isKilled) {
			EventPump.getSelf().post(r);
		}
	}
	
	static EventTask task() {
		EventTask t = (EventTask) currentThread();
		return t;
	}
	
	void kill() {
		isKilled = true;
	}
	
	boolean killed() {
		return isKilled;
	}

}
