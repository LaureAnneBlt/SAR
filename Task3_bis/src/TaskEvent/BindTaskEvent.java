package TaskEvent;

import Abstract.AbstractEventQueueBroker.AcceptListener;
import Events.EventQueueBroker;
import Events.EventTask;

public class BindTaskEvent extends EventTask {
	
	EventQueueBroker queueBroker;
	int port;
	AcceptListener listener;
	
	public BindTaskEvent(EventQueueBroker queueBroker, int port, AcceptListener listener) {
		this.queueBroker = queueBroker;
		this.port = port;
		this.listener = listener;
	}

	@Override
	public void run() {
		queueBroker._bind(port, listener);
		this.kill();
	}
}
