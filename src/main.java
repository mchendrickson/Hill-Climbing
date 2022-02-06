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
	
	/**
	 * Calculates the fitness of each individual
	 * @param puzzleOption 1 for puzzle 1 or 2 for puzzle 2
	 * @param bin1 the product of numbers
	 * @param bin2 the sum of numbers
	 * @param bin3 the calculated range
	 * @param height height of the tower
	 * @param totalCost cost to make the tower
	 * @return the fitness score
	 */
	public static float fitnessFunction(int puzzleOption, float bin1, float bin2, float bin3, 
			int height, int totalCost) {
		float fitnessScore = 0;
		if (puzzleOption == 1) {
			fitnessScore = bin1 + bin2 + bin3;
		} else if (puzzleOption == 2) {
			fitnessScore = (float) (10 + Math.pow(height, 2) - totalCost);
		}
		return fitnessScore;
	}
}
