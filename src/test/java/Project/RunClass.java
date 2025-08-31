package Project;

import java.awt.AWTException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import okio.AsyncTimeout;

public class RunClass {

	static String Closing;
	static String lastMessage = "null";
	static String  myOrder = "Active";
	static int Count;
	static Boolean onChatMessage;
	static String storeCameraImageName ;
	static WhatsappPanel Help = new WhatsappPanel();
	static StoreChatHistory chats = new StoreChatHistory();
	static shotreplyFunction reply  = new shotreplyFunction();
	static RunClass Action = new RunClass();
	static manageOrders Order = new manageOrders();
	private static String chatsAvailibility;

//	public static void main(String[] args) throws InterruptedException, AWTException, IOException {
//
//
//		Help.openWhatsapp();
//		outerLoop:do { 
//			Help.unReadChatList();
//			Boolean chatsAvilibility = Help.OpenChat();
//			System.out.println("Open Chats ");
//            
//			String getMessage = "null";
//			MessageLoop:do{ 
//				System.out.println("Start  MessageLoop");
//				String getMessage1 = Help.readMessage();
//				getMessage =getMessage1;
//				
//				System.out.println("End MessageLoop: "+ getMessage);
//
//			}while(getMessage.equals("null"));
//
//			if(lastMessage.equals("null")){
//				onChatMessage = false;
//			}else if(!getMessage.equals(lastMessage)) {
//
//				onChatMessage = true;
//			}else {
//				onChatMessage = false;
//
//			}
//
//			if(chatsAvilibility == true || onChatMessage == true) {
////				Boolean chatsAvilibilitytest = Help.OpenChat();
//				
//				System.out.println("Chats Available" + chatsAvailibility);
//				System.out.println("On Chats Message" + onChatMessage);
//				String userIdboss = Help.userName();
//				String validateBoss = userIdboss;
//				Count = 0;
//				middleLoop:do {	
//					System.out.println("Middleloop Start");
//					String currentLastMessage = lastMessage;
//					try {
//						String NewcurrentLastMessage = Help.readMessage();
//						currentLastMessage = NewcurrentLastMessage;
//					}catch(NoSuchElementException e) {
//						System.out.println("Element not found : "+ e.getMessage());
//					}
//
//					//	Normal person Message 
//					if(!currentLastMessage.equals(lastMessage)) {
//						lastMessage = currentLastMessage;
//
//						if(myOrder.equals("Active")) {
//
//							if("Boss".equals(userIdboss)&& currentLastMessage.equals("Help")) {
//								System.out.println("Boss conditon in order manage");
//								boolean bossReply = Action.BossChats(userIdboss, currentLastMessage);
//
//								if(bossReply ==true) {
//									break outerLoop;
//								}else {
//									break middleLoop;
//								}
//							}
//							
//							if(currentLastMessage.equals("Test")){
//								String response = Order.reply(userIdboss, currentLastMessage);
//								Help.sendReply("Menu List: \n"+ response + "\nNote: Provide item name ex. Burger");
//								
//							}else if(chats.checkFileExists("Order_"+userIdboss) == true) {
//							
//								String response = Order.reply(userIdboss, currentLastMessage);
//								Help.sendReply(response);
//								Help.sendReply("Confirm yor order with \n -Yes \n -No \n Add more order");
//							}else {
//								
//								String response = Order.reply(userIdboss, currentLastMessage);
//								Help.sendReply("Menu List: \n"+ response + "\nNote: Provide item name ex. Burger");
//							}
//                        
//						}else if("Boss".equals(userIdboss)) {
//
//							boolean bossReply = Action.BossChats(userIdboss, currentLastMessage);
//
//							if(bossReply ==true) {
//								break outerLoop;
//							}else {
//								break middleLoop;
//							}
//						}else{
//
//							lastMessage = currentLastMessage;
//							//Generate The message from gemini 
//							String Send = Help.generateReply(userIdboss, currentLastMessage);
//							Help.sendReply(Send);
//							System.out.println("Message Send Successfully");
//						}
//
//					}else {
//						Thread.sleep(3000);
//						System.out.println("Message not available"+ Count++);
//					}
//				}while(Count < 5);
//			}else {
//				System.out.println("Chats Not available");
//			}
//
//		}while(true);
//
//		Help.closedWindow();
//	}
//
//
//	public boolean Logout() throws InterruptedException, AWTException {
//
//		SendPasscodeEmail  sent = new SendPasscodeEmail ();
//		String recivedPasscode = sent.passcode();
//		Thread.sleep(100);
//		Help.sendReply("Provide 6 digit passcode sent on your mail wait 20 sec");
//		int countDown = 0;
//		int chance = 3;
//		boolean returnValue = false ;
//		Thread.sleep(2000);
//		logoutloop:do {
//			String getPasscode = Help.readMessage();
//
//			if(!getPasscode.equals(lastMessage)) {
//				lastMessage = getPasscode;
//
//				if(recivedPasscode.equals(getPasscode)) {
//					Help.logOutWhatsapp();	
//					returnValue = true;
//					break logoutloop;
//				}else {			
//					chance--;
//					if(chance == 0) {
//						countDown = 10;
//					}else {
//						Help.sendReply("Passcode is wrong you have "+chance+" chance ");
//					}
//				}
//			}else {
//				countDown++;
//			}
//			Thread.sleep(2000);
//		}while(countDown <10);
//
//		if(chance == 0) {
//			Help.sendReply("You are not authorised person sory I can logut the system");
//			Help.sendReply("How Can I help you");
//			Thread.sleep(200);
//			chats.deleteFile(Help.userName());
//			returnValue = false;
//		}else if(countDown >=10) {
//			Help.sendReply("You are not authorised person Time is out");
//			Help.sendReply("How Can I help you");
//			Thread.sleep(200);
//			chats.deleteFile(Help.userName());
//			returnValue = false;
//		}
//		return returnValue;						
//
//	}
//
//	public boolean SendPhoto() throws InterruptedException, AWTException {
//		System.out.println("Camera is open");
//		Help.sendReply("wait I am capturing the photo");
//
//		LocalDateTime now = LocalDateTime.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
//		String formattedTime = now.format(formatter);
//		storeCameraImageName = formattedTime;
//
//		CameraCapture image = new CameraCapture();
//
//		image.Capture(storeCameraImageName);
//		image.copyImage(storeCameraImageName);
//		Help.sendReply("Your Image is ready");
//		Thread.sleep(500);
//
//		Help.SendImage();
//		return true;
//	}
//
//	public boolean BossChats(String userIdboss, String bossMessage) throws InterruptedException, AWTException {
//		int BossChat = 0;
//		Help.sendReply("Hello "+ userIdboss);
//		Thread.sleep(100);
//		Help.sendReply(reply.generateBossReply(userIdboss, bossMessage));
//		boolean returnValue = false;
//
//		loop:do {
//			String BossMessage = Help.readMessage();
//			Thread.sleep(100);
//			String lowerBossMsg = BossMessage .toLowerCase();
//
//			if(!BossMessage.equals(lastMessage)){
//				lastMessage = BossMessage;
//				//Log Out Functionality 
//				if("logout".equals(lowerBossMsg)){
//
//					boolean value = Action.Logout();
//					if(value == true){
//						returnValue = true;
//						break loop;
//					}else {
//						returnValue = false;
//						break loop;
//					}
//				}
//				//Camera captured photo send functionality 
//				else if( lowerBossMsg.contains("capture") && (lowerBossMsg.contains("camera") || lowerBossMsg.contains("photo"))) {
//
//					Action.SendPhoto();
//					BossChat = 0;
//
//				}else if(lowerBossMsg.contains("manage orders")&&lowerBossMsg.contains("active")) {
//					myOrder = "Active";
//					Thread.sleep(500);
//					Help.sendReply("Manage Order is Activated");
//					break loop;
//				}else if(lowerBossMsg.contains("manage orders inactive")&&lowerBossMsg.contains("inactive")) {
//
//					myOrder = "Inactive";
//					Thread.sleep(500);
//					Help.sendReply("Manage Order is Activated");
//					break loop;
//				}
//				else if(lowerBossMsg.contains("connected applications")) {
//
//					Help.sendReply("This Functionality Not Implemented");
//					BossChat = 0;
//
//				}else if(lowerBossMsg.contains("play song")) {
//
//					Help.sendReply("This Functionality Not Implemented");
//					BossChat = 0;
//
//				}else {
//					//Generate The message from gemini 
//					String Send = Help.generateReply(userIdboss, BossMessage);
//					Help.sendReply(Send);
//					BossChat = 0;
//					System.out.println("Message Send Successfully");
//				}
//
//			}else {
//				Thread.sleep(3000);
//				System.out.println("Message not available"+ BossChat++);
//			}
//
//		}while(BossChat < 7);
//
//		return  returnValue ;
//	}

	public void Test(String userId, String currentLastMessage) throws IOException, InterruptedException, AWTException {
		String userIdboss = "Order_"+userId;
		if(currentLastMessage.equals("Test")){
			String response = Order.reply(userId, currentLastMessage);
//			Help.sendReply("Menu List: \n"+ response + "\nNote: Provide item name ex. Burger");
			System.out.println("Menu List: \n"+ response + "\nNote: Provide item name ex. Burger");
			
		}else if(chats.checkFileExists("Order_"+userIdboss) == true) {
		
			String response = Order.reply(userIdboss, currentLastMessage);
//			Help.sendReply(response);
//			Help.sendReply("Confirm yor order with \n -Yes \n -No \n Add more order");
			System.out.println("Confirm yor order with \n -Yes \n -No \n Add more order");
		}else {
			
			String response = Order.reply(userIdboss, currentLastMessage);
//			Help.sendReply("Menu List: \n"+ response + "\nNote: Provide item name ex. Burger");
			System.out.println("Menu List: \n"+ response + "\nNote: Provide item name ex. Burger");
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
		
		Action.Test("Boss", "Yes");
	}
}
