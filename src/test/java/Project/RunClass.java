package Project;

public class RunClass {

	public static void main(String[] args) throws InterruptedException {
		
		openWhatsapp Help = new openWhatsapp();
		Help.OpenWhatsapp();
		Thread.sleep(1000);
		Help.readCurrentMessage();

	}

}
