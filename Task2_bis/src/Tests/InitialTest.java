package Tests;

import Implem.Broker;
import Implem.DisconnectedException;
import Implem.MessageQueue;
import Implem.QueueBroker;
import Implem.Server;

public class InitialTest {

    public void testOneClient() throws InterruptedException, DisconnectedException {

    	Broker serverBroker = new Broker("Server");
        QueueBroker serverQueueBroker = new QueueBroker(serverBroker);
        Server server = new Server(serverQueueBroker);
        server.startServer(8080);

        QueueBroker clientQueueBroker = new QueueBroker(new Broker("Client"));

        MessageQueue clientQueue = clientQueueBroker.connect("Server", 8080);

        byte[] msg = "Test".getBytes();
        clientQueue.send(msg, 0, msg.length);

        byte[] resp = clientQueue.receive();

        String msgReceived = new String(resp);

        if (!"Test".equals(msgReceived)) {
            throw new RuntimeException("Test échoué : attendu 'Test', mais reçu '" + msgReceived + "'");
        }

        System.out.println("Test réussi : Message reçu = " + msgReceived);

        clientQueue.close();
    }
}
