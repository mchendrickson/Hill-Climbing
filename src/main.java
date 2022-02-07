import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
public class main {
	
	public static HashMap<Integer, Float> binHashMap = new HashMap<Integer, Float>();
	public static HashMap<Integer, String[]> towerHashMap = new HashMap<Integer, String[]>();
	
	public static void main(String[] args) {

		/*
		String fileName = args[0];
		int puzzleOption = Integer.parseInt(args[1]);
		float timeToRun = Float.parseFloat(args[2]);
		*/
		
		String fileName = "puzzle1test.txt";
		int puzzleOption = 1;
		float timeToRun = 5;
		
		System.out.println("Running algorithm " + puzzleOption + " for " + timeToRun + " seconds...\n");
		
		Timer timer = new Timer(timeToRun);
		timer.start();
		
		openFile(fileName, puzzleOption, timeToRun);
		
	}
	
	
	/**
	 * Opens and reads the file
	 * @param fileName
	 * @param puzzleOption
	 * @param timeToRun
	 */
	private static void openFile(String fileName, int puzzleOption, float timeToRun) {
		
		// TODO Puzzle 1: Outputs all four bins on each line
		// TODO Puzzle 1: Outputs max score at the end of the run
		System.out.println("Solving Puzzle " + puzzleOption + " for " + timeToRun + " seconds...");

		//Taken from https://www.w3schools.com/java/java_files_read.asp
		
		try {
		      File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      
		      int i = 0;
		      while (myReader.hasNextLine()) {
		    	  String data = myReader.nextLine();
		    	  if(puzzleOption == 1) {
		    		  binHashMap.put(i, Float.parseFloat(data));
		    	  }
		    	  i++;
		    	  
		    	  System.out.println(data);
		      }
		      
		      myReader.close();
		    
		} catch (FileNotFoundException e) {
		    	System.out.println("Error: file not found");
		    	e.printStackTrace();
		}
	}
	
	public static void reproductionFunction() {
		
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
