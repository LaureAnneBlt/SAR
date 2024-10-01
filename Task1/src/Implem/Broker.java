package Implem;

import java.util.Map;
import java.util.HashMap;

public class Broker extends AbstractBroker {
	
	BrokerManager bm = BrokerManager.getSelf();
	Map<Integer, RdV> accepts;
	
    public Broker(String name) {
        super(name);
        accepts = new HashMap<Integer, RdV>();
        this.bm.addBrokers(this);
    }

    @Override
    public synchronized AbstractChannel accept(int port) throws InterruptedException, IllegalStateException {
       RdV rdv = null;
       
       synchronized(accepts) {
    	   rdv = accepts.get(port);
    	   if(rdv != null) {
    		   throw new IllegalStateException("Another broker is already accepting on this port");
    	   }
    	   rdv = new RdV();
    	   accepts.put(port, rdv);
    	   accepts.notifyAll();
       }
       
       return rdv.accept(this, port);
    }
    
    @Override
    public synchronized AbstractChannel connect(String name, int port) throws InterruptedException {
    	Broker broker = (Broker)bm.getBroker(name);
    	
    	if(broker == null) {
    		return null;
    	}
    	
    	return broker._connect(this, port);
    }

    public synchronized AbstractChannel _connect(Broker b, int port) throws InterruptedException {
    	RdV rdv = null;
        
        synchronized(accepts) {
     	   rdv = accepts.get(port);
     	   while(rdv == null) {
     		   try {
     			   accepts.wait();
     		   } catch(InterruptedException e){
     			   
     		   }
     		   accepts.remove(port);
     	   }
        }
        return rdv.connect(b, port);
    }
    
    public String getName() {
    	return this.name;
    }

}