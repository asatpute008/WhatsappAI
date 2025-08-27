package Project;

import java.util.LinkedHashMap;
import java.util.Map;

public class shotreplyFunction {
	
	public String generateReply(String userId, String messageText) {
	    if (messageText == null || messageText.trim().isEmpty()) {
	        return "Not available";
	    }

	    String lowerMsg = messageText.toLowerCase();
	    
	    if (lowerMsg.contains("you") && (lowerMsg.contains("job") || lowerMsg.contains("work"))) {
	        return "I am a Software tester";
	    };
	    
	    if(lowerMsg.contains("fev") && (lowerMsg.contains("like") || lowerMsg.contains("Food"))) {
	    	  return "Methi , siple dal, Rice, chapati";
	    };
	    
	    
	    

	    // Map of keywords → reply
	    Map<String[], String> replyMap = new LinkedHashMap<>();

	    replyMap.put(new String[]{"hello", "hey", "hii", "how are you", "kasaha"}, "hello "+userId);
	    replyMap.put(new String[]{"thank you", "thanks", "thx", "tnx"}, "Welcome!");
	    replyMap.put(new String[]{"bye", "goodbye", "see you", "take care"}, "Bye, take care!");
	    replyMap.put(new String[]{"what is your name", "your name", "who","who are you"}, "Hii, I am Aniket");
	    replyMap.put(new String[]{"qualification", "edjucation", "qlf.", "edu", "degree"}, "I completed Bachelor of Engineering in Electronics and Telecommunication");
	    

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
		
		System.out.println(help.generateReply("Vidi","job"));
	}

}
