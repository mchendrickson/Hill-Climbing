import java.util.HashMap;
import java.util.Random;

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

}

