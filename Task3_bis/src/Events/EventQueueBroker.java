package Events;

import java.util.HashMap;
import java.util.Map;

import Abstract.AbstractEventQueueBroker;
import Implem.Broker;
import Implem.Channel;
import Implem.MessageQueue;
import TaskEvent.BindTaskEvent;
import TaskEvent.UnbindTaskEvent;
import TaskEvent.ConnectTaskEvent;

public class EventQueueBroker extends AbstractEventQueueBroker {

    private static Map<Integer, AcceptListener> listeners = new HashMap<>();
    private Broker broker;

    public EventQueueBroker(String name) {
        super(name);
    }
    
    public void setBroker(Broker broker) {
    	this.broker = broker;
    }

    public Broker getBroker() {
        return broker;
    }
    
    public String getName() {
    	return broker.getName();
    }
    
    @Override
    public boolean bind(int port, AcceptListener listener) {
    	BindTaskEvent bindTask = new BindTaskEvent(this, port, listener);
    	EventPump.getSelf().post(bindTask);
    	return true;
    }

    public void _bind(int port, AcceptListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				do {
					
					listeners.put(port, listener);
					Channel acceptChannel;
					try {
						acceptChannel = (Channel) broker.accept(port);
						MessageQueue mq = new MessageQueue(acceptChannel);
						EventMessageQueue emq = new EventMessageQueue(mq);
						listener.accepted(emq);
					} catch (IllegalStateException | InterruptedException e) {
						e.printStackTrace();
					}
					

					
				} while(listeners.get(port) != null);
				
			}
		}).start();
	}

    @Override
    public boolean unbind(int port) {
    	UnbindTaskEvent unbindTask = new UnbindTaskEvent(this, port);
    	EventPump.getSelf().post(unbindTask);
    	return true;
    }
    
    public void _unbind(int port) {
    	listeners.remove(port);
    }

    @Override
    public boolean connect(String name, int port, ConnectListener listener) {
    	ConnectTaskEvent connectTask = new ConnectTaskEvent(this, name, port, listener);
    	EventPump.getSelf().post(connectTask);
    	return true;
    }
    
    public void _connect(String name, int port, ConnectListener listener) throws InterruptedException {
    	Channel connectChannel = (Channel) broker.connect(name, port);
		
		if (connectChannel == null) {
			listener.refused();
		}
		else {
			MessageQueue mq = new MessageQueue(connectChannel);
			EventMessageQueue emq = new EventMessageQueue(mq);
			listener.connected(emq);
    }
		
		
    }
}
