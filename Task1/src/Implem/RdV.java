package Implem;

public class RdV {
	
	Broker acceptBroker, connectBroker;
	int port;
	boolean isConnected;
	
	Channel acceptChannel;
	Channel connectChannel;
	
	
	public RdV(int port) {
		this.port = port;
		this.isConnected = false;
	}
	
	// Cette méthode est appelée par le broker qui veut se connecter
	public synchronized Channel connect(Broker b) throws InterruptedException {
		this.connectBroker = b;
		
		while(acceptBroker == null) {
			wait(); // On attend qu'un broker accept existe sur ce port
		}
		
		while(!isConnected) {
			wait();
		}
		
		return connectChannel;
	}
	
	// Cette méthode est appelée par le broker qui veut accepter la connexion
	public synchronized Channel accept(Broker b) throws InterruptedException, IllegalStateException {
		if(acceptBroker != null) {
			throw new IllegalStateException("Only one broker can accept on this port");
		}
		
		this.acceptBroker = b;
		
		notifyAll();
		
		while(connectBroker == null) {
			wait(); // on attend une demande de connexion
		}
		
		CircularBuffer in = new CircularBuffer(256); 	
		CircularBuffer out = new CircularBuffer(256); 	
		
		this.connectChannel = new Channel(in, out);
		this.acceptChannel = new Channel(out, in);
		
		this.isConnected = true;
		notifyAll(); // on réveille le broker qui attend les connexions
		
		return acceptChannel;
	}

}
