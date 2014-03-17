package ro.infoiasi.min.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class GeneticAlgorithmExecution {

	private int individualSize;
	private int populationSize;
	private double crossoverRate;
	private double mutationRate;
	private int spaceSize;
	private Individual[][] population;
	private Double[] fitnesses;
	private FitnessAlgorithm fitnessAlgorithm;

	private static final double MAX_FITNESS = 100;
	private static final int MAX_ITERATIONS = 200;

	public GeneticAlgorithmExecution(int spaceSize, int populationCount, int invididualSize, double crossOverRate,
			double mutationRate, FitnessAlgorithm fitnessAlgorithm) {
		super();
		this.spaceSize = spaceSize;
		this.individualSize = invididualSize;
		this.populationSize = populationCount;
		this.crossoverRate = crossOverRate;
		this.mutationRate = mutationRate;
		this.fitnessAlgorithm = fitnessAlgorithm;
	}

	public void exec() {
		init();
		fitnesses = evaluate(population);
		Double[] fitnessesk1 = fitnesses;
		Individual[][] populationk1 = population;
		int k = 0;
		do {
			log("Iteration " + k);
			populationk1 = rouletteWheelSelection(populationk1, fitnessesk1);
			fitnessesk1 = evaluate(populationk1);
			System.out.print("Fitnesses after selection: \n");
			for (int i = 0; i < fitnessesk1.length; i++) {
				System.out.print(fitnessesk1[i] + " \n");
			}
			populationk1 = mutate(populationk1, mutationRate);
			populationk1 = crossover(populationk1, crossoverRate);
			fitnessesk1 = evaluate(populationk1);
			// for (int i = 0; i < populationk1.length; i++) {
			// log(Individual.toString(populationk1[i]) + " " + fitnessesk1[i]);
			// }
			k++;
			System.out.print("Fitnesses after crossover & mutation: \n");
			for (int i = 0; i < fitnessesk1.length; i++) {
				System.out.print(fitnessesk1[i] + " \n");
			}
			printIterationWinner(fitnessesk1, populationk1);
			log("**************************************");
		} while (Collections.max(Arrays.asList(fitnessesk1)) < MAX_FITNESS && k < MAX_ITERATIONS);
	}

	private void printIterationWinner(Double[] fitnesses, Individual[][] population) {
		double max = Collections.max(Arrays.asList(fitnesses));
		int maxIndex = -1;
		for (int i = 0; i < fitnesses.length; i++) {
			if (fitnesses[i].equals(max)) {
				maxIndex = i;
				break;
			}
		}
		double value = fitnesses[maxIndex];
		value = 1 / value - 0.0001;
		log("Winner is index " + maxIndex + " " + value + " " + fitnesses[maxIndex]);
		log(population[maxIndex]);
	}

	private final Individual[][] rouletteWheelSelection(Individual[][] population, Double[] fitnesses) {
		Individual[][] output = new Individual[population.length][population[0].length];
		double fitnessesSum = 0;
		for (Double fit : fitnesses) {
			fitnessesSum += fit;
		}
		Double[] q = new Double[fitnesses.length];
		for (int i = 0; i < fitnesses.length; i++) {
			q[i] = Double.valueOf(0);
			if (i != 0) {
				q[i] += q[i - 1];
			}
			q[i] += fitnesses[i] / fitnessesSum;
		}
		Random random = new Random();
		for (int i = 0; i < output.length; i++) {
			double r = random.nextDouble();
			for (int j = 0; j < q.length; j++) {
				if (r < q[j]) {
					output[i] = population[j];
					break;
				}
			}
		}
		return output;
	}

	private final Individual[][] mutate(Individual[][] population, double mutationRate) {
		Individual[][] output = new Individual[population.length][population[0].length];
		Random random = new Random();

		for (int i = 0; i < population.length; i++) {
			Individual[] selectedCrom = Individual.clone(population[i]);
			double r = random.nextDouble();
			if (r < mutationRate) {
				// log("mutating index " + i);
				// log(selectedCrom);
				int selectedGene = random.nextInt(selectedCrom[0].getInitialBitCount());
				int selectedIndex = random.nextInt(selectedCrom.length);
				Individual selectedIndiv = selectedCrom[selectedIndex];
				selectedIndiv.flip(selectedGene);
				selectedCrom[selectedIndex] = selectedIndiv;
				// log(selectedCrom);
				// log("-----------");
			}
			output[i] = selectedCrom;
		}
		return output;
	}

	private final Individual[][] crossover(Individual[][] population, double crossoverRate) {
		Individual[][] output = new Individual[population.length][population[0].length];
		Random random = new Random();
		List<Integer> selectedIndexes = new ArrayList<>();

		for (int i = 0; i < population.length; i++) {
			double r = random.nextDouble();
			if (r < crossoverRate) {
				selectedIndexes.add(i);
			}
		}

		for (int i = 0; i < population.length; i++) {
			output[i] = Individual.clone(population[i]);
		}

		while (selectedIndexes.size() > 1) {
			int randomInt = random.nextInt(selectedIndexes.size());
			int index1 = selectedIndexes.get(randomInt);
			selectedIndexes.remove(Integer.valueOf(index1));

			randomInt = random.nextInt(selectedIndexes.size());
			int index2 = selectedIndexes.get(randomInt);
			selectedIndexes.remove(Integer.valueOf(index2));
			// log("Performing crossover between indexes: " + index1 + " " + index2);

			Individual[] crossoverResult = performCrossover(population[index1], population[index2]);
			output[index1] = Individual.clone(crossoverResult);
			output[index2] = Individual.clone(crossoverResult);
			// log("-----------");
		}
		return output;
	}

	private Individual[] performCrossover(Individual[] ind1, Individual[] ind2) {
		Individual[] output = new Individual[ind1.length];
		Random random = new Random();
		int selectedIndividualIndex = random.nextInt(ind1.length);
		int selectedGeneIndex = random.nextInt(ind1[0].getInitialBitCount());
		boolean firstShouldBegin = random.nextBoolean();
		// log("Performing crossover between: ");
		// log(Individual.toString(ind1) + " " + Individual.toString(ind2));
		// log("Selected indexes: " + selectedIndividualIndex + " " + selectedGeneIndex + " " + firstShouldBegin);

		Individual[] first, second;
		if (firstShouldBegin) {
			first = ind1;
			second = ind2;
		} else {
			first = ind2;
			second = ind1;
		}

		for (int i = 0; i < ind1.length; i++) {
			if (i < selectedIndividualIndex) {
				output[i] = Individual.clone(first[i]);
			} else if (i > selectedIndividualIndex) {
				output[i] = Individual.clone(second[i]);
			} else {
				Individual middleIndividual = Individual.generateNewIndividual(ind1[0].getInitialBitCount());
				for (int j = 0; j < ind1[0].getInitialBitCount(); j++) {
					if (j < selectedGeneIndex) {
						middleIndividual.set(j, first[i].get(j));
					} else {
						middleIndividual.set(j, second[i].get(j));
					}
				}
				output[i] = middleIndividual;
			}
		}
		// log("Crossover result: ");
		// log(Individual.toString(output));

		return output;
	}

	private final void init() {
		population = new Individual[populationSize][spaceSize];
		for (int i = 0; i < populationSize; i++) {
			for (int j = 0; j < spaceSize; j++) {
				Individual newIndividual = Individual.generateNewIndividual(individualSize);
				population[i][j] = newIndividual;
			}
			log(population[i]);
		}
	}

	private final Double[] evaluate(Individual[][] population) {
		Double[] output = new Double[1];
		if (fitnessAlgorithm != null && population != null) {
			output = new Double[population.length];
			for (int i = 0; i < population.length; i++) {
				output[i] = fitnessAlgorithm.evaluateFitness(population[i]);
			}
		}
		return output;
	}

	private void log(Object obj) {
		if (obj != null) {
			if (obj instanceof Individual[]) {
				log(Individual.toString((Individual[]) obj));
			} else if (obj instanceof Individual[][]) {
				log(Individual.toString((Individual[][]) obj));
			} else {
				System.out.println(obj.toString());
			}
		} else {
			System.out.println("null");
		}
	}

}
