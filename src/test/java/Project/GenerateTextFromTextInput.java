package Project;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class GenerateTextFromTextInput {

	public static void main(String[] args) {
	    Client client = Client.builder().apiKey("AIzaSyAlS23e4cVjHvvsYftdM5WCVEhe0dSZOx4").build();

	    GenerateContentResponse response =
	        client.models.generateContent(
	            "gemini-2.5-flash",
	            "Explain how AI works in a few words",
	            null);

	    System.out.println(response.text());
	  }
}
