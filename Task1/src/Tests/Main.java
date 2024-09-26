package Tests;

import Implem.Broker;
import Implem.BrokerManager;
import Implem.Server;
import Implem.Client;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		BrokerManager brokerManager = new BrokerManager();
		Broker serverBroker = new Broker("Server", brokerManager);
		
		Server server = new Server(serverBroker);
		server.startServer(8080);
		
		Client client1 = new Client("Client1", brokerManager);
		client1.connectClient("Server", 8080);
		
		InitialTest test = new InitialTest();
		
		try {
			test.testOneClient();
			test.testMultipleClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
