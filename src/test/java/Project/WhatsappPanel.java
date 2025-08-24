package Project;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WhatsappPanel {

	WebDriver driver;
	public WhatsappPanel(WebDriver Rdriver) {
		this.driver =Rdriver;
	}

	public WhatsappPanel() {

	}

	String currentLastMessage;

	//Open Site and closed unwanted model
	public void openWhatsapp() {
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

	//Tap On UnRead Chat List 
	public void unReadChatList() throws InterruptedException {

		try {
			Thread.sleep(500);
			driver.findElement(By.xpath("//div[text()='Favourites']")).click();
			Thread.sleep(500);
			driver.findElement(By.xpath("//div[text()='Unread']")).click();

			System.out.println("Open Unread Chat list");
		}catch(NoSuchElementException e) {
			System.out.println("Element not fond in UnreadChatlist"+ e.getMessage());
		}
	}

	public Boolean OpenChat() throws InterruptedException {
		try {
			Thread.sleep(2000);		
			List<WebElement> NewChat = driver.findElements(By.xpath("//div[@class='x10l6tqk xh8yej3 x1g42fcv']/div/div/div/div[2]/div[2]/div[2]/span/div"));

			if (NewChat.size() > 0) {
				System.out.println("Available");
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

	public String  readMessage() {

			try {
				List<WebElement> messages = driver.findElements(By.cssSelector("div.message-in"));  

				currentLastMessage = messages.get(messages.size() - 1).getText();
				System.out.println("Last message: " + currentLastMessage);
				WebElement lastMessage = messages.get(messages.size() - 1);

				// Clean the text (remove timestamp)
				String messageText = lastMessage.getText();
				messageText = messageText.replaceAll("\\d{1,2}:\\d{2}\\s?(AM|PM)?", "").trim();

				System.out.println("Received Message: " + messageText);

				System.out.println("Message Reading");

				return messageText;

			}catch(NoSuchElementException e) {
				System.out.println("Element not fond in Message reading"+ e.getMessage());
				return "null";
			}
	}

	public String generateReply(String messageText) {
		try {
			ReplyFunctions ChatGPTReply = new ReplyFunctions();	
			String Send = ChatGPTReply.generateReply(messageText);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			System.out.println("Response -"+Send);
			return Send;
		}catch(NoSuchElementException e) {
			System.out.println("Element not fond in Response generation"+ e.getMessage());

			return null;
		}
	}

	public void sendReply(String replyText) throws InterruptedException, AWTException {
		try {
			Thread.sleep(200);
			driver.findElement(By.xpath("//div[@aria-label='Type a message']")).sendKeys(replyText);
			Thread.sleep(500);

			Robot  Keyboard = new Robot();
			Keyboard.keyPress(KeyEvent.VK_ENTER);
			Keyboard.keyRelease(KeyEvent.VK_ENTER);

			System.out.println("Reply sent");
		}catch(NoSuchElementException e) {
			System.out.println("Element not fond reply sent"+ e.getMessage());
		}
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
