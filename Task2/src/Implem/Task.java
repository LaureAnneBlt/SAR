package Implem;

public class Task extends AbstractTask {
	
	Broker broker;
	Runnable r;

    public Task(Broker b, Runnable r) {
        super(b, r);
        this.broker = b;
        this.r = r;
    }
    
    public Task(QueueBroker qb, Runnable r) {
    	super(qb, r);
    }
    
    public void run() {
    	r.run();
    }
    
    public static Broker getBroker() {
    	AbstractTask t = (AbstractTask) currentThread();
		return t.broker;
    }
    
    public static QueueBroker getQueueBroker() {
    	AbstractTask t = (AbstractTask) currentThread();
		return (QueueBroker)t.qb;
    }

}