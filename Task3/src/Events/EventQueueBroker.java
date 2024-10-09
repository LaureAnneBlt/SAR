package Events;

import java.util.HashMap;
import java.util.Map;

import Implem.CircularBuffer;
import Implem.Channel;

import Abstract.AbstractEventQueueBroker;

public class EventQueueBroker extends AbstractEventQueueBroker {

    private static Map<Integer, AcceptListener> listeners = new HashMap<>();

    public EventQueueBroker(String name) {
        super(name);
    }

    public boolean unbind(int port) {
        if (listeners.remove(port) != null) {
            System.out.println("Port " + port + " unbound successfully.");
            return true;
        }
        System.out.println("Port " + port + " is not bound.");
        return false;
    }

    public interface ConnectListener {
        void connected(EventMessageQueue queue);
        void refused();
    }

    @Override
    public boolean bind(int port, AcceptListener listener) {
        if (listeners.containsKey(port)) {
            System.out.println("Port " + port + " is already bound.");
            return false;
        }

        listeners.put(port, listener);
        System.out.println("Port " + port + " bound successfully.");

        new Thread(() -> {
            while (true) {
            }
        }).start();

        return true;
    }

    public boolean connect(String name, int port, AbstractEventQueueBroker.ConnectListener listener) {
        AcceptListener acceptListener = listeners.get(port);
        if (acceptListener != null) {
        	
        	CircularBuffer buffer1 = new CircularBuffer(512);
            CircularBuffer buffer2 = new CircularBuffer(512);
            Channel clientChannel = new Channel(null, port, buffer1, buffer2);
            Channel serverChannel = new Channel(null, port, buffer2, buffer1);

            clientChannel.connect(serverChannel, name);
        	
            EventMessageQueue messageQueue = new EventMessageQueue(name);
            messageQueue.channel = clientChannel;
            
            EventPump.getSelf().post(() -> {
                acceptListener.accepted(messageQueue);
                listener.connected(messageQueue);
                System.out.println("Connection to " + name + " on port " + port + " was successful. \n");
            });
            return true;
        } else {
            EventPump.getSelf().post(() -> {
                listener.refused();
                System.out.println("Connection to " + name + " on port " + port + " was refused. \n");
            });
            return false;
        }
    }
}
