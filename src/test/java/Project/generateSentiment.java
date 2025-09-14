package Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayDeque;
import java.util.Scanner;

public class generateSentiment {

	StoreChatHistory ChatHistory = new StoreChatHistory();

	public String createPrompt(String userName, String newMessage) {

		boolean fileAvailibility = ChatHistory.checkFileExists(userName);

		if(fileAvailibility == false) {
			ChatHistory.storeRecivedMessage(userName, newMessage);
			String ReturnMessage = "\n generate the reply for '" + newMessage + "'";
			return ReturnMessage;
		}else {
			String History = ChatHistory.readFile(userName);
			String ReturnMessage = "You are Robo, the user's friendly buddy.\r\n" +
									"Talk casually, like a supportive friend, not like a formal assistant.\r\n" +
									"Keep replies short and clear, but personal and caring.\r\n" +
									"If the user is happy, celebrate with them.\r\n" +
									"If the user is sad or stressed, comfort them.\r\n" +
									"Avoid sounding robotic or too professional.\r\n" +
									"Dont provide the multiple reply options provide sing reply.\r\n"+
									"\r\n" +
									"Chat History:\r\n" +
									History + "\r\n" +
									"\r\n" +
									"User: " + newMessage + "\r\n" +
									"Robo:";
			ChatHistory.storeRecivedMessage(userName, newMessage);
			// First message case
			return ReturnMessage;
		}

	}

	public static void main(String[] args) {
		generateSentiment chatApp = new generateSentiment();
		StoreChatHistory ChatHistory = new StoreChatHistory();

		String replys = "Hello line";
		String userId = "Aniket Satpute";
		ChatHistory.storeRecivedMessage(userId, replys);
		// Generate prompt
		String prompt = chatApp.createPrompt(userId, replys);
		System.out.println("Prompt for API: " + prompt);

		ChatHistory.storeReplyMessage(userId, "Kasa aahes tu");
	}
}
