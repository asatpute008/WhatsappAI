package Project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class StoreChatHistory {
	
	public void storeRecivedMessage(String userID, String Message) {
	        String data = "\nSender: "+ Message;

	        try {
	        	  FileWriter writer = new FileWriter("D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\" + userID + ".txt", true); // true = append mode
	        	    writer.write(data);
	        	    writer.close();
//	        	    System.out.println("Recived message appended successfully to " + userID + ".txt");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	public void storeReplyMessage(String userID, String Message) {
        String data = "\nReply: "+ Message;

        try {
        	  FileWriter writer = new FileWriter("D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\" + userID + ".txt", true); // true = append mode
        	    writer.write(data);
        	    writer.close();
//        	    System.out.println("Send Message appended successfully to " + userID + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void deleteFile(String userID) {
		String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\"+userID+".txt";

        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully: " + filePath);
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
	}
	
	public String readFile(String userID) {
		
        String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\" + userID + ".txt";
        int lastLines = 20;
        String returnvalue = "" ;
        List<String> lines = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (lines.size() > lastLines) {
                    lines.remove(0); // Keep only last 10 lines
                }
            }

            for (String l : lines) {
                returnvalue = returnvalue +" \n" + l;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
		return returnvalue;
	}
	
	public boolean checkFileExists(String userID) {
        File file = new File("D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\" + userID + ".txt");
        return file.exists() && file.isFile(); // Ensures it's a file, not directory
    }
	
	public static void main(String[] args) {
		StoreChatHistory Help = new StoreChatHistory();
		String UserName = "Aniket";
//		Help.storeRecivedMessage(UserName, "Line 1");
//		Help.storeReplyMessage(UserName, "Line 2");
//		Help.storeRecivedMessage(UserName, "Line 3");
//		Help.storeReplyMessage(UserName, "Line 4");
		 
		Help.readFile(UserName);

	}
}