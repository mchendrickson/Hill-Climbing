import java.util.HashMap;
import java.util.Random;

public class GeneticAlgo {

	float duration;
	Timer timer;


	public GeneticAlgo(float duration) {
		this.duration = duration;
		this.timer = new Timer(duration);
	}
	
	/**
	 * Performs complete genetic algorithm, to be called from main
	 * 
	 * @param population (uninitialized)
	 * @return best individual
	 */
	public Individual GA(int puzzleOption, HashMap<Integer, Individual> population){
		HashMap<Integer, Individual> newPopulation = new HashMap<Integer, Individual>();
		float bestScore = 0;
		int bestKey = 0;
		timer.run();
		do {
			Random rand = new Random();
			for(int i = 0; i < population.size(); i++) {
				//randomly pick two parents from population
				Individual x = population.get(rand.nextInt(population.size()));
				Individual y = population.get(rand.nextInt(population.size()));
				
				/*
				 * Reproduction only set up to work with puzzle 1 
				 */
				
				//get the data of the child
				Float[][] childData = reproductionFunction(x.getData(), y.getData(), 0.05f);
				//add child to new population
				newPopulation.put(i, new BinSet(childData));
				//set the fitness score of the new child
				newPopulation.get(i).setFitness(fitnessFunction(1, newPopulation.get(i)));
				if(newPopulation.get(i).getFitness() > bestScore) {
					bestScore = newPopulation.get(i).getFitness();	//keep track of best child
					bestKey = i;
				}
			}
		} while (!timer.finished());
		return newPopulation.get(bestKey);	//return best member of new population
	}
	
	/**
	 * Calculates the fitness of each individual
	 * 
	 * @param puzzleOption 1 for puzzle 1 or 2 for puzzle 2
	 * @param individual calculating the score for this individual
	 * @return the fitness score
	 */
	public static float fitnessFunction(int puzzleOption, Individual individual) {
		float fitnessScore = 0;
		if (puzzleOption == 1) {
			float [][] bins = Utility.convertToPrimitive(individual.getData());

			float bin1 = calculateBin1(bins[0]);
			float bin2 = calculateBin2(bins[1]);
			float bin3 = calculateBin3(bins[2]);
			fitnessScore = bin1 + bin2 + bin3;
		} else if (puzzleOption == 2) {
			Piece [] pieces = individual.getPieces();
			
			float totalCost = getCost(pieces);
			int height = getHeight(pieces);
			fitnessScore =  (float) (10 + Math.pow(height, 2) - totalCost);
		}
		return fitnessScore;
	}


	/**
	 * Calculates the product of numbers in bin 1
	 * @param bins numbers to be multiplied
	 * @return the product of the bin
	 */
	public static float calculateBin1(float[] bin1) {
		float product = 1;
        for (int i = 0; i < bin1.length; i++) {
        	product *= bin1[i];
        }
        return product;
	}
	
	/**
	 * Calculate the sum of the numbers in bin 2
	 * @param bin2 the numbers to be added
	 * @return the sum of the bin
	 */
	public static float calculateBin2(float[] bin2) {
		float sum = 0;
        for (int i = 0; i < bin2.length; i++) {
        	sum += bin2[i];
        }
        return sum;
	}
	
	/**
	 * Calculates the range of the numbers in bin 3
	 * @param bin3 the numbers to get the range
	 * @return the range of the bin
	 */
	public static float calculateBin3(float[] bin3) {
		float max = bin3[0];
        float min = bin3[0];
        for (int i = 0; i < bin3.length; i++) {
            if (bin3[i] > max) {
                max = bin3[i];
            } else if (bin3[i] < min) {
                min = bin3[i];
            }
        }
        return max - min;
	}
	
	/**
	 * Gets the height of the tower
	 * @param tower the constructed tower
	 * @return the height
	 */
	public static int getHeight(Piece [] pieces) {		
		return pieces.length;
	}
	
	/**
	 * Gets the total cost of building the tower
	 * @param tower the constructed tower
	 * @return the total cost of the tower
	 */
	public static float getCost(Piece [] pieces) {
		int cost = 0;
		for (Piece piece : pieces) {
			cost += piece.getCost();
		}
		return cost;
	}
	
	/**
	 * Breed two parents to create a child
	 * 
	 * @param parent1
	 * @param parent2
	 * @param mutationPercent from 0.0 to 1.0, will determine how many genes we
	 *                        randomly swap once we breed
	 * @return child
	 */
	public static Float[][] reproductionFunction(Float[][] parent1, Float[][] parent2, float mutationPercent) {
		Float[][] child = new Float[4][10];
		Random rand = new Random();

		// Take the 0th and 2nd columns from the first parent and the 1st and 3rd from
		// the second
		for (int i = 0; i <= 3; i++) {
			if (i % 2 == 0) {
				child[i] = parent1[i];
			} else {
				child[i] = parent2[i];
			}
		}

		// Mutate the genes so they can cross buckets
		for (int i = 0; i < mutationPercent * 40; i++) {
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
}

