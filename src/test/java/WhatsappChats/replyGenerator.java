package WhatsappChats;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;

import Project.ReplyFunctions;
import Project.shotreplyFunction;

public class replyGenerator {
      WebDriver driver;
      
      WebDriver Idriver;
      public replyGenerator(WebDriver Rdriver) {
     	 driver = Rdriver;
      }
      
      public replyGenerator() {
     	 
      }
      
	public String generateReply(String userID, String messageText) {

		try {
			String returnValue;
			ReplyFunctions ChatGPTReply = new ReplyFunctions();	
			shotreplyFunction shotGenerate = new shotreplyFunction();

			String sendShot = shotGenerate.generateReply(userID, messageText);
			returnValue =  sendShot;
			
			if(sendShot.equals("Not available")) {
				@SuppressWarnings("static-access")
				String Send = ChatGPTReply.generateReply(userID , messageText);
				System.out.println("Response long -"+Send);
				returnValue = Send;
			};

//			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			System.out.println("Response shot -"+sendShot);
			return returnValue;
		}catch(NoSuchElementException e) {
			System.out.println("Element not fond in Response generation"+ e.getMessage());

			return null;
		}
	}
}
