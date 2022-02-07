import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
public class main {
	
	//Contains all the initial values, unmodified
	public static HashMap<Integer, Float[][]> binHashMap;
	public static HashMap<Integer, String[]> towerHashMap;
	
	//An example of how we can hold all the data from each bin, 0-3 for each bin, 0-9 for each value in the bin
	public static Float[][] originalBinHolder = new Float[4][10];

	public static void main(String[] args) {

		/*
		String fileName = args[0];
		int puzzleOption = Integer.parseInt(args[1]);
		float timeToRun = Float.parseFloat(args[2]);
		*/
		
		//For testing purposes
		String fileName = "puzzle1test.txt";
		int puzzleOption = 1;
		float timeToRun = 5;
		
		//Start the timer
		Timer timer = new Timer(timeToRun);
		timer.start();
		
		System.out.println("Solving Puzzle " + puzzleOption + " for " + timeToRun + " seconds...");
		
		openFile(fileName, puzzleOption);
		binHashMap = generateRandomizedSample(1000);
		
		for(int k = 0; k <= 3; k++) {
	    	  System.out.println("\n");
	    	  for(int j = 0; j <= 9; j++) {
			    	System.out.print(binHashMap.get(1)[k][j] + "   ");
			  }
	    }
	}
	
	/**
	 * generates randomly sorted bins of size sampleSize
	 * @param sampleSize the size of the requested amount of children
	 * @return a hashmap of bins
	 */
	public static HashMap<Integer, Float[][]> generateRandomizedSample(int sampleSize){
		
		HashMap<Integer, Float[][]> localBinHashMap = new HashMap<Integer, Float[][]>();
		Random rand = new Random();
		
		for(int i = 0; i <= sampleSize; i++) {
			
			Float[][] randomizedBins = originalBinHolder.clone();
			
			//taken from https://www.tutorialspoint.com/how-to-randomize-and-shuffle-array-of-numbers-in-java
			for(int j = 0; j <= 3; j++) {
				
				for (int k = 0; k < randomizedBins[j].length; k++) {
				   int index = rand.nextInt(randomizedBins[j].length - k);
				   float tmp = randomizedBins[j][randomizedBins[j].length - 1 - k];
				   randomizedBins[j][randomizedBins[j].length - 1 - k] = randomizedBins[j][index];
				   randomizedBins[j][index] = tmp;
				}
			}
			
			localBinHashMap.put(i, randomizedBins);
			
		}
		
		return localBinHashMap;
	}
	
	public static void reproductionFunction(Float[][] binHolder1, Float[][] binHolder2, float mutationAmount) {
		
		
	}
	
	
	/**
	 * Opens and reads the file
	 * @param fileName
	 * @param puzzleOption
	 * @param timeToRun
	 */
	private static void openFile(String fileName, int puzzleOption) {
		
		// TODO Puzzle 1: Outputs all four bins on each line
		// TODO Puzzle 1: Outputs max score at the end of the run
		

		//Taken from https://www.w3schools.com/java/java_files_read.asp
		
		try {
		      File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      
		      int i = 0;
		      while (myReader.hasNextLine()) {
		    	  
		    	  String data = myReader.nextLine();
		    	  if(puzzleOption == 1) {
		    		  
		    		  originalBinHolder[i/10][i%10] = Float.parseFloat(data);
		    		  
		    	  }else if(puzzleOption == 2) {
		    		  
		    		  String[] towerValues = data.split("\\t");
		    		  //towerHashMap.put(i, towerValues);
		    	  }
		    	  
		    	  i++;
		    	  
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
