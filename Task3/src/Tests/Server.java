package Tests;

import Events.EventMessageQueue;
import Events.EventPump;
import Events.EventQueueBroker;
import Events.EventTask;
import Implem.DisconnectedException;

public class Server {

    private EventQueueBroker queueBroker;

    public Server(EventQueueBroker qb) {
        this.queueBroker = qb;
    }

    public void startServer(int port) {
        queueBroker.bind(port, queue -> {
            System.out.println("New client connected on port: " + port);

            EventTask clientTask = new EventTask() {
                @Override
                public void run() {
                    handleClient(queue);
                }
            };
            EventPump.getSelf().post(clientTask);
        });
    }

    private void handleClient(EventMessageQueue mq) {
        mq.setListener(new EventMessageQueue.Listener() {
            @Override
            public void received(byte[] msg) {
                String receivedMessage = new String(msg);
                System.out.println("Server received message: " + receivedMessage);

                EventPump.getSelf().post(() -> {
                    try {
                        mq.send(msg);
                    } catch (DisconnectedException e) {
                        System.err.println("Failed to send message: " + e.getMessage());
                    }
                });
            }

            @Override
            public void closed() {
                System.out.println("Client disconnected");
            }
        });
    }
}