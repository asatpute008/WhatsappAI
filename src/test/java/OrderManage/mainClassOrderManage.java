package OrderManage;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;

import Project.WhatsappPanel;

public class mainClassOrderManage {
	static WebDriver Idriver;
	public mainClassOrderManage(WebDriver Rdriver) {
		Idriver = Rdriver;
	}

	public mainClassOrderManage() {

	}
	
	static WhatsappPanel whatsapp = new  WhatsappPanel(Idriver);
	static mainClassOrderManage main = new mainClassOrderManage();
	static  GenerateOrderReply response = new  GenerateOrderReply(whatsapp.driver);

	String oldMessage = "null" ;
	
	@SuppressWarnings("unused")
	public void ExecuteOrdermManage() throws InterruptedException, AWTException, IOException {
		whatsapp.openWhatsapp();

		Outerloop:do {
			whatsapp.unReadChatList();
			System.out.println("Unread Chat Open -");
		
			unreadchatloop:do {
				boolean presentChatMessageAvailavble = main.verifyMessage();
				boolean unReadChatAvailibility = whatsapp.AvailableChats();				

				if(presentChatMessageAvailavble == true) {
					System.out.println("Current Chat Message Available");
					main.readAndsend();
					break unreadchatloop;

				}else if(unReadChatAvailibility  == true) {
					System.out.println("New Chat Message Available");
					whatsapp.OpenChat();
					main.readAndsend();
					break unreadchatloop;

				}else {
					System.out.println("Unread Chats Not Available");
					break unreadchatloop;

				}
			}while(true);
		
		
		}while(true);
	}
	
	
	@SuppressWarnings({ "unused"})
	public void readAndsend() throws InterruptedException, AWTException, IOException {

		int newMessageValue = 0;

		newMessageLoop:do {
			Thread.sleep(500);
			newMessageValue++;
			System.out.println("New Message Available - "+ newMessageValue);
			String newMessage = whatsapp.readMessage();

			if(!oldMessage.equals(newMessage)) {
				oldMessage = newMessage;
				String userName = whatsapp.userName();
				String replyGenerate = response.generateReply(userName, newMessage);			
				System.out.println("Final Reply: "+ replyGenerate);
				whatsapp.sendReply(replyGenerate);
				newMessageValue = 0;
			}

			Thread.sleep(500);

		}while(newMessageValue < 20);
	}
	
	@SuppressWarnings("unused")
	public boolean verifyMessage() {
		System.out.println("Check verifyMessage - 1");
		boolean returnValue = false;
		if(!oldMessage.equals("null")){	
			String newMessage = whatsapp.readMessage();
			if(!oldMessage.equals(newMessage)) {
				returnValue = true;
			}
		}else {
			returnValue = false;
		}
		return returnValue;
	}
}
