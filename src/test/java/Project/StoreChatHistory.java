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

	static StoreChatHistory Sheet = new StoreChatHistory();
	
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
	
	public void storeCustomerOrder(String userID, String Message) {
		
//		  if(Sheet.checkFileExists(userID) == false) {
//			  Sheet.createCustomerOrderFile(userID);
//		  }
		     String data =  "\nitem -" + Message;
		try {
			FileWriter writer = new FileWriter("D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\" + userID + ".txt", true); // true = append mode
			writer.write(data);
			writer.close();
			//	        	    System.out.println("Recived message appended successfully to " + userID + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
  public void createCustomerOrderFile(String fileName) {
	     String data =  "Your Order is: \n";
			try {
				FileWriter writer = new FileWriter("D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\" +fileName + ".txt", true); // true = append mode
				writer.write(data);
				writer.close();
//System.out.println("Recived message appended successfully to " + userID + ".txt");
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
	
	public void creatOrderList() {
		String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\menuList.csv";

        // Updated CSV Header
        String header = "Id,Menu Item,Stock";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append(header);
            System.out.println("CSV file created successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void CustomerList() {
		String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\customerList.csv";

        // Updated CSV Header
        String header = "OrderId,Date,FileName, OrderDetails";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append(header);
            System.out.println("CSV file created successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void addCustomerOrderDetails(String OrderId, String Date, String FileName, String OrderDetails) {
		String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\customerList.csv";
             System.out.println("Order Details: "+ OrderDetails);
        // Updated CSV Header
		 String orderDetailsRow = "\n" + OrderId + "," + Date + "," + FileName + "," + OrderDetails;

	        try (FileWriter writer = new FileWriter(filePath, true)) { // true = append mode
	            writer.append(orderDetailsRow);
	            System.out.println("Row appended successfully at: " + filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public List<String> getCustomerOrderDetails(String orderId) {
		String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\customerList.csv";
		  List<String> returnValues = new ArrayList<>();
		try {
			
			 List<String> lines = Files.readAllLines(Paths.get(filePath));

			 // Skip header (index 0)
	            for (int i = 1; i < lines.size(); i++) {
	                String[] columns = lines.get(i).split(",");

	                if (columns.length >= 4 && columns[0].trim().equals(orderId)) {
//	                    List<String> row = new ArrayList<>();
	                	returnValues.add(columns[0].trim()); // OrderId
	                	returnValues.add(columns[1].trim()); // Date
	                	returnValues.add(columns[2].trim()); // FileName
	                	returnValues.add(columns[3].trim()); // OrderDetails

	                    	
	                }
	            }
            System.out.println(returnValues);
			
		}catch(IOException e) {
            e.printStackTrace();
        }
		
		return returnValues;
	}
	
	public void addNewItem(String itemName) {
		 String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\menuList.csv";
	        String menuItem = itemName;
	        int stock = 0;

	        try {
	            // Read all lines from the CSV file
	            List<String> lines = Files.readAllLines(Paths.get(filePath));

	         // If file has only header or empty
	            int newId = 1;
	            if (lines.size() > 1) {
	                // Get the last data line (not header)
	                String lastLine = lines.get(lines.size() - 1);
	                String[] columns = lastLine.split(",");

	                // Parse last Id
	                newId = Integer.parseInt(columns[0].trim()) + 1;
	            }     

	            // Prepare new row
	            String newRow = "\n" + newId + "," + menuItem + "," + stock;

	            // Append new row
	            try (FileWriter writer = new FileWriter(filePath, true)) {
	                writer.append(newRow);
	            }

	            System.out.println("Row added successfully: " + newRow);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
     
	public List<String> getItemList() throws IOException {

        String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\menuList.csv";

        List<String> menuItems = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Skip header (start from index 1)
            for (int i = 1; i < lines.size(); i++) {
                String[] columns = lines.get(i).split(",");
                if (columns.length >= 2) {
                    menuItems.add(columns[1].trim()); // Add Menu Item
                }
            }

//            System.out.println("Menu Items: " + menuItems);

        } catch (IOException e) {
            e.printStackTrace();
        }
		return menuItems;
		 
	}


	public static void main(String[] args) throws IOException {
		StoreChatHistory Help = new StoreChatHistory();
//		String UserName = "TestFile";
//		String test = "Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is (222) aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniketHii my name is aniketHii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniket Hii my name is aniketHii my name is aniketHii(33) my name is aniket";
//		Help. checkFileExists("Aniket");
//		Help.storeReplyMessage("Aniket", "test");
//
//		Help.readFile("Aniket");
//		Help.copyImage();
//		Help.creatOrderList();
//		Help.addNewItem("Veg-Roll");
		List<String> list =Help.getItemList();
		System.out.println(list);
//		Help.CustomerList();
//		Help.addCustomerOrderDetails("101", "2025-08-31", "Order_Aniket","\"vada-Pav,Pizza\"");
		
//		Help.getCustomerOrderDetails("102");
		
	}
}