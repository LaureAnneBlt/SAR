package Tests;

import Implem.AbstractBroker;
import Implem.AbstractChannel;
import Implem.Broker;

public class TestClient {

    public void test() {
        
        AbstractBroker client = new Broker("client");
        AbstractChannel channel = client.connect("server", 8080);
        
        String msg = "Test";
        byte[] msgBuffer = msg.getBytes();
        
        int bytesWritten = channel.write(msgBuffer, 0, msgBuffer.length);
        assert(bytesWritten == msgBuffer.length);
        
        channel.disconnect();
    }
}