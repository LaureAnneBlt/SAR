package Tests;

import Abstract.AbstractEventQueueBroker;
import Events.EventMessageQueue;
import Events.EventQueueBroker;
import Implem.DisconnectedException;

public class Client {

    private EventQueueBroker queueBroker;
    private boolean messageSent; // Drapeau pour vérifier si le message a été envoyé

    public Client(String name, EventQueueBroker broker) {
        this.queueBroker = broker;
        this.messageSent = false;
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

                if (!messageSent) {
                    String message = "Test client N°" + Thread.currentThread().getId();
                    byte[] msg = message.getBytes();
                    try {
                        queue.send(msg);
                        messageSent = true;
                        System.out.println("Client sent message: " + message);
                    } catch (DisconnectedException e) {
                        System.err.println("Failed to send message: " + e.getMessage());
                    }
                }
            }

            @Override
            public void refused() {
                System.out.println("Connection refused by the server.");
            }
        });
    }
}
