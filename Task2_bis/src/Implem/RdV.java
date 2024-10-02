package Implem;

public class RdV {

	Broker acceptBroker;
	Broker connectBroker;

	boolean isConnected;

	Channel acceptChannel;
	Channel connectChannel;


	public RdV() {
		this.isConnected = false;
	}

	private void rwait() {
		while (acceptChannel == null || connectChannel == null) {
			try {
				wait();
			}
			catch(InterruptedException ex) {

			}
		}
	}

	// Cette méthode est appelée par le broker qui veut se connecter
	public synchronized Channel connect(Broker b, int port) throws InterruptedException {

		System.out.println(b.getName() + " is trying to connect on port: " + port);
		this.connectBroker = b;

		connectChannel = new Channel(connectBroker, port, new CircularBuffer(512), new CircularBuffer(512));

		if (acceptBroker != null) {
			acceptChannel.connect(connectChannel, connectBroker.getName());
			notify(); // On réveille le thread serveur (accept)
		}
		else {
			rwait();
		}

		System.out.println("Connection established between " + acceptBroker.getName() + " and " + connectBroker.getName());
		return connectChannel;
	}

	// Cette méthode est appelée par le broker qui veut accepter la connexion
	public synchronized Channel accept(Broker b, int port) throws InterruptedException, IllegalStateException {

		System.out.println(b.getName() + " is accepting on port: " + port);
		this.acceptBroker = b;

		acceptChannel = new Channel(connectBroker, port, new CircularBuffer(512), new CircularBuffer(512));

		if (connectBroker != null) {
			acceptChannel.connect(connectChannel, acceptBroker.getName());
			notify(); // On réveille le thread client (connect)
		}
		else {
			rwait();
		}

		return acceptChannel;
	}

}