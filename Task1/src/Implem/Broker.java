package Implem;

import java.util.ArrayList;

public class Broker extends AbstractBroker {
	
	BrokerManager bm;
	ArrayList<RdV> rdvs;
	
    public Broker(String name, BrokerManager bm) {
        super(name, bm);
        
        this.bm.addBrokers(this);
        this.rdvs = new ArrayList<RdV>();
    }

    @Override
    public synchronized AbstractChannel accept(int port) throws InterruptedException, IllegalStateException {
       RdV rdv = findOrCreateRdV(port);
       
       if(rdv.acceptBroker != null) {
    	   throw new IllegalStateException("Another broker is already accepting on this port");
       }
       
       return rdv.accept(this);
    }

    @Override
    public synchronized AbstractChannel connect(String name, int port) throws InterruptedException {
    	RdV rdv = findOrCreateRdV(port);
    	
    	return rdv.connect(this);
    }
    
    private synchronized RdV findOrCreateRdV(int port) {
    	for (RdV rdv : rdvs) {
    		if (rdv.port == port) {
    			return rdv;
    		}
    	}
    	
    	RdV newRdv = new RdV(port);
    	rdvs.add(newRdv);
    	return newRdv;
    }
    
    public String getName() {
    	return this.name;
    }

}