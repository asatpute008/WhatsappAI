package Project;

import java.awt.AWTException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RunClass {

	static String Closing;
	static String lastMessage = "null";
	static int Count;
	static Boolean onChatMessage;
	static String storeCameraImageName ;
	public static void main(String[] args) throws InterruptedException, AWTException {
		
		WhatsappPanel Help = new WhatsappPanel();
		Help.openWhatsapp();
		do { 
			Help.unReadChatList();
			Boolean chatsAvilibility = Help.OpenChat();
			System.out.println(" Open Chats ");
			  
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
					
					String userId = Help.userName();
					String currentLastMessage = Help.readMessage();

					if(!currentLastMessage.equals(lastMessage)) {
						lastMessage = currentLastMessage;

						if("Logout".equals(lastMessage)){
							break;
						}else if("Send Your Photo".equals(lastMessage)) {
							System.out.println("Camera is open");
							Help.sendReply("wait I am capturing the photo");
							
							LocalDateTime now = LocalDateTime.now();
					        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
					        String formattedTime = now.format(formatter);
					        storeCameraImageName = formattedTime;
					        
							 CameraCapture image = new CameraCapture();

							 image.Capture(storeCameraImageName);
							 image.copyImage(storeCameraImageName);
							 Help.sendReply("Your Image is ready");
							 Thread.sleep(500);
							 
							 Help.SendImage();
						}
						
						String Send = Help.generateReply(userId, currentLastMessage);
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
