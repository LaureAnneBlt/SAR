package Implem;

public abstract class AbstractTask extends Thread {
	
	AbstractTask(AbstractBroker b, Runnable r) {
		
	};
	
	static AbstractBroker getBroker() {
		return null;
	}; 

}
