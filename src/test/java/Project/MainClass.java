package Project;

import javax.swing.JFrame;

import RunFile.AryaRoboPanel;

public class MainClass {


	static WhatsappPanel Help = new WhatsappPanel();
	static StoreChatHistory chats = new StoreChatHistory();
	static shotreplyFunction reply  = new shotreplyFunction();
	static RunClass Action = new RunClass();
	static manageOrders Order = new manageOrders();
	static MainClass main = new MainClass();

	public static void main(String[] args) throws InterruptedException {

		JFrame frame = new JFrame("ROBO Arya");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.add(new AryaRoboPanel());
        frame.setLocationRelativeTo(null); // Center screen
        frame.setVisible(true);

	}

	@SuppressWarnings({ "unused" })
	public void whatsappChats() throws InterruptedException {

		Help.openWhatsapp();
		int middleloopValue = 0;
		int endloopValue = 1;
		outerLoop:do{
	     		Help.unReadChatList();
				Boolean chatAvailable = Help.OpenChat();	
				if(chatAvailable == true) {
					System.out.println("Chats is available");
					
					endloop:do {
						String message = Help.readMessage();
						System.out.println("Recived Message - "+ endloopValue +":"+message );
						Thread.sleep(2000);
						endloopValue++;

					}while( endloopValue<6);
					endloopValue =1;
					
				}else {
					System.out.println("Chats not available");
				}

			}while(true);

	}
}
