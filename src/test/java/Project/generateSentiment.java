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
			String ReturnMessage =  "Chat history: " + History +  "\nNew message: '" + newMessage + "'" + "\nBased on the chat history and the new message, generate a single, natural, and concise line as a reply. Your response should continue the conversation smoothly and sound like a genuine friend";
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
