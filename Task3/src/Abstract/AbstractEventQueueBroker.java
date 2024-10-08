package Abstract;

import Events.EventMessageQueue;

public abstract class AbstractEventQueueBroker {
	
	String name;

    public AbstractEventQueueBroker(String name) {
        this.name = name;
    }
    
    public interface AcceptListener {
        void accepted(EventMessageQueue queue);
    }
    
    public abstract boolean bind(int port, AbstractEventQueueBroker.AcceptListener listener);
    public abstract boolean unbind(int port);
    
    public interface ConnectListener{
        void connected(EventMessageQueue queue);
        void refused();
    }
    
    protected abstract boolean connect(String name, int port, AbstractEventQueueBroker.ConnectListener listener);
}
