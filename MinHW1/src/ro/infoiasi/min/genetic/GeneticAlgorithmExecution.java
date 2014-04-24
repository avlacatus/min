package ro.infoiasi.min.genetic;

import ro.infoiasi.min.AbstractExecution;
import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

import java.util.*;

public class GeneticAlgorithmExecution extends AbstractExecution {

    private int individualSize;
    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int spaceSize;
    private Individual[][] population;
    private Double[] fitnesses;
    private FitnessAlgorithm fitnessAlgorithm;
    private Individual[] eliteIndividual = null;

    protected int fitnessEvaluationCount = 0;

    private static final double MAX_FITNESS = 2000;
    private static final int MAX_ITERATIONS = 100;

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

    public Double exec() {
        init();
        return exec(population);
    }

    public Double exec(Individual[][] population) {
        this.population = Individual.clone(population);
        fitnesses = evaluate(population);
        Double[] fitnessesk1 = fitnesses;
        Individual[][] populationk1 = population;
        int k = 0;
        Double output;
        do {
            populationk1 = rouletteWheelSelection(populationk1, fitnessesk1);
            populationk1 = mutate(populationk1, mutationRate);
            populationk1 = crossover(populationk1, crossoverRate);
            fitnessesk1 = evaluate(populationk1);
            k++;
            output = getIterationWinner(fitnessesk1);
            saveEliteIndividual(fitnessesk1, populationk1);
        } while (output < MAX_FITNESS && k < MAX_ITERATIONS);

        return output;
    }

    private void saveEliteIndividual(Double[] fitnesses, Individual[][] population) {
        double max = Collections.max(Arrays.asList(fitnesses));
        int maxIndex = -1;
        for (int i = 0; i < fitnesses.length; i++) {
            if (fitnesses[i].equals(max)) {
                maxIndex = i;
                break;
            }
        }
        eliteIndividual = population[maxIndex];
    }

    private Double getIterationWinner(Double[] fitnesses) {
        Double currentMax = fitnesses[0];
        for (int i = 0; i < fitnesses.length; i++) {
            if (currentMax < fitnesses[i]) {
                currentMax = fitnesses[i];
            }
        }
        double value = currentMax;
        value = 1 / value - 0.0001;
        return value;
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

        if (eliteIndividual != null) {
            boolean found = false;
            for (Individual[] config : output) {
                if (config.equals(eliteIndividual)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                output[output.length-1] = eliteIndividual;
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
                int selectedGene = random.nextInt(selectedCrom[0].getInitialBitCount());
                int selectedIndex = random.nextInt(selectedCrom.length);
                Individual selectedIndiv = selectedCrom[selectedIndex];
                selectedIndiv.flip(selectedGene);
                selectedCrom[selectedIndex] = selectedIndiv;
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

            Individual[] crossoverResult = performCrossover(population[index1], population[index2]);
            output[index1] = Individual.clone(crossoverResult);
            output[index2] = Individual.clone(crossoverResult);
        }
        return output;
    }

    private Individual[] performCrossover(Individual[] ind1, Individual[] ind2) {
        Individual[] output = new Individual[ind1.length];
        Random random = new Random();
        int selectedIndividualIndex = random.nextInt(ind1.length);
        int selectedGeneIndex = random.nextInt(ind1[0].getInitialBitCount());
        boolean firstShouldBegin = random.nextBoolean();
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
        return output;
    }

    private void init() {
        population = new Individual[populationSize][spaceSize];
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < spaceSize; j++) {
                Individual newIndividual = Individual.generateNewIndividual(individualSize);
                population[i][j] = newIndividual;
            }
        }
    }

    protected Double[] evaluate(Individual[][] population) {
        Double[] output = new Double[1];
        if (fitnessAlgorithm != null && population != null) {
            output = new Double[population.length];
            for (int i = 0; i < population.length; i++) {
                output[i] = fitnessAlgorithm.evaluateFitness(population[i]);
                fitnessEvaluationCount++;
            }
        }
        return output;
    }

    public FitnessAlgorithm getFitnessAlgorithm() {
        return fitnessAlgorithm;
    }

    @Override
    public int getFitnessEvaluationCount() {
        return fitnessEvaluationCount;
    }
}
