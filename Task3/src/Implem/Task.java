package Implem;

import Abstract.AbstractTask;
import Events.EventQueueBroker;

public class Task extends AbstractTask {

    public Task(Broker b, Runnable r) {
        super(b, r);
    }

    public Task(EventQueueBroker eqb, Runnable r) {
    	super(eqb, r);
    }

    @Override
	public void run() {
    	runnable.run();
    }

    public static Broker getBroker() {
    	AbstractTask t = (AbstractTask) currentThread();
		return t.broker;
    }

    public static EventQueueBroker getQueueBroker() {
    	AbstractTask t = (AbstractTask) currentThread();
		return (EventQueueBroker) t.eqb;
    }

    public static Task getTask() {
		Task t = (Task) currentThread();
		return t;
	}

}