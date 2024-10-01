package Implem;

public abstract class AbstractChannel {
	
	Broker broker;
	
	public AbstractChannel(Broker b) {
		this.broker =b;
	}
	
	public abstract int read(byte[] bytes, int offset, int length) throws DisconnectedException;
	public abstract int write(byte[] bytes, int offset, int length) throws DisconnectedException;
	public abstract void disconnect();
	public abstract boolean disconnected();
}
