package Project;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class openWhatsapp {
	String Arya = null;
	WebDriver driver;

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

	public void readCurrentMessage() throws InterruptedException {
		do {
			
			driver.findElement(By.xpath("//div[text()='Favourites']")).click();
			Thread.sleep(100);
			driver.findElement(By.xpath("//div[text()='Unread']")).click();
			Thread.sleep(1000);
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			List<WebElement> NewChat = driver.findElements(By.xpath("//div[@class='x10l6tqk xh8yej3 x1g42fcv']/div/div/div/div[2]/div[2]/div[2]/span/div"));

			if (NewChat.size() > 0) {
				System.out.println("Available");
				driver.findElement(By.xpath("(//div[@class='x10l6tqk xh8yej3 x1g42fcv'])[1]")).click();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

				String lastMessageText = "";
				String EndChat = null;
				int nextChat = 0;

				long startTime = System.currentTimeMillis(); // start time
				do{
					List<WebElement> messages = driver.findElements(By.cssSelector("div.message-in"));
					String currentLastMessage = messages.get(messages.size() - 1).getText();

					if (!currentLastMessage.equals(lastMessageText)) {
						lastMessageText = currentLastMessage;
						WebElement lastMessage = messages.get(messages.size() - 1);
						lastMessage.getText();

						// Clean the text (remove timestamp)
						String messageText = lastMessage.getText();
						messageText = messageText.replaceAll("\\d{1,2}:\\d{2}\\s?(AM|PM)?", "").trim();

						System.out.println("Received Message: " + messageText);

						EndChat = messageText;
						Arya = messageText;

						// Call reply function
						ReplyFunctions ChatGPTReply = new ReplyFunctions();	
						String Send = ChatGPTReply.generateReply(messageText);

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

					Thread.sleep(3000); // check every 3 seconds
				} while (nextChat < 8);
				System.out.println("Exit chat");
			}else {
				System.out.println("Chats Not Available");
			}

		}while(!Arya.equals("Leave"));

	}

	
}
