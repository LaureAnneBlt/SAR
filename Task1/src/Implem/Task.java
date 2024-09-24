package Implem;

public class Task extends AbstractTask {
	
	static Broker broker;

    Task(Broker b, Runnable r) {
        super(b, r);
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    static Broker getBroker() {
    	return broker;
    }

}