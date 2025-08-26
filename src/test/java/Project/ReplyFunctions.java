package Project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class ReplyFunctions {
	
	private static String StaticReply = "I cant understand what you say";
	static boolean replyType;
	static String userMessage;
	
	// Replace with your Gemini API Key
	private static final String API_KEY = "AIzaSyBLYZ8s6OZcQNecTqRdNBXAhlMcG9vBPZc";

	public static String generateReply(String userId, String recMessage) {
		String responseText = "";
        
		StoreChatHistory ChatHistory = new StoreChatHistory();
		

		
		//check if need more information
		String[] byeMessages = {"info", "plan", "joke", "place", "bore", "jaga", "means", "kay aahe", "list", "tour"};
		
		
		if (Arrays.stream(byeMessages).anyMatch(responseText.toLowerCase()::contains)) {
		    replyType = false;
		} else {
			 replyType = true;
		}
		 
		if(replyType == true) {
		generateSentiment  generatPrompt = new generateSentiment();
		String getuserMessage = generatPrompt.createPrompt(userId, recMessage);
		userMessage = getuserMessage;
		System.out.println("gemini prompt: "+ userMessage);
		}else {
			userMessage = recMessage;
		}
		
		try {
			// Gemini endpoint (latest model)
			String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

			// Open connection
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			// Create JSON body
			String safeMessage = userMessage.replace("\"", "\\\"");

			String jsonBody = "{"
			        + "\"contents\": [{\"parts\":[{\"text\":\"" + safeMessage + "\"}]}]"
			        + "}";
			// Send request
			try (OutputStream os = conn.getOutputStream()) {
				os.write(jsonBody.getBytes("utf-8"));
			}

			// Check HTTP response code
			int statusCode = conn.getResponseCode();
			InputStream inputStream = (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream();

			// Read response
			StringBuilder response = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
				String line;
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
			}

			// Parse JSON to extract "text"
			String jsonResponse = response.toString();
			if (statusCode == 200) {
				JSONObject obj = new JSONObject(jsonResponse);
				JSONArray candidates = obj.getJSONArray("candidates");
				JSONObject firstCandidate = candidates.getJSONObject(0);
				JSONArray parts = firstCandidate.getJSONObject("content").getJSONArray("parts");
				responseText = parts.getJSONObject(0).getString("text");
			} else {
				responseText = "Error: " + statusCode + " - " + jsonResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (responseText.contains("Error") || responseText.contains("error")) {
			 return StaticReply;
		}else {	
			ChatHistory.storeReplyMessage(userId, responseText);
			
			return responseText;
		}
	       }

	public static void main(String[] args) {

		String reply = generateReply("Ani jio","kuthe jaun");
		System.out.println("Gemini Response: " + reply);
	}
}

