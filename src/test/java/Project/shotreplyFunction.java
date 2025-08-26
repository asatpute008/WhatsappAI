package Project;

import java.util.LinkedHashMap;
import java.util.Map;

public class shotreplyFunction {
	
	public String generateReply(String userId, String messageText) {
	    if (messageText == null || messageText.trim().isEmpty()) {
	        return "Not available";
	    }

	    String lowerMsg = messageText.toLowerCase();

	    // Map of keywords → reply
	    Map<String[], String> replyMap = new LinkedHashMap<>();

	    replyMap.put(new String[]{"hello", "hey", "hii", "how are you", "kasaha"}, "hello "+userId);
	    replyMap.put(new String[]{"thank you", "thanks", "thx", "tnx"}, "Welcome!");
	    replyMap.put(new String[]{"bye", "goodbye", "see you", "take care"}, "Bye, take care!");
//	    replyMap.put(new String[]{"yes", "yaa", "ha", "ok"}, "Okay, noted!");
//	    replyMap.put(new String[]{"no", "na", "nahi"}, "Alright, no problem.");
//	    replyMap.put(new String[]{"what is your name", "your name"}, "I am Arya assistent of Mr Aniket");
	    replyMap.put(new String[]{"joke", "funny", "laugh"}, "Here's a joke for you: Why don’t skeletons fight each other? They don’t have the guts!");

	    // Check message against all keywords
	    for (Map.Entry<String[], String> entry : replyMap.entrySet()) {
	        for (String keyword : entry.getKey()) {
	            if (lowerMsg.contains(keyword)) {
	                return entry.getValue();
	            }
	        }
	    }

	    // Default if no match
	    return "Not available";
	}
	
	public static void main(String[] args) {
		shotreplyFunction help = new shotreplyFunction();
		
		System.out.println(help.generateReply("Vidi", "Ani"));
	}

}
