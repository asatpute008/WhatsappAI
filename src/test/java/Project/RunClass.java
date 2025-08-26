package Project;

import java.awt.AWTException;

public class RunClass {

	static String Closing;
	static String lastMessage = "null";
	static int Count;
	static Boolean onChatMessage;
	public static void main(String[] args) throws InterruptedException, AWTException {
		
		WhatsappPanel Help = new WhatsappPanel();
		Help.openWhatsapp();
		do { 
			Help.unReadChatList();
			Boolean chatsAvilibility = Help.OpenChat();
			System.out.println("Open Chats");
			  
			String getMessage = Help.readMessage();
			if(lastMessage.equals("null")){
				onChatMessage = false;
			}if(!getMessage.equals(lastMessage)) {
		
				onChatMessage = true;
			}else {
				onChatMessage = false;
				
			}
			
			if(chatsAvilibility == true || onChatMessage == true) {
				do {	
					String currentLastMessage = Help.readMessage();

					if(!currentLastMessage.equals(lastMessage)) {
						lastMessage = currentLastMessage;

						if("Logout".equals(lastMessage)){
							break;
						}
						String Send = Help.generateReply(currentLastMessage);
						Help.sendReply(Send);
						Count = 0;


						System.out.println("Message Send Successfully");

						
					}else {
						Thread.sleep(3000);
						System.out.println("Message not available"+ Count++);
					}
				}while(Count < 5);
			}else {
				System.out.println("Chats Not available");
			}

			if("Logout".equals(lastMessage)){
				break;
			}
		}while(true);
		
		Help.logOutWhatsapp();
	}

}
