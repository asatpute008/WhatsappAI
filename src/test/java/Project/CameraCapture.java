package Project;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import nu.pattern.OpenCV;

public class CameraCapture {

	 public void Capture(String formatter) {
		 System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
		 nu.pattern.OpenCV.loadLocally();
	        // Load OpenCV native library
//	        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	        // Open the default camera (index 0)
	        VideoCapture camera = new VideoCapture(0);

	        if (!camera.isOpened()) {
	            System.out.println("Error: Camera not accessible!");
	            return;
	        }

	        // Create a matrix to store the image
	        Mat frame = new Mat();

	        // Read one frame from the camera
	        if (camera.read(frame)) {
	            System.out.println("Captured image!");

	            // Save the image to a file
	            String filePath = "D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\image\\"+formatter+".jpg";
	            Imgcodecs.imwrite(filePath, frame);

	            System.out.println("Image saved at: " + filePath);
	        } else {
	            System.out.println("Failed to capture image!");
	        }

	        // Release the camera
	        camera.release();
	    }
	 
	 public void copyImage(String imageName) { 
		  try {
	            // File to copy
	            File fileToCopy = new File("D:\\Aniket\\Automation\\Project-2\\WhatsappAI\\target\\image\\"+imageName+".jpg");

	            // Put file into clipboard
	            List<File> files = new ArrayList<>();
	            files.add(fileToCopy);

	            FileTransferable transferable = new FileTransferable(files);
	            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);

	            System.out.println("File copied to clipboard (like Ctrl + C).");
	        } catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    // Custom Transferable for file list
	    static class FileTransferable implements Transferable {
	        private List<File> files;

	        public FileTransferable(List<File> files) {
	            this.files = files;
	        }

	        @Override
	        public DataFlavor[] getTransferDataFlavors() {
	            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
	        }

	        @Override
	        public boolean isDataFlavorSupported(DataFlavor flavor) {
	            return DataFlavor.javaFileListFlavor.equals(flavor);
	        }

	        @Override
	        public Object getTransferData(DataFlavor flavor) {
	            return files;
	        }
	    }
	    
	    public static void main(String[] args) {
			CameraCapture help = new CameraCapture();
			help.Capture("2025-08-27_18:01:20");
		}
}
