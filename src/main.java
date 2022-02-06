import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
public class main {

	public static void main(String[] args) {
		
		//Taken from https://www.w3schools.com/java/java_files_read.asp
		try {
		      File myObj = new File("test.txt");
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
