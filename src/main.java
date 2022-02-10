import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class main {

	// Contains all the initial values, unmodified
	public static HashMap<Integer, Individual> puzzleHashMap;

	// An example of how we can hold all the data from each bin, 0-3 for each bin,
	// 0-9 for each value in the bin
	public static Float[][] originalBinHolder = new Float[4][10];
	public static Tower originalTower;
	public static void main(String[] args) {

		/*
		 * String fileName = args[0]; 
		 * int puzzleOption = Integer.parseInt(args[1]);
		 * float timeToRun = Float.parseFloat(args[2]);
		 */

		//For testing purposes
		//String fileName = "../puzzle1test.txt";	//for testing on Alyssa's PC
		String fileName = "puzzle2test.txt";
		int puzzleOption = 2;
		float timeToRun = 60;


		System.out.println("Solving Puzzle " + puzzleOption + " for " + timeToRun + " seconds...\n");

		openFile(fileName, puzzleOption);
		puzzleHashMap = generateRandomizedPopulation(5000, 2);  //increasing this gets better results
		System.out.println("Generated " + puzzleHashMap.size() + " population...\n");
		GeneticAlgo ga = new GeneticAlgo(timeToRun);
		Individual best = ga.GA(puzzleOption, puzzleHashMap);
		System.out.println(best.getFitness());
		printTower(best.getPieces());
		
		
		//Piece[] child = GeneticAlgo.reproductionFunction(puzzleHashMap.get(0).getPieces(), puzzleHashMap.get(1).getPieces(), 0.1f);
		//System.out.println("Parent 1:");
		//printTower(originalTower.getPieces());
		//System.out.println(GeneticAlgo.fitnessFunction(2, originalTower));
		//System.out.println("Parent 2:");
		//printTower(puzzleHashMap.get(1).getPieces());
		//System.out.println("Child:");
		//printTower(child);
		
		
		//printTower(best.getPieces());
		
		/*	

		printBins(best.getData());
		System.out.println(best.getFitness());
		*/
		
		/*
		// Breed parent 1 and parent 2 to create a child, mutating 10% of the genes
		Float[][] child = GeneticAlgo.reproductionFunction(puzzleHashMap.get(0).getData(), puzzleHashMap.get(1).getData(), 0.1f);

		// Example of two parents and then their child
		System.out.println("Parent 1:");
		printBins(puzzleHashMap.get(0).getData());
		System.out.println("Parent 2:");
		printBins(puzzleHashMap.get(1).getData());
		System.out.println("Child:");
		printBins(child);
		*/
	}

	/**
	 * Prints all four bins
	 * 
	 * @param bin
	 */
	public static void printBins(Float[][] bin) {
		for (int k = 0; k <= 3; k++) {
			System.out.println();
			System.out.print("Bin " + (k+1) + ":\t");
			for (int j = 0; j <= 9; j++) {
				System.out.print(bin[k][j] + "\t");
			}
		}
		System.out.println("\n\n");
	}
	
	public static void printTower(Piece[] pieces) {
		System.out.println();
		for(int i = 0; i < pieces.length; i++) {
			
			if(pieces[i].isIncluded()) {
				System.out.println(pieces[i].getType() + ", " + pieces[i].getWidth() + ", " + pieces[i].getStrength() + ", " + pieces[i].getCost());
			}
			
		}
		System.out.println();
	}

	/**
	 * Generates population of randomly sorted bins of size sampleSize (For Puzzle 1)
	 * 
	 * @param sampleSize the size of the requested amount of individuals in population
	 * @return a hashmap of individuals
	 */
	public static HashMap<Integer, Individual> generateRandomizedPopulation(int sampleSize, int puzzleType) {
		HashMap<Integer, Individual> population = new HashMap<Integer, Individual>();
		Random rand = new Random();
		
		if(puzzleType == 1) {
			for (int i = 0; i < sampleSize; i++) {
				// make a copy of the original bin holder
				Float[][] randomizedBins = new Float[4][10];
				for (int j = 0; j <= 3; j++) {
					for (int k = 0; k <= 9; k++) {
						randomizedBins[j][k] = originalBinHolder[j][k];
					}
				}
				// taken from
				// https://www.tutorialspoint.com/how-to-randomize-and-shuffle-array-of-numbers-in-java
				for (int j = 0; j <= 3; j++) {
					for (int k = 0; k < randomizedBins[j].length; k++) {
						int index = rand.nextInt(randomizedBins[j].length - k);
						float tmp = randomizedBins[j][randomizedBins[j].length - 1 - k];
						randomizedBins[j][randomizedBins[j].length - 1 - k] = randomizedBins[j][index];
						randomizedBins[j][index] = tmp;
					}
				}
				// Enter the randomized child into the array
				population.put(i, new BinSet(randomizedBins));
			}
			
			
		}else if(puzzleType == 2) {
			
			Piece[] originalTowerPieces = originalTower.getPieces();
			int towerLength = originalTower.getPieces().length;
			
			for(int i = 0; i < sampleSize; i++) {
				Piece[] randTowerPieces = new Piece[towerLength];
				
				//clone the original tower pieces
				for(int j = 0; j < towerLength; j++) {
					randTowerPieces[j] = new Piece(originalTowerPieces[j].getType(), originalTowerPieces[j].getWidth(), originalTowerPieces[j].getStrength(), originalTowerPieces[j].getCost());
					
					if(rand.nextInt(100) >= 33) {
						randTowerPieces[j].invertInclusion();
					}
				}
				
				for(int k = 0; k < towerLength; k++) {
					int index = rand.nextInt(randTowerPieces.length - k);
					Piece tmp = randTowerPieces[randTowerPieces.length - 1 - k];
					randTowerPieces[randTowerPieces.length - 1 - k] = randTowerPieces[index];
					randTowerPieces[index] = tmp;
				}
				population.put(i, new Tower(randTowerPieces));
			}
			
			
		}
		
		return population;
	}

	/**
	 * Opens and reads the file
	 * 
	 * @param fileName
	 * @param puzzleOption
	 * @param timeToRun
	 */
	private static void openFile(String fileName, int puzzleOption) {
		// Taken from https://www.w3schools.com/java/java_files_read.asp

		try {
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			List<Piece> pieces = new ArrayList<Piece>();
			
			int i = 0;
			while (myReader.hasNextLine()) {

				String data = myReader.nextLine();
				if (puzzleOption == 1) {

					originalBinHolder[i / 10][i % 10] = Float.parseFloat(data);

				} else if (puzzleOption == 2) {

					String[] towerValues = data.split("\\t");
					Piece piece = new Piece(towerValues[0], Float.parseFloat(towerValues[1]), Float.parseFloat(towerValues[2]), Float.parseFloat(towerValues[3]));
					pieces.add(piece);
				}
				i++;
			}
			
			if (puzzleOption == 2) {
				Piece[] pieceArr = new Piece[pieces.size()];
				for(int j = 0; j < pieces.size(); j++) {
					pieceArr[j] = pieces.get(j);
				}
				originalTower = new Tower(pieceArr);
				//System.out.println(GeneticAlgo.fitnessFunction(2, originalTower));
				//printTower(pieceArr);
			}
			myReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error: file not found");
			e.printStackTrace();
		}
	}
}
