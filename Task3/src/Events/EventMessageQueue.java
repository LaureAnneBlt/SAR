package Events;

import Abstract.AbstractEventMessageQueue;
import Implem.Channel;
import Implem.DisconnectedException;

public class EventMessageQueue extends AbstractEventMessageQueue{
	
	Channel channel;

    public EventMessageQueue(String name) {
		super(name);
	}

	private Listener listener;
    private boolean isClosed = false;

    public interface Listener {
        void received(byte[] msg);
        void closed();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public boolean send(byte[] bytes) throws DisconnectedException {
    	Message message = new Message(bytes, 0, bytes.length);
        return send(message);
    }

    public boolean send(Message message) throws DisconnectedException {
        if (isClosed) {
            System.out.println("MessageQueue is closed. Cannot send message.");
            return false;
        }
        
        channel.write(message.bytes, message.offset, message.length);
        
        byte[] msg = new byte[message.length];
        System.arraycopy(message.bytes, message.offset, msg, 0, message.length);
        
        if (listener != null) {
            listener.received(msg);
        }
        System.out.println("Message sent successfully.");
        return true;
    }

    public void close() {
        isClosed = true;
        if (listener != null) {
            listener.closed();
        }
        System.out.println("MessageQueue closed.");
    }

    public boolean closed() {
        return isClosed;
    }

	@Override
	protected void setListener(Abstract.AbstractEventMessageQueue.Listener l) {
		this.listener = (Listener) l;
		
	}
}
