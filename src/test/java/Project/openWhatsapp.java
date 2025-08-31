package Project;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class openWhatsapp {
	WebDriver driver;
	
	
	public String Arya = "" ;
	String lastMessageText = "";
	String EndChat = null;
	String currentLastMessage;
	String userID;
	int nextChat = 0;

	public openWhatsapp() {

	}

	//open Whatsapp Site
	public void OpenWhatsapp() {
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "D:\\MangePanel\\Whatsapphandel\\src\\test\\resources\\Driver\\chromedriver.exe");

		driver.get("https://web.whatsapp.com/");
		driver.manage().window().maximize(); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		try {
			driver.findElement(By.xpath("//div[text()='Continue']")).click();

		}catch(NoSuchElementException e) {
			System.out.println("Element not found " + e.getMessage());
		}
	}



  // CheckChats Chat List 
	public void readCurrentMessage() throws InterruptedException {
		do {
			Thread.sleep(500);
			driver.findElement(By.xpath("//div[text()='Favourites']")).click();
			Thread.sleep(500);
			driver.findElement(By.xpath("//div[text()='Unread']")).click();

			openWhatsapp Avail = new openWhatsapp();
			Arya = Avail.ChatAvailibility();
			System.out.println("Final Execution: "+Arya);
		}while(!" LogOut".equals(Arya));

	}


	public String ChatAvailibility() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		List<WebElement> NewChat = driver.findElements(By.xpath("//div[@class='x10l6tqk xh8yej3 x1g42fcv']/div/div/div/div[2]/div[2]/div[2]/span/div"));

		if (NewChat.size() > 0) {
			System.out.println("Available");
			driver.findElement(By.xpath("(//div[@class='x10l6tqk xh8yej3 x1g42fcv'])[1]")).click();
			Thread.sleep(2000);
			
			//find person name in chat 
			String getUserName = driver.findElement(By.xpath("(//div[@class='x78zum5 x1cy8zhl x1y332i5 xggjnk3 x1yc453h']/div/div/div/span)[1]")).getText();
			userID = getUserName;
			
			openWhatsapp reply = new openWhatsapp();
			String ValueForArya = reply.replyMessage();
			System.out.println("Exit chat");
			
			return ValueForArya ;
		}
		else {
			System.out.println("Chats Not Available");
			return null;
		}
	}

	public String replyMessage() throws InterruptedException {
    
		   String returnValue = null;
		//long startTime = System.currentTimeMillis(); // start time
		do{

			List<WebElement> messages = driver.findElements(By.cssSelector("div.message-in"));           
			System.out.println("Check message availibility ="+ messages);

			if(!messages.isEmpty()) {
				currentLastMessage = messages.get(messages.size() - 1).getText();
				System.out.println("Last message: " + currentLastMessage);

				if (!currentLastMessage.equals(lastMessageText)) {
					lastMessageText = currentLastMessage;
					WebElement lastMessage = messages.get(messages.size() - 1);

					// Clean the text (remove timestamp)
					String messageText = lastMessage.getText();
					messageText = messageText.replaceAll("\\d{1,2}:\\d{2}\\s?(AM|PM)?", "").trim();

					System.out.println("Received Message: " + messageText);

					EndChat = messageText;
					
					returnValue = messageText;
					System.out.println("Final Execution: " + returnValue);
					
					
					if(returnValue.equals("Logout")){
						break;
					};
					
					// Call reply function
					ReplyFunctions ChatGPTReply = new ReplyFunctions();	
					String Send = ChatGPTReply.generateReply(userID, messageText);

					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
					System.out.println("Response -"+Send);

					Thread.sleep(200);
					driver.findElement(By.xpath("//div[@aria-label='Type a message']")).sendKeys(Send);
					Thread.sleep(1000);

					nextChat = 0;
				}else {
					System.out.println("No new message" + nextChat );
					nextChat ++;
				} 
			}else {
				nextChat = 8;
				System.out.println("Chat is emoty" + nextChat );
				Thread.sleep(500);
				driver.findElement(By.xpath("//div[text()='Favourites']")).click();
				Thread.sleep(500);
				driver.findElement(By.xpath("//div[text()='Unread']")).click();
			}
			Thread.sleep(3000); // check every 3 seconds
		} while (nextChat < 8);
		
		return returnValue;
	}
	
	
 
}
