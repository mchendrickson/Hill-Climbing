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
		timer.start(); //If the timer is finished, timer.finished() returns true
		
		System.out.println("Solving Puzzle " + puzzleOption + " for " + timeToRun + " seconds...\n");
		
		//Open the file, then generate two parents
		openFile(fileName, puzzleOption);
		binHashMap = generateRandomizedSample(2);
		System.out.println("Generated " + binHashMap.size() + " bin holders...\n");
		
		//Breed parent 1 and parent 2 to create a child, mutating 10% of the genes
		Float[][] child = reproductionFunction(binHashMap.get(0), binHashMap.get(1), 0.1f);
		
		//Example of two parents and then their child
		System.out.println("Parent 1:");
		printBins(binHashMap.get(0));
		System.out.println("Parent 2:");
		printBins(binHashMap.get(1));
		System.out.println("Child:");
		printBins(child);
		    
	}
	
	/**
	 * Prints all four bins
	 * @param bin
	 */
	public static void printBins(Float[][] bin) {
		for(int k = 0; k <= 3; k++) {
	    	  System.out.println();
	    	  for(int j = 0; j <= 9; j++) {
			    	System.out.print(bin[k][j] + "  ");
			  }
	    }
		System.out.println("\n\n");
	}
	
	/**
	 * Breed two parents to create a child
	 * @param parent1
	 * @param parent2
	 * @param mutationPercent from 0.0 to 1.0, will determine how many genes we randomly swap once we breed
	 * @return child
	 */
	public static Float[][] reproductionFunction(Float[][] parent1, Float[][] parent2, float mutationPercent) {
		Float[][] child = new Float[4][10];
		Random rand = new Random();

		//Take the 0th and 2nd columns from the first parent and the 1st and 3rd from the second
		for(int i = 0; i <= 3; i++) {
			if(i % 2 == 0) {
				child[i] = parent1[i];
			}else {
				child[i] = parent2[i];
			}
		}
		
		//Mutate the genes so they can cross buckets
		for(int i = 0; i < mutationPercent * 40; i++) {
			int randBin1 = rand.nextInt(4);
			int randValue1 = rand.nextInt(10);
			
			int randBin2 = rand.nextInt(4);
			int randValue2 = rand.nextInt(10);
			
			float temp = child[randBin1][randValue1];
			child[randBin1][randValue1] = child[randBin2][randValue2];
			child[randBin2][randValue2] = temp;
		}
		
		return child;
	}
	
	/**
	 * Generates randomly sorted bins of size sampleSize
	 * @param sampleSize the size of the requested amount of children
	 * @return a hashmap of bins
	 */
	public static HashMap<Integer, Float[][]> generateRandomizedSample(int sampleSize){
		
		HashMap<Integer, Float[][]> localBinHashMap = new HashMap<Integer, Float[][]>();
		Random rand = new Random();
		
		for(int i = 0; i < sampleSize; i++) {
			
			//make a copy of the original bin holder
			Float[][] randomizedBins = new Float[4][10];
			for(int j = 0; j <= 3; j++) {
				for(int k = 0; k <= 9; k++) {
					randomizedBins[j][k] = originalBinHolder[j][k];
				}
			}
			
			//taken from https://www.tutorialspoint.com/how-to-randomize-and-shuffle-array-of-numbers-in-java
			for(int j = 0; j <= 3; j++) {
				
				for (int k = 0; k < randomizedBins[j].length; k++) {
				   
				   int index = rand.nextInt(randomizedBins[j].length - k);
				   float tmp = randomizedBins[j][randomizedBins[j].length - 1 - k];
				   randomizedBins[j][randomizedBins[j].length - 1 - k] = randomizedBins[j][index];
				   randomizedBins[j][index] = tmp;
				}
			}
			
			//Enter the randomized child into the hashmap
			localBinHashMap.put(i, randomizedBins);
			
		}
		
		return localBinHashMap;
	}
	
	/**
	 * Opens and reads the file
	 * @param fileName
	 * @param puzzleOption
	 * @param timeToRun
	 */
	private static void openFile(String fileName, int puzzleOption) {
		
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
