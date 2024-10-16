package Events;

public abstract class EventTask implements Runnable {
	
	boolean isKilled;
	
	public EventTask() {
		this.isKilled = false;
	}
	
	public void react() {
		this.run();
	}
	
	public void kill() {
		EventPump.getSelf().queue.remove(this);
		isKilled = true;
	}
	
	boolean killed() {
		return isKilled;
	}
}
