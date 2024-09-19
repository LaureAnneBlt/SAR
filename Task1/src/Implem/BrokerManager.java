package Implem;

import java.util.ArrayList;

public class BrokerManager {
	
	ArrayList<Broker> allBrokers;
	
	
	public BrokerManager() {	
		allBrokers = new ArrayList<Broker>();
	};
	
	public void addBrokers(Broker b) {
		allBrokers.add(b);
	};
	
	public void removeBroker(Broker b) {
		allBrokers.remove(b);
	};
	
	public Broker getBroker(String name) {
		for (Broker broker : allBrokers) {
			if(broker.getName().equals(name)) {
				return broker;
			}
		}
		return null;
	};

}
