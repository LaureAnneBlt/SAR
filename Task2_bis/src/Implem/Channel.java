
package Implem;

public class Channel extends AbstractChannel {

	CircularBuffer in;
	CircularBuffer out;

	Channel remoteChannel;

	int port;

	boolean isConnected;
	boolean dangling; // Booleen qui indique si l'autre bout est connecté
	String remoteName;

	public Channel(Broker b, int port, CircularBuffer in, CircularBuffer out) {
		super(b);
		this.port = port;
		this.in = in;
		this.out = out;
		this.isConnected = true;
	}

    @Override
    public
    synchronized int read(byte[] bytes, int offset, int length) throws DisconnectedException {

    	if(!isConnected) {
    		throw new DisconnectedException("The channel is not connected, cannot read");
    	}

    	int bytesRead = 0;

    	try {
            while (bytesRead == 0) {
                if (this.in.empty()) {
                    synchronized(this.in) {
                        while(this.in.empty()) {
                            if (!isConnected || dangling) {
                                throw new DisconnectedException("The channel is not connected, cannot read");
                            }
                            try {
                                this.in.wait();
                            } catch (InterruptedException e) {

                            }
                        }
                    }
                }
                while (bytesRead < length && !this.in.empty()) {
                    bytes[offset + bytesRead++] = this.in.pull();
                }
                if (bytesRead != 0) {
                    synchronized (this.in) {
                        this.in.notify();
                    }
                }
            }
        } catch (DisconnectedException e) {
            if (this.isConnected) {
                this.isConnected = false;
                synchronized (this.out) {
                    this.out.notifyAll();
                }
            }
            throw e;
        }
        return bytesRead;
    }

    @Override
    public
    synchronized int write(byte[] bytes, int offset, int length) throws DisconnectedException {

    	if(!isConnected) {
    		throw new IllegalStateException("The channel is not connected, cannot write");
    	}

    	int bytesWritten = 0;

    	while (bytesWritten == 0) {
            if (this.out.full()) {
                synchronized(this.out) {
                    while(this.out.full()) {
                        if (!this.isConnected) {
                            throw new DisconnectedException("The channel is not connected, cannot write");
                        }
                        if (this.dangling) {
                            return length;
                        }
                        try {
                            this.out.wait();
                        } catch (InterruptedException e) {

                        }
                    }
                }
            }
            while (bytesWritten < length && !this.out.full()) {
                this.out.push(bytes[offset + bytesWritten++]);
            }
            if (bytesWritten != 0) {
                synchronized (this.out) {
                    this.out.notify();
                }
            }
        }

        return bytesWritten;
    }

    @Override
    public
    void disconnect() throws IllegalStateException {
    	if(!isConnected) {
    		throw new IllegalStateException("The channel is already disconnected");
    	}

    	this.isConnected = false;
    	this.remoteChannel.dangling = true;
    }

    @Override
    public
    boolean disconnected() throws UnsupportedOperationException {
        return !isConnected;
    }

    public void connect(Channel remoteChannel, String name) {
    	this.remoteChannel = remoteChannel;
    	this.remoteChannel.remoteChannel = this;

    	this.remoteChannel.out = this.in;
    	this.out = remoteChannel.in;

    	this.remoteName = name;
    }

}
