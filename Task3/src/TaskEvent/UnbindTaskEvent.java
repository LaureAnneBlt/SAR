package TaskEvent;

import Events.EventQueueBroker;
import Events.EventTask;

public class UnbindTaskEvent extends EventTask {
	
	EventQueueBroker queueBroker;
	int port;
	
	public UnbindTaskEvent(EventQueueBroker queueBroker, int port) {
		this.queueBroker = queueBroker;
		this.port = port;
	}

	@Override
	public void run() {
		if(queueBroker.unbind(port)) {
			this.kill();
		}
	}

}
