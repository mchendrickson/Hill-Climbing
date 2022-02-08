import java.util.Collection;
import java.util.HashMap;

public class GeneticAlgo {

	float duration;
	Timer timer;
	HashMap<Integer, Individual> population;


	public GeneticAlgo(float duration, HashMap<Integer, Individual> population) {
		this.duration = duration;
		this.timer = new Timer(duration);
		this.population = population;
	}
	
	/**
	 * Performs complete genetic algorithm, to be called from main
	 * 
	 * @param population (uninitialized)
	 * @return best individual
	 */
	public Individual GA(HashMap<Integer, Individual> population){
		Individual best = null;
		timer.run();
		do {
			HashMap<Integer, Individual> newPopulation = new HashMap<Integer, Individual>();
			for(int i = 0; i < population.size(); i++) {
				/*TODO: randomly select 2 members of population
				 * 	-make child
				 * 	-maybe mutate?
				 * 	-add child to new population
				 */
			}

		} while (!timer.finished());
		return best;	//return best member of new population
	}
	
	/**
	 * Calculates the fitness of each individual
	 * 
	 * @param puzzleOption 1 for puzzle 1 or 2 for puzzle 2
	 * @param bin1         the product of numbers
	 * @param bin2         the sum of numbers
	 * @param bin3         the calculated range
	 * @param height       height of the tower
	 * @param totalCost    cost to make the tower
	 * @return the fitness score
	 */
	public static float fitnessFunction(int puzzleOption, float bin1, float bin2, float bin3, int height,
			int totalCost) {
		float fitnessScore = 0;
		if (puzzleOption == 1) {
			fitnessScore = bin1 + bin2 + bin3;
		} else if (puzzleOption == 2) {
			fitnessScore = (float) (10 + Math.pow(height, 2) - totalCost);
		}
		return fitnessScore;
	}

	/**
	 * Calculates the product of numbers in bin 1
	 * @param bin1 numbers to be multiplied
	 * @return the product of the bin
	 */
	public static float calculateBin1(float[][] bin1) {
		float product = 1;
        for (int i = 0; i < bin1[0].length; i++) {
        	product *= bin1[0][i];
        }
        return product;
	}
	
	/**
	 * Calculate the sum of the numbers in bin 2
	 * @param bin2 the numbers to be added
	 * @return the sum of the bin
	 */
	public static float calculateBin2(float[][] bin2) {
		float sum = 0;
        for (int i = 0; i < bin2[1].length; i++) {
        	sum += bin2[1][i];
        }
        return sum;
	}
	
	/**
	 * Calculates the range of the numbers in bin 3
	 * @param bin3 the numbers to get the range
	 * @return the range of the bin
	 */
	public static float calculateBin3(float[][] bin3) {
		float max = bin3[2][0];
        float min = bin3[2][0];
        for (int i = 0; i < bin3[2].length; i++) {
            if (bin3[2][i] > max) {
                max = bin3[2][i];
            } else if (bin3[2][i] < min) {
                min = bin3[2][i];
            }
        }
        return max - min;
	}
	
	/**
	 * Gets the height of the tower
	 * @param tower the constructed tower
	 * @return the height
	 */
	public static int getHeight(HashMap<Integer, String[]> tower) {		
		return tower.size();
	}
	
	/**
	 * Gets the total cost of building the tower
	 * @param tower the constructed tower
	 * @return the total cost of the tower
	 */
	public static int getCost(HashMap<Integer, String[]> tower) {
		int cost = 0;
		Collection<String[]> towerValues = tower.values();
		for (String[] pieceCost : towerValues) {
			cost += Integer.parseInt(pieceCost[3]);
		}
		return cost;
	}
}

