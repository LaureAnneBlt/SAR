package TaskEvent;

import Abstract.AbstractEventQueueBroker.ConnectListener;
import Events.EventQueueBroker;
import Events.EventTask;

public class ConnectTaskEvent extends EventTask {
	
	EventQueueBroker queueBroker;
	String name;
	int port;
	ConnectListener listener;
	
	public ConnectTaskEvent(EventQueueBroker queueBroker, String name, int port, ConnectListener listener) {
		this.queueBroker = queueBroker;
		this.name = name;
		this.port = port;
		this.listener = listener;
	}

	@Override
	public void run() {
		if(queueBroker.connect(name, port, listener)) {
			this.kill();
		}
		
	}

}
