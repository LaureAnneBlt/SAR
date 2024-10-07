package Implem;

public class Task extends AbstractTask {

	Broker broker;
	QueueBroker queueBroker;
	Runnable r;

    public Task(Broker b, Runnable r) {
        super(b, r);
        this.broker = b;
        this.r = r;
    }

    public Task(QueueBroker qb, Runnable r) {
    	super(qb, r);
    	this.queueBroker = qb;
    	this.r = r;
    }

    @Override
	public void run() {
    	r.run();
    }

    public static Broker getBroker() {
    	AbstractTask t = (AbstractTask) currentThread();
		return t.broker;
    }

    public static QueueBroker getQueueBroker() {
    	AbstractTask t = (AbstractTask) currentThread();
		return t.qb;
    }

    public static Task getTask() {
		Task t = (Task) currentThread();
		return t;
	}

}