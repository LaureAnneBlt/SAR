package Abstract;

import Events.EventQueueBroker;
import Implem.Broker;

public abstract class AbstractTask extends Thread {
	public Broker broker;
	protected Runnable runnable;
	public EventQueueBroker eqb;

	protected AbstractTask(Broker b, Runnable r) {
		this.broker = b;
		this.runnable = r;

	}

	public AbstractTask(EventQueueBroker eqb, Runnable r) {
		this.eqb = eqb;
		this.runnable = r;
	}
}