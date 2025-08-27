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
		StoreChatHistory chats = new StoreChatHistory();
		Help.openWhatsapp();
		outerLoop:do { 
			Help.unReadChatList();
			Boolean chatsAvilibility = Help.OpenChat();
			System.out.println(" Open Chats ");

			String getMessage = "null";
			do{ 
				String getMessage1 = Help.readMessage();
				getMessage =getMessage1;
				
			}while(getMessage.equals("null"));
			
			if(lastMessage.equals("null")){
				onChatMessage = false;
			}if(!getMessage.equals(lastMessage)) {

				onChatMessage = true;
			}else {
				onChatMessage = false;

			}

			if(chatsAvilibility == true || onChatMessage == true) {
				middleLoop:do {	

					String userId = Help.userName();
					String currentLastMessage = Help.readMessage();

					String lowerMsg = currentLastMessage.toLowerCase();

					if(!currentLastMessage.equals(lastMessage)) {
						lastMessage = currentLastMessage;
						//Log Out Functionality 
						if("Logout".equals(lastMessage)){
							SendPasscodeEmail  sent = new SendPasscodeEmail ();
							String recivedPasscode = sent.passcode();
							Thread.sleep(100);
							Help.sendReply("Provide 6 digit passcode sent on your mail wait 20 sec");
							int countDown = 0;
							int chance = 3;
							Thread.sleep(2000);
							do {
								String getPasscode = Help.readMessage();

								if(!getPasscode.equals(lastMessage)) {
									lastMessage = getPasscode;

									if(recivedPasscode.equals(getPasscode)) {
										Help.logOutWhatsapp();
										break outerLoop;
									}else {			
										chance--;
										if(chance == 0) {
											countDown = 10;
										}else {
											Help.sendReply("Passcode is wrong you have "+chance+" chance ");
										}

										
									}
								}else {
									countDown++;
								}
								Thread.sleep(2000);
							}while(countDown <10);

							if(chance == 0) {
								Help.sendReply("You are not authorised person sory I can logut the system");
								Help.sendReply("How Can I help you");
								Thread.sleep(200);
								chats.deleteFile(Help.userName());
								break middleLoop;
							}else if(countDown >=10) {
								Help.sendReply("You are not authorised person Time is out");
								Help.sendReply("How Can I help you");
								Thread.sleep(200);
								chats.deleteFile(Help.userName());
								break middleLoop;
							}

							//Camera captured photo send functionality 
						}else if(lowerMsg.contains("send") && (lowerMsg.contains("camera") || lowerMsg.contains("photo"))) {
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
						//Generate The message from gemini 
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

		}while(true);




		Help.closedWindow();
	}

}
