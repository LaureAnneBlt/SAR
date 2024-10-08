package Abstract;

import Events.Message;
import Implem.DisconnectedException;

public abstract class AbstractEventMessageQueue {
	
	public AbstractEventMessageQueue(String name) {
        
    }
    
    public interface Listener {
        void received(byte[] msg);
        void closed();
    }
    
    protected abstract void setListener(Listener l);
    
    public abstract boolean send(byte[] bytes) throws DisconnectedException;
    public abstract boolean send(Message message) throws DisconnectedException;
    
    public abstract void close();
    public abstract boolean closed();
}
