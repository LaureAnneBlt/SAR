package Events;

import java.util.HashMap;
import java.util.Map;

import Abstract.AbstractEventQueueBroker;
import Implem.Broker;

public class EventQueueBroker extends AbstractEventQueueBroker {

    private static Map<Integer, AcceptListener> listeners = new HashMap<>();
    private Broker broker;

    public EventQueueBroker(String name) {
        super(name);
        broker = new Broker(name);
    }

    public Broker getBroker() {
        return broker;
    }

    @Override
    public boolean bind(int port, AcceptListener listener) {
        if (listeners.containsKey(port)) {
            System.out.println("Port " + port + " is already bound.");
            return false;
        }

        listeners.put(port, listener);
        System.out.println("Port " + port + " bound successfully.");

        broker = new Broker("ServerBroker");

        new Thread(() -> {
            while (true) {
                try {
                    broker.accept(port);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }).start();

        return true;
    }

    @Override
    public boolean unbind(int port) {
        if (listeners.remove(port) != null) {
            System.out.println("Port " + port + " unbound successfully.");
            return true;
        }
        System.out.println("Port " + port + " is not bound.");
        return false;
    }

    public boolean connect(String name, int port, ConnectListener listener) {
        AcceptListener acceptListener = listeners.get(port);
        if (acceptListener != null) {
            EventMessageQueue messageQueue = new EventMessageQueue(name);
            acceptListener.accepted(messageQueue);
            listener.connected(messageQueue);
            return true;
        } else {
            EventPump.getSelf().post(() -> {
                listener.refused();
                System.out.println("Connection to " + name + " on port " + port + " was refused.");
            });
            return false;
        }
    }
}
