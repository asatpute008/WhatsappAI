package Project;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class ReplyFunctions {

	
	private static String StaticReply = "I can't understand what you say";
	static boolean replyType;
	static String userMessage;
	 private static final String API_KEY = "AIzaSyBx9p5WnCFqMOdiToC2auEsP0Vp_7Y6aj0"; // replace with your key

	 public static String generateReply(String userId, String recMessage) throws IOException, InterruptedException {
		 
		 String responseText = recMessage;
		    StoreChatHistory ChatHistory = new StoreChatHistory();

		    // Check if need more information
		    String[] byeMessages = {"info","information","details","mahiti","mhantat",
		            "write","plan","joke","funny","place","bore","jaga","means",
		            "kay aahe","list","tour"};

		    String lowerCaseResponse = responseText.toLowerCase();
		    replyType = !Arrays.stream(byeMessages).anyMatch(lowerCaseResponse::contains);

		    if (replyType) {
		        System.out.println("Print Short message");
		        generateSentiment generatPrompt = new generateSentiment();
		        String getuserMessage = generatPrompt.createPrompt(userId, recMessage);
		        userMessage = getuserMessage;
		        System.out.println("Gemini prompt: " + userMessage);
		    } else {
		        System.out.println("Print Long message");
		        userMessage = recMessage;
		    }
		    
		    
	        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

	        HttpClient client = HttpClient.newHttpClient();
            
	        String safeMessage = userMessage.replace("\"", "\\\"");
	       String requestBody = "{"
	                + "\"contents\": ["
	                + "{"
	                + "\"parts\": ["
	                + "{ \"text\": \"" + safeMessage + "\" }"
	                + "]"
	                + "}"
	                + "]"
	                + "}";

	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(apiUrl))
	                .header("Content-Type", "application/json")
	                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                .build();

	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	        if (response.statusCode() == 200) {
	            // Parse JSON and extract only the text
	            JSONObject obj = new JSONObject(response.body());
	            JSONArray candidates = obj.getJSONArray("candidates");
	            JSONObject firstCandidate = candidates.getJSONObject(0);
	            JSONArray parts = firstCandidate.getJSONObject("content").getJSONArray("parts");
	            String replyText = parts.getJSONObject(0).getString("text");

//	            System.out.println("Gemini reply: " + replyText);
	            return replyText;
	        } else {
	           return "Error: " + response.body();
	        }
	    }
	 
	 public static void main(String[] args) throws IOException, InterruptedException {
	    String reply = generateReply("Ani jio", "How are you");
	    System.out.println("Gemini Response: " + reply);
	}

}

