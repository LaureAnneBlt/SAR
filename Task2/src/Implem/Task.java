package Implem;

public class Task extends AbstractTask {
	
	Broker broker;
	Runnable r;

    Task(Broker b, Runnable r) {
        super(b, r);
        this.broker = b;
        this.r = r;
    }
    
    public void run() {
    	r.run();
    }
    
    static Broker getBroker() {
    	AbstractTask t = (AbstractTask) currentThread();
		return t.broker;
    }

}