package Project;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StoreChatHistory {

	public void storeRecivedMessage(String userID, String Message) {
		String data = "\nYou: "+ Message;

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
		String data = "\nMe: "+ Message;

		try {
			FileWriter writer = new FileWriter("D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\" + userID + ".txt", true); // true = append mode
			if (data.length() < 250) {
				writer.write(data);  	   
				writer.close();
			}else {
				int limit = Math.min(Message.length(), 300);
				String result = Message.substring(0, limit);

				System.out.println("Length of line: " + result.length());
				writer.write(result);  	   
				writer.close();
			}
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

//	

	public static void main(String[] args) {
		StoreChatHistory Help = new StoreChatHistory();
//		String UserName = "TestFile";
//		String test = "Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is (222) aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniketHii my name is aniketHii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniketHii my name is aniketHii(33) my name is aniket";
//		Help. checkFileExists(UserName);
//		Help.storeReplyMessage(UserName, test);
//
//		Help.readFile(UserName);
//		Help.copyImage();

	}
}