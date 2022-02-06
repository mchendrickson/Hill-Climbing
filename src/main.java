import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
public class main {

	public static void main(String[] args) {
		
		String fileName = args[0];
		int algorithmNum = Integer.parseInt(args[1]);
		float timeToRun = Float.parseFloat(args[2]);
		
		System.out.println("Running algorithm " + algorithmNum + " for " + timeToRun + " seconds...");
		//Taken from https://www.w3schools.com/java/java_files_read.asp
		try {
		      File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      
		      while (myReader.hasNextLine()) {
		    	  String data = myReader.nextLine();
		    	  System.out.println(data);
		      }
		      
		      myReader.close();
		    
		} catch (FileNotFoundException e) {
		    	System.out.println("Error: file not found");
		    	e.printStackTrace();
		}
	}
}
