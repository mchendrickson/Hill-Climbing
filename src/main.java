import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class main {

	// Contains all the initial values, unmodified
	public static HashMap<Integer, Individual> puzzleHashMap;
//	public static HashMap<Integer, String[]> towerHashMap;

	// An example of how we can hold all the data from each bin, 0-3 for each bin,
	// 0-9 for each value in the bin
	public static Float[][] originalBinHolder = new Float[4][10];

	public static void main(String[] args) {

		/*
		 * String fileName = args[0]; int puzzleOption = Integer.parseInt(args[1]);
		 * float timeToRun = Float.parseFloat(args[2]);
		 */

		// For testing purposes
		String fileName = "../puzzle1test.txt";	//for testing on Alyssa's PC
//		String fileName = "puzzle1test.txt";
		int puzzleOption = 1;
		float timeToRun = 5;

		// Start the timer
//		Timer timer = new Timer(timeToRun);
//		timer.start(); // If the timer is finished, timer.finished() returns true

		System.out.println("Solving Puzzle " + puzzleOption + " for " + timeToRun + " seconds...\n");

		// Open the file, then generate two parents
		openFile(fileName, puzzleOption);
		puzzleHashMap = generateRandomizedPopulation(100);	//increasing this gets better results
		System.out.println("Generated " + puzzleHashMap.size() + " bin holders...\n");
    
		GeneticAlgo ga = new GeneticAlgo(timeToRun);
		Individual best = ga.GA(puzzleOption, puzzleHashMap);
		printBins(best.getData());
		System.out.println(best.getFitness());
		
		
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

	/**
	 * Generates population of randomly sorted bins of size sampleSize (For Puzzle 1)
	 * 
	 * @param sampleSize the size of the requested amount of individuals in population
	 * @return a hashmap of individuals
	 */
	public static HashMap<Integer, Individual> generateRandomizedPopulation(int sampleSize) {
		HashMap<Integer, Individual> population = new HashMap<Integer, Individual>();
		Random rand = new Random();

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

			int i = 0;
			while (myReader.hasNextLine()) {

				String data = myReader.nextLine();
				if (puzzleOption == 1) {

					originalBinHolder[i / 10][i % 10] = Float.parseFloat(data);

				} else if (puzzleOption == 2) {

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
}
