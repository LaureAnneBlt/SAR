package Implem;

public class QueueBroker {
	
	Broker broker;
	
	public QueueBroker(Broker broker) {
		this.broker = broker;
	}
	
	public String name() {
		return broker.getName();
	}
	
	public MessageQueue accept(int port) throws IllegalStateException, InterruptedException {
		Channel channel = (Channel)(broker.accept(port));
		return new MessageQueue(channel);
		
	}
	public MessageQueue connect(String name, int port) throws InterruptedException {
		Channel channel = (Channel)(broker.connect(name, port));
		return new MessageQueue(channel);
	
	}

}
