package Events;

import Abstract.AbstractEventMessageQueue;
import Implem.Channel;
import Implem.DisconnectedException;

import java.util.List;
import java.util.LinkedList;

public class EventMessageQueue extends AbstractEventMessageQueue{
	
	Channel channel;
	private List<Message> pendingMessages = new LinkedList<>();

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

    public synchronized boolean send(Message message) throws DisconnectedException {
        if (isClosed) {
            System.out.println("MessageQueue is closed. Cannot send message.");
            return false;
        }
        
//        channel.write(message.bytes, message.offset, message.length);
        pendingMessages.add(message);
        notify();
       
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
