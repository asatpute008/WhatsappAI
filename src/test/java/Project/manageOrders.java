package Project;

import java.awt.AWTException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class manageOrders {

    static StoreChatHistory Sheet = new StoreChatHistory();
//    static StoreChatHistory items = new  StoreChatHistory();
    static WhatsappPanel panelAction = new  WhatsappPanel();
    static manageOrders order = new manageOrders();
    LocalDateTime now = LocalDateTime.now();
    static String userOrder;
    
	public String reply(String userName, String currentLastMessage) throws IOException, InterruptedException, AWTException {
		String returnValue = "Please select Other Option";
		userOrder = "Order_"+userName;
		boolean itemAvailibility = false;
		
		List<String> itemList = Sheet.getItemList();
		StringBuilder resultBuilder = new StringBuilder();

		for (int i = 0; i < itemList.size(); i++) {
		    resultBuilder.append((i + 1)).append(". ").append(itemList.get(i));
		    if (i < itemList.size() - 1) {
		        resultBuilder.append("\n"); // Add new line except after last item
		    }
		    if(itemList.get(i).equals(currentLastMessage)) {
		    	itemAvailibility =true;
		    }
		}
			
	//condition as per the message input
		if(Sheet.checkFileExists(userOrder) == false) {
			Sheet.createCustomerOrderFile(userOrder);
			String result = resultBuilder.toString();
			returnValue = result;
			
		}else if(itemAvailibility==true){
			String Response = order.generateResponse(userOrder, currentLastMessage);
			System.out.println("item is available:" + Response);
			returnValue = "Your Food is"+Response;
		}else if("Yes".equals(currentLastMessage)){
			
			//current date and time 
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HHmmss");
			String Date = now.format(formatterDate);
			String Time = now.format(formatterTime);
			
			//Create OrderID
			String orderID = "CAFE"+Time;
			
			//Create File Name 
			String fileName = userOrder;
			
			//Get order Details
			String OrderDetails = Sheet.readFile(userOrder);		
			
			
			Sheet.addCustomerOrderDetails(orderID, Date, fileName, OrderDetails);
			
			String Response = order.ConfirmOrder(orderID);
			
			returnValue = Response;
			
		}else {
			System.out.println("item is not availabe");
		}
		
		return returnValue;
	}
	
	
	public String generateResponse(String userFile, String Message) {
		 Sheet.storeCustomerOrder(userFile, Message);
		 String returnValue = Sheet.readFile(userFile);
		return returnValue;
		
	}
	
	
	public String  ConfirmOrder(String orderId) {	
		List<String> orderDetails = Sheet.getCustomerOrderDetails(orderId);
		String returnValue = "Order Id: " + orderDetails.get(0) +"\n Date: " + orderDetails.get(1) + "\n Your Order: " + orderDetails.get(3);
		return returnValue;	
	}
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
		manageOrders help = new manageOrders();
//		System.out.println(help.ConfirmOrder("1"));
		System.out.println("Out put: \n"+help.reply("Boss", "Test"));
		
	}
}
