package Tests;

public class Main {
	
	public static void main(String[] args) {
		InitialTest test = new InitialTest();
		
		try {
			test.testOneClient();
			test.testMultipleClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
