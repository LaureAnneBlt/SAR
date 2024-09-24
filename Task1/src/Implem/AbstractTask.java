package Implem;

public abstract class AbstractTask extends Thread {
	Broker broker;
	Runnable runnable;
	
	AbstractTask(Broker b, Runnable r) {
		this.broker = b;
		this.runnable = r;
		
	};
	
	static AbstractBroker getBroker() {
		return null;
	}; 

}
