package Tests;

import Abstract.AbstractEventQueueBroker;
import Events.EventMessageQueue;
import Events.EventPump;
import Events.EventQueueBroker;
import Implem.DisconnectedException;

public class Client {

    private EventQueueBroker queueBroker;

    public Client(String name, EventQueueBroker broker) {
        this.queueBroker = broker;
    }

    public void connectClient(int port) {
        queueBroker.connect("Server", port, new AbstractEventQueueBroker.ConnectListener() {
            @Override
            public void connected(EventMessageQueue queue) {
                queue.setListener(new EventMessageQueue.Listener() {
                    @Override
                    public void received(byte[] msg) {
                        String responseMessage = new String(msg);
                        System.out.println("Client received: " + responseMessage);
                    }

                    @Override
                    public void closed() {
                        System.out.println("Connection closed by the server.");
                    }
                });

                EventPump.getSelf().post(() -> {
                    String message = "Test client " + Thread.currentThread().getId();
                    byte[] msg = message.getBytes();
                    try {
                        queue.send(msg);
                        System.out.println("Client sent message: " + message);
                    } catch (DisconnectedException e) {
                        System.err.println("Failed to send message: " + e.getMessage());
                    }
                });
            }

            @Override
            public void refused() {
                System.out.println("Connection refused by the server.");
            }
        });
    }
}
