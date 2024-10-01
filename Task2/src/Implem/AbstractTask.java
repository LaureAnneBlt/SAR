package Implem;

public abstract class AbstractTask extends Thread {
	Broker broker;
	Runnable runnable;
	QueueBroker qb;
	
	AbstractTask(Broker b, Runnable r) {
		this.broker = b;
		this.runnable = r;
		
	}

	public AbstractTask(QueueBroker qb, Runnable r) {
		this.qb = qb;
		this.runnable = r;
	};
}