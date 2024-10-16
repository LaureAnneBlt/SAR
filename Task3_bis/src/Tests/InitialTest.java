package Tests;

import Events.EventQueueBroker;
import Events.EventPump;

public class InitialTest {

    public static void main(String[] args) throws InterruptedException {
        InitialTest test = new InitialTest();
        test.testOneClient();
        test.testMultipleClients();
    }

    public void testOneClient() throws InterruptedException {
        System.out.println("\nTest One Client\n");

        EventPump eventPump = EventPump.getSelf();
        eventPump.start();

        EventQueueBroker serverQueueBroker = new EventQueueBroker("Server");
        Server server = new Server(serverQueueBroker);
        server.startServer(8080);

        EventQueueBroker clientQueueBroker = new EventQueueBroker("Client");
        Client client = new Client("Client", clientQueueBroker);
        client.connectClient(8080);
        
        eventPump.interrupt();
    }

    public void testMultipleClients() throws InterruptedException {
        System.out.println("\nTest Multiple Clients\n");

        EventPump eventPump = EventPump.getSelf();
        eventPump.start(); 

        EventQueueBroker serverQueueBroker = new EventQueueBroker("Server");
        Server server = new Server(serverQueueBroker);
        server.startServer(8080);

        EventQueueBroker client1QueueBroker = new EventQueueBroker("Client1");
        Client client1 = new Client("Client1", client1QueueBroker);
        client1.connectClient(8080);

        EventQueueBroker client2QueueBroker = new EventQueueBroker("Client2");
        Client client2 = new Client("Client2", client2QueueBroker);
        client2.connectClient(8080);

        eventPump.interrupt();
    }
}