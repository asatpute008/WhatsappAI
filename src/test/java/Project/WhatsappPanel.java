package Project;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WhatsappPanel {

	public WebDriver driver;
	
	 WebDriver Idriver;
     public WhatsappPanel(WebDriver Rdriver) {
    	 driver = Rdriver;
     }
     
     public WhatsappPanel() {
    	 
     }

	String currentLastMessage;
//	String userID;

	//Open Site and closed unwanted model
	public void openWhatsapp() {
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "D:\\MangePanel\\Whatsapphandel\\src\\test\\resources\\Driver\\chromedriver.exe");

		driver.get("https://web.whatsapp.com/");
		driver.manage().window().maximize(); 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {
			WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Continue']")));
			continueButton.click();
			System.out.println("Clicked on Continue button");
		} catch (TimeoutException e) {
			System.out.println("Element not found within timeout: " + e.getMessage());
		}
	}

	//Tap On UnRead Chat List 
	public void unReadChatList() throws InterruptedException {

		try {
			driver.findElement(By.xpath("//div[text()='Groups']")).click();
			Thread.sleep(200);
			driver.findElement(By.xpath("//div[text()='Favourites']")).click();
			Thread.sleep(200);
			driver.findElement(By.xpath("//div[text()='Unread']")).click();

			System.out.println("Open Unread Chat list");
		}catch(NoSuchElementException e) {
			System.out.println("Element not fond in UnreadChatlist"+ e.getMessage());
		}
	}
	 
	public Boolean AvailableChats() throws InterruptedException {
		try {
			Thread.sleep(500);		
			List<WebElement> NewChat = driver.findElements(By.xpath("//div[@class='x10l6tqk xh8yej3 x1g42fcv']/div/div/div/div[2]/div[2]/div[2]/span/div"));

			if (NewChat.size() > 0) {
				System.out.println("Available");
				return true;
			}else {
				return false;
			}}catch(NoSuchElementException e) {
				System.out.println("Element not fond in Chat list"+ e.getMessage());
				return false;
			}
	}

	public Boolean OpenChat() throws InterruptedException {
		try {
			Thread.sleep(200);		
			List<WebElement> NewChat = driver.findElements(By.xpath("//div[@class='x10l6tqk xh8yej3 x1g42fcv']/div/div/div/div[2]/div[2]/div[2]/span/div"));

			if (NewChat.size() > 0) {
				driver.findElement(By.xpath("(//div[@class='x10l6tqk xh8yej3 x1g42fcv'])[1]")).click();
				Thread.sleep(2000);	

				return true;
			}
			else {
				return false;
			}

		}catch(NoSuchElementException e) {
			System.out.println("Element not fond in Chat list"+ e.getMessage());
			return false;
		}
	}
	
	public String userName() {
		//find person name in chat 
		String getUserName = driver.findElement(By.xpath("(//div[@class='x78zum5 x1cy8zhl x1y332i5 xggjnk3 x1yc453h']/div/div/div/span)[1]")).getText();
		return getUserName;
	}

	public String  readMessage() {

		try {

			List<WebElement> messages = driver.findElements(By.cssSelector("div.message-in"));  

			do{
				if (!messages.isEmpty() || messages.size() != 0) {
					currentLastMessage = messages.get(messages.size() - 1).getText();
					break;
				} 
			}while(true);

			System.out.println("Last message: " + currentLastMessage);
			WebElement lastMessage = messages.get(messages.size() - 1);

			// Clean the text (remove timestamp)
			String messageText = lastMessage.getText();
			messageText = messageText.replaceAll("\\d{1,2}:\\d{2}\\s?(AM|PM)?", "").trim();

			System.out.println("Received Message: " + messageText);

			return messageText;

		}catch(NoSuchElementException e) {
			System.out.println("Element not fond in Message reading"+ e.getMessage());
			return "null";
		}
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

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			System.out.println("Response shot -"+sendShot);
			return returnValue;
		}catch(NoSuchElementException e) {
			System.out.println("Element not fond in Response generation"+ e.getMessage());

			return null;
		}
	}

	public void sendReply(String replyText) throws InterruptedException, AWTException {
		try {
			Thread.sleep(200);
			WebElement replyMessage = driver.findElement(By.xpath("//div[@aria-placeholder='Type a message']"));			
			Actions act = new Actions(driver);
			act.click(replyMessage).sendKeys(replyText).perform();
			
			Thread.sleep(500);
			try {
				// Try Robot ENTER key first
				Robot keyboard = new Robot();
				keyboard.keyPress(KeyEvent.VK_ENTER);
				keyboard.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("Clicked using Robot ENTER key");
			} catch (AWTException e) {
				System.out.println("Robot failed, trying XPath click: " + e.getMessage());
				try {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
					WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-icon='wds-ic-send-filled']")));
					sendButton.click();
					System.out.println("Clicked using XPath");
				} catch (TimeoutException | NoSuchElementException ex) {
					System.out.println("Both Robot and XPath click failed: " + ex.getMessage());
				}
			}

			System.out.println("Reply sent");
		}catch(NoSuchElementException e) {
			System.out.println("Element not fond reply sent"+ e.getMessage());
		}
	}

	public void SendImage() throws InterruptedException, AWTException {
		
		driver.findElement(By.xpath("//div[@aria-placeholder='Type a message']")).click();
		Thread.sleep(100);
		Robot keyboard = new Robot();
		keyboard.keyPress(KeyEvent.VK_CONTROL);
		keyboard.keyPress(KeyEvent.VK_V);
		keyboard.keyRelease(KeyEvent.VK_V);
		keyboard.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@aria-label='Add a caption']")).sendKeys("Ready");
		keyboard.keyPress(KeyEvent.VK_ENTER);
		keyboard.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void closedWindow()
	{
		driver.quit();
	}

	public void logOutWhatsapp() throws InterruptedException {
		Thread.sleep(200);
		driver.findElement(By.xpath("(//button[@title='Menu'])[1]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[normalize-space(text())='Log out']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[normalize-space(text())='Log out']")).click();
	}
}
