package Events;

public abstract class EventTask implements Runnable {
	
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
		return EventPump.getSelf().getCurrentRunnable();
	}
	
	public void kill() {
		isKilled = true;
	}
	
	boolean killed() {
		return isKilled;
	}
}
