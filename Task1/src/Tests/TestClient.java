package Tests;

import Implem.Broker;
import Implem.Channel;
import Implem.SimpleBroker;

public class TestClient {

    public void test() {
        
        Broker client = new SimpleBroker("client");
        Channel channel = client.connect("server", 8080);
        
        String msg = "Test";
        byte[] msgBuffer = msg.getBytes();
        
        int bytesWritten = channel.write(msgBuffer, 0, msgBuffer.length);
        assert(bytesWritten == msgBuffer.length);
        
        channel.disconnect();
    }
}