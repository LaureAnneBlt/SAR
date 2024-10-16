package Events;

import Abstract.AbstractEventMessageQueue;
import Implem.Channel;
import Implem.DisconnectedException;
import Implem.MessageQueue;
import TaskEvent.CloseTaskEvent;
import TaskEvent.SendTaskEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventMessageQueue extends AbstractEventMessageQueue{
	
	public Channel channel;
	public MessageQueue msgQueue;
	
	private final Thread reader;
	private final Thread writer;
	
	
	private Listener listener;
	public boolean isClosed = false;
	
	
	public BlockingQueue<Message> pendingMessages = new LinkedBlockingQueue<>();

    public EventMessageQueue(MessageQueue msgQueue) {
    	this.msgQueue = msgQueue;
		this.channel = this.msgQueue.channel;
		this.writer = new Thread(() -> sender());
		this.reader = new Thread(() -> receiver());
		this.writer.start();
	}

    public boolean send(byte[] bytes) throws DisconnectedException {
    	Message message = new Message(bytes, 0, bytes.length);
        return send(message);
    }
    
    public boolean send(Message message) {
    	SendTaskEvent sendMsg = new SendTaskEvent(this, message, listener);
    	EventPump.getSelf().post(sendMsg);
    	return true;
    }

    public boolean _send(Message message, Listener listener) throws DisconnectedException {
        if (isClosed) {
            System.out.println("MessageQueue is closed. Cannot send message.");
            return false;
        }

        synchronized (pendingMessages) {
        	pendingMessages.add(message);
            pendingMessages.notify();
            listener.sent(message);
        }
        return true;
    }
    
    private void receiver() {
        while (true) {
            try {
                byte[] msg = msgQueue.receive();
                Message message = new Message(msg, 0, msg.length);
                if (listener != null) {
                	listener.received(message);
                }
            } catch (Exception e) {
                break;
            }
        }
    }
    
    private void sender() {
        while (true) {
            try {
                Message msg;
                synchronized (pendingMessages) {
                    if (!pendingMessages.isEmpty()) {
                        msg = pendingMessages.poll();
                    } else {
                    	pendingMessages.wait();
                        continue;
                    }
                }
                msgQueue.send(msg.bytes, 0, msg.length);
                listener.sent(msg);
            } catch (Exception e) {
                break;
            }
        }

    }


    public void close() {
    	CloseTaskEvent closeTask = new CloseTaskEvent(this, listener);
        EventPump.getSelf().post(closeTask);
        System.out.println("MessageQueue closed.");
    }

    public boolean closed() {
        return isClosed;
    }

	@Override
	public void setListener(Abstract.AbstractEventMessageQueue.Listener l) {
		this.listener = (Listener) l;
		this.reader.start();
	}
}
