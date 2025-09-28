package OrderManage;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;

import Project.StoreChatHistory;
import Project.manageOrders;

public class GenerateOrderReply {

	WebDriver driver;
	public GenerateOrderReply(WebDriver Rdriver) {
		driver = Rdriver;
	}

	public GenerateOrderReply() {

	}

	static StoreChatHistory userFile = new StoreChatHistory();
	static manageOrders  orders = new  manageOrders ();

	@SuppressWarnings("unused")
	public static String generateReply(String userName, String Message) throws IOException, InterruptedException, AWTException {
		String returnValue = null;
		boolean FileAvailibility = userFile.checkFileExists("Order_"+userName);
		
		if(FileAvailibility == true) {
			String responseMessage = orders.reply(userName, Message);
			String lowerMsg = Message.toLowerCase();
			
			if(lowerMsg.equals("yes")){
				returnValue = responseMessage +"\nThank you for Order";
				userFile.deleteFile("Order_"+userName);
			}else if(lowerMsg.equals("no")){
				userFile.deleteFile("Order_"+userName);
				returnValue = responseMessage +"You Dont have any orders,Thank you";
				
			}else if("Please select Other Option".equals(responseMessage)){
				
				String itemList = orders.reply(userName, Message);
				returnValue ="We dont have"+Message+" item" + responseMessage +"\n"+ itemList ;
				
			}else {
				returnValue = responseMessage + "\nConfirm yor order with \n -Yes \n -No \n Add more order";
			}
		}else {
			String itemList = orders.reply(userName, Message);
			String message = "Hello " + userName + "\nWelcome to My cafe" + "\nToday's Special Menu List\n";
			return message + itemList;
		}
		return returnValue;
	}

	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
//		System.out.println(GenerateOrderReply.generateReply("Boss", "Piss"));
		userFile.deleteFile("Order_Boss");
	}
}
