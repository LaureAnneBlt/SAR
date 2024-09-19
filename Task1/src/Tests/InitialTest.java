package Tests;

import Implem.AbstractBroker;
import Implem.Broker;

public class InitialTest {
	
	public void test() {
		AbstractBroker server = new Broker("server");
		AbstractBroker client = new Broker("client");
	}

}
