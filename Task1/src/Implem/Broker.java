package Implem;

public class Broker extends AbstractBroker {
	
	BrokerManager bm;
	
    public Broker(String name, BrokerManager bm) {
        super(name, bm);
        
        this.bm = bm;
        
        this.bm.addBrokers(this);
    }

    @Override
    public synchronized AbstractChannel accept(int port) throws InterruptedException, IllegalStateException {
       RdV rdv = bm.findOrCreateRdV(port);
       
       if(rdv.acceptBroker != null) {

    	   throw new IllegalStateException("Another broker is already accepting on this port");
       }
       
       return rdv.accept(this);
    }

    @Override
    public synchronized AbstractChannel connect(String name, int port) throws InterruptedException {
    	RdV rdv = bm.findOrCreateRdV(port);
    	
    	return rdv.connect(this);
    }
    
    public String getName() {
    	return this.name;
    }

}