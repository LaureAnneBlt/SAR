package Implem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrokerManager {
	
	ArrayList<Broker> allBrokers;
	Map<Integer, RdV> rdvs;
	
	
	public BrokerManager() {	
		allBrokers = new ArrayList<Broker>();
		rdvs = new HashMap<>();
	}
	
	public synchronized void addBrokers(Broker b) {
		allBrokers.add(b);
	}
	
	public synchronized  void removeBroker(Broker b) {
		allBrokers.remove(b);
	}
	
	public Broker getBroker(String name) {
		for (Broker broker : allBrokers) {
			if(broker.getName().equals(name)) {
				return broker;
			}
		}
		return null;
	}
	
	 public synchronized RdV findOrCreateRdV(int port) {
		 
		 RdV rdv = rdvs.get(port);
		 
		 if (rdv == null) {
			 rdv = new RdV(port); 
			 rdvs.put(port, rdv);
		 }
		 
		 return rdv;
    }

}
