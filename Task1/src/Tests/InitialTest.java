package Tests;

import Implem.Broker;
import Implem.SimpleBroker;

public class InitialTest {
	
	public void test() {
		Broker server = new SimpleBroker("server");
		Broker client = new SimpleBroker("client");
	}

}
