package Tests;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		InitialTest test = new InitialTest();
		
		try {
			test.testOneClient();
			test.testMultipleClients();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
