package Tests;

import java.util.Arrays;

import Implem.Broker;
import Implem.Channel;
import Implem.SimpleBroker;

public class TestServer {

    public void test() {
        
        Channel channel= null;
        
        Broker server = new SimpleBroker("server");
        
        while (channel == null) {
            channel = server.accept(8080);
        }
        
        byte[] msgBuffer = new byte[256];
        int offset = 0;
        
        
        while (!channel.disconnected()) {
            int bytesRead = channel.read(msgBuffer,  offset, msgBuffer.length);
            
            String msg = new String(msgBuffer, 0, bytesRead);
            
            offset = (offset + msgBuffer.length) % 256;
            assert(msg == "Test");
            
            System.out.println(msg.toString());
            
            Arrays.fill(msgBuffer, (byte) 0);
            
        }
        
        channel.disconnect();

    }
}