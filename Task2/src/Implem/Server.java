package Implem;

public class Server {
    
    Broker broker;
    
    public Server(Broker b) {
        this.broker = b;
    }
    
    public void startServer(int port) throws InterruptedException {
        Runnable serverRunnable = () -> {
            try {
                System.out.println("Server started, waiting for connections...");
                
                while (true) {
                    AbstractChannel clientChannel = broker.accept(port);
                    System.out.println("New client connected");

                    // Pour chaque connexion de client, lancer un nouveau thread pour le gérer
                    new Thread(() -> handleClient(clientChannel)).start();
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Task serverTask = new Task(broker, serverRunnable);
        serverTask.start();
    }

    
    void handleClient(AbstractChannel channel) {
        byte[] buffer = new byte[256];
        int bytesRead;
        
        try {
            while ((bytesRead = channel.read(buffer, 0, buffer.length)) > 0) {
                channel.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.disconnect();
                System.out.println("Client disconnected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}