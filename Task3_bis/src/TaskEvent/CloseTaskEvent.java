package TaskEvent;

import Abstract.AbstractEventMessageQueue.Listener;
import Events.EventMessageQueue;
import Events.EventTask;

public class CloseTaskEvent extends EventTask {
	
	EventMessageQueue mq;
	Listener listener;
	
	public CloseTaskEvent(EventMessageQueue mq, Listener listener) {
		this.mq = mq;
		this.listener = listener;
	}
	
	@Override
	public void run() {
		mq.channel.disconnect();
		listener.closed();
		this.kill();
	}
}
