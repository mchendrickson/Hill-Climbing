import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GeneticAlgo {

	float duration;
	Timer timer;
	static int generation = 0; 
	static float bestScore = 0;
	static int bestKey = 0;


	public GeneticAlgo(float duration) {
		this.duration = duration * 1000;
		this.timer = new Timer(duration);
	}

	/**
	 * Performs complete genetic algorithm, to be called from main
	 * 
	 * @param population (uninitialized)
	 * @return best individual
	 */
	public Individual GA(HashMap<Integer, Individual> population,int puzzleOption){
		//		timer.run();
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		Individual bestIndividual = null;
		int bestGeneration = 0;
		Random rand = new Random();
		do {
			
			
			//original
			HashMap<Integer, Individual> newPopulation = new HashMap<Integer, Individual>();
			//elitism
//			HashMap<Integer, Individual> newPopulation = getBestTwo(population, puzzleOption);
			
			
			for(int i = newPopulation.size(); i < population.size(); i++) {
				//random selection four individuals from population
				Individual x1 = population.get(rand.nextInt(population.size()));
				x1.setFitness(fitnessFunction(puzzleOption, x1));
				Individual x2 = population.get(rand.nextInt(population.size()));
				x2.setFitness(fitnessFunction(puzzleOption, x2));
				Individual y1 = population.get(rand.nextInt(population.size()));
				y1.setFitness(fitnessFunction(puzzleOption, y1));
				Individual y2 = population.get(rand.nextInt(population.size()));
				y2.setFitness(fitnessFunction(puzzleOption, y2));

				//tournament selection
				Individual x = null;
				Individual y = null;
				if(x1.getFitness() > x2.getFitness()) {x = x1;}
				else {x = x2;}
				if(y1.getFitness() > y2.getFitness()) {y = y1;}
				else {y = y2;}

				//crossover/mutation
				if(puzzleOption==1) {
					Float[][] childData = reproductionFunction(x.getData(), y.getData(), 0.05f); //get the data of the child
					newPopulation.put(i, new BinSet(childData)); //add child to new population
				}else if(puzzleOption==2) {
					Piece[] childTower = reproductionFunction(x.getPieces(), y.getPieces(), 0.05f);	//get the pieces of the child
					newPopulation.put(i, new Tower(childTower)); //add child to new population
				}
				//set the fitness score of the new child
				newPopulation.get(i).setFitness(fitnessFunction(puzzleOption, newPopulation.get(i)));
				if(newPopulation.get(i).getFitness() > bestScore) {
					bestScore = newPopulation.get(i).getFitness();	//keep track of best child
					bestIndividual = newPopulation.get(i);
					bestGeneration = generation;
					bestKey = i;
				}
			}
			population.clear();
			population = newPopulation;
			generation++;
			elapsedTime = (new Date()).getTime() - startTime;
		} while (elapsedTime < duration);		
		System.out.println("Total Number of Generations Ran for: "+generation);
		System.out.println("Best Solution found at Generation: " + bestGeneration);
		return bestIndividual;	//return best member of new population
	}
	
	/**
	 * Sets the fitness of every member of population and returns the best
	 * 
	 * @param population hashmap of original population
	 * @return the individual with the highest fitness score
	 */
	public static HashMap<Integer, Individual> getBestTwo(HashMap<Integer, Individual> population, int puzzleOption) {
		HashMap<Integer, Individual> bestTwo = new HashMap<Integer, Individual>();
		float bestScore = 0;
		float nextBestScore = 0;
		Individual best = null;
		Individual nextBest = null;
		for(int i = 0; i < population.size(); i++) {
			population.get(i).setFitness(fitnessFunction(puzzleOption, population.get(i)));
			if(population.get(i).getFitness() > bestScore) {
				bestScore = population.get(i).getFitness();
				best = population.get(i);
			}else if(population.get(i).getFitness() > nextBestScore) {
				nextBestScore = population.get(i).getFitness();
				nextBest = population.get(i);
			}
		}
		bestTwo.put(0, best);
		bestTwo.put(1, nextBest);
		return bestTwo;
	}
	
	public static HashMap<Integer, Individual> removeWorstTwo(HashMap<Integer, Individual> population, int puzzleOption) {
		float worstScore = 0;
		float nextWorstScore = 0;
		Individual worst = null;
		Individual nextWorst = null;
		int worstKey = 0;
		int nextWorstKey = 0;
		for(int i = 0; i < population.size(); i++) {
			population.get(i).setFitness(fitnessFunction(puzzleOption, population.get(i)));
			if(population.get(i).getFitness() < worstScore) {
				worstScore = population.get(i).getFitness();
				worst = population.get(i);
				worstKey = i;
			}else if(population.get(i).getFitness() < nextWorstScore) {
				nextWorstScore = population.get(i).getFitness();
				nextWorst = population.get(i);
				nextWorstKey = i;
			}
		}
		population.remove(nextWorstKey, nextWorst);
		population.remove(worstKey, worst);
		return population;
	}
	
	public static HashMap<Integer, Individual> deepCopy(HashMap<Integer, Individual> oldPop, int puzzleOption){
		HashMap<Integer, Individual> copy = new HashMap<Integer, Individual>();
		if(puzzleOption==1) {
			for(int i = 0; i < oldPop.size(); i++) {
				Float[][] copyData = oldPop.get(i).getData();
				copy.put(i, new BinSet(copyData));
			}
		}else {
			for(int i = 0; i < oldPop.size(); i++) {
				Piece[] copyData = oldPop.get(i).getPieces();
				copy.put(i, new Tower(copyData));
			}
		}
		return copy;		
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
			if(!checkValidTower(pieces)) {
				fitnessScore = 0;
			}else {
				float totalCost = getCost(pieces);
				int height = getHeight(pieces);
				fitnessScore =  (float) (10 + Math.pow(height, 2) - totalCost);
			}
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
		int total = 0;
		for(Piece piece : pieces){
			if(piece.isIncluded()) {
				total++;
			}
		}
		return total;
	}

	/**
	 * Gets the total cost of building the tower
	 * @param tower the constructed tower
	 * @return the total cost of the tower
	 */
	public static float getCost(Piece [] pieces) {
		int cost = 0;
		for (Piece piece : pieces) {
			if(piece.isIncluded()) {
				cost += piece.getCost();
			}
		}
		return cost;
	}

	/**
	 * Checks that the tower configuration is valid
	 * @param tower a tower config
	 * @return boolean whether valid
	 */
	public static boolean checkValidTower(Piece[] pieces){
		float prevWidth = Float.MAX_VALUE;
		List<Piece> includedPieces = new ArrayList<Piece>();
		for(Piece piece : pieces) {
			if(piece.isIncluded()) {
				includedPieces.add(piece);
			}
		}

		for(int k = 0; k < includedPieces.size(); k++) {
			if(k==0 && !includedPieces.get(k).getType().equals("Door")) {	//first piece door
				return false;
			}else if(k==(includedPieces.size()-1) && !includedPieces.get(k).getType().equals("Lookout")) {	//last piece lookout
				return false;
			}else if((k!=0)&&(k!=(includedPieces.size()-1)) && (!includedPieces.get(k).getType().equals("Wall"))){	//intermediate pieces are walls
				return false;
			}else if(includedPieces.get(k).getWidth() > prevWidth) {	//piece isn't wider than last piece
				return false;
			}else if(includedPieces.get(k).getStrength() < (includedPieces.size() - k - 1)) {	//number pieces left isn't more than strength
				return false;
			}
			prevWidth = includedPieces.get(k).getWidth();
		}
		return true;	//passes all rules

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
	public static Piece[] reproductionFunction(Piece[] parent1, Piece[] parent2, float mutationPercent) {
		Piece[] child = new Piece[parent1.length];
		Random rand = new Random();

		HashMap<String, Integer> unusedValues = new HashMap<String, Integer>();

		//Map all tower values to the string
		for(int i = 0; i < child.length; i++) {
			if(unusedValues.containsKey(parent1[i].toString())) {
				int currValueCount = unusedValues.get(parent1[i].toString());
				unusedValues.remove(parent1[i].toString());
				currValueCount++;
				unusedValues.put(parent1[i].toString(), currValueCount);

			}else {
				unusedValues.put(parent1[i].toString(), 1);
			}
		}

		for(int i = 0; i < child.length; i++) {
			if(i <= child.length / 2) {
				//Check and see if we haven't used the specific value
				int currValueCount = unusedValues.get(parent1[i].toString());
				if(currValueCount != 0) {
					child[i] = parent1[i];
					currValueCount--;
					unusedValues.replace(parent1[i].toString(), currValueCount--);
				}else {
					child[i] = null; //a placeholder to make finding duplicates easier
				}

			}else if(i > child.length / 2){
				//Check and see if we haven't used the specific value
				int currValueCount = unusedValues.get(parent2[i].toString());
				if(currValueCount != 0) {
					child[i] = parent2[i];
					currValueCount--;
					unusedValues.replace(parent2[i].toString(), currValueCount);
				}else {
					child[i] = null; //a placeholder to make finding duplicates easier
				}
			}
		}

		unusedValues.values().removeAll(Collections.singleton(0)); //remove all unused elements
		List<String> keysAsArray = new ArrayList<String>(unusedValues.keySet()); //get all unused values as keys

		for(int i = 0; i < child.length; i++) {
			if(child[i] == null) {
				String randomKey = keysAsArray.get(rand.nextInt(keysAsArray.size())); //get a random key
				int currValueCount = unusedValues.get(randomKey); //get the amount of that random key
				String[] pieceStr = randomKey.split(" ");
				child[i] = new Piece(pieceStr[0], Float.parseFloat(pieceStr[1]), Float.parseFloat(pieceStr[2]), Float.parseFloat(pieceStr[3])); //set the value to that random key

				//If its the last one, remove it from the list, if not, decrement the list.
				if(currValueCount <= 1) {
					unusedValues.remove(randomKey);
					keysAsArray = new ArrayList<String>(unusedValues.keySet());
				}else {
					currValueCount--;
					unusedValues.replace(randomKey, currValueCount);
				}
			}
		}

		// Mutate the genes so they can cross
		for (int i = 0; i < mutationPercent * child.length; i++) {
			int randPiece1 = rand.nextInt(child.length);
			int randPiece2 = rand.nextInt(child.length);

			Piece temp = child[randPiece1];
			child[randPiece1] = child[randPiece2];
			child[randPiece2] = temp;

			if((int)(mutationPercent * 100 * 4) == rand.nextInt(100)) {
				int inversionPiece = rand.nextInt(child.length);
				child[inversionPiece].invertInclusion();
			}
		}	
		return child;
	}

	public static Float[][] reproductionFunction(Float[][] parent1, Float[][] parent2, float mutationPercent) {
		Float[][] child = new Float[4][10];
		Random rand = new Random();

		HashMap<String, Integer> unusedValues = new HashMap<String, Integer>();
		for (int i = 0; i <= 3; i++) {

			for (int j = 0; j <= 9; j++) {
				if(unusedValues.containsKey(parent1[i][j].toString())) {
					int currValueCount = unusedValues.get(parent1[i][j].toString());
					unusedValues.remove(parent1[i][j].toString());
					currValueCount++;
					unusedValues.put(parent1[i][j].toString(), currValueCount);

				}else {
					unusedValues.put(parent1[i][j].toString(), 1);
				}
			}
		}

		//For each row, take the first 5 from parent 1 and the second 5 from parent 2 and combine them.

		for(int i = 0; i <=3; i++) {
			for(int j = 0; j <= 9; j++) {
				if(j <= 4) {
					//Check and see if we haven't used the specific value
					int currValueCount = unusedValues.get(parent1[i][j].toString());
					if(currValueCount != 0) {
						child[i][j] = parent1[i][j];
						currValueCount--;
						unusedValues.replace(parent1[i][j].toString(), currValueCount--);
					}else {
						child[i][j] = Float.MAX_VALUE; //a placeholder to make finding duplicates easier
					}
				}else if(j > 4){
					//Check and see if we haven't used the specific value
					int currValueCount = unusedValues.get(parent2[i][j].toString());
					if(currValueCount != 0) {
						child[i][j] = parent2[i][j];
						currValueCount--;
						unusedValues.replace(parent2[i][j].toString(), currValueCount);
					}else {
						child[i][j] = Float.MAX_VALUE; //a placeholder to make finding duplicates easier
					}
				}
			}
		}

		unusedValues.values().removeAll(Collections.singleton(0)); //remove all unused elements
		List<String> keysAsArray = new ArrayList<String>(unusedValues.keySet()); //get all unused values as keys

		for(int i = 0; i <= 3; i++) {
			for(int j = 0; j <= 9; j++) {
				if(child[i][j] == Float.MAX_VALUE) {
					String randomKey = keysAsArray.get(rand.nextInt(keysAsArray.size())); //get a random key
					int currValueCount = unusedValues.get(randomKey); //get the amount of that random key
					child[i][j] = Float.parseFloat(randomKey); //set the value to that random key

					//If its the last one, remove it from the list, if not, decrement the list.
					if(currValueCount <= 1) {
						unusedValues.remove(randomKey);
						keysAsArray = new ArrayList<String>(unusedValues.keySet());
					}else {
						currValueCount--;
						unusedValues.replace(randomKey, currValueCount);
					}
				}
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

