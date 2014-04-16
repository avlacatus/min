package ro.infoiasi.min;

import ro.infoiasi.min.algorithm.RastriginAlgorithm;
import ro.infoiasi.min.algorithm.SixHumpCamelBackAlgorithm;
import ro.infoiasi.min.genetic.GeneticAlgorithmExecution;
import ro.infoiasi.min.hillclimbing.HillClimbingExecution;
import ro.infoiasi.min.hybrid.HybridAlgorithmExecution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static final int SPACE_DIMENSION = 10;
    public static final int INDIVIDUAL_SIZE = 5;
    public static final int POPULATION_SIZE = 10;

    public static final int ITERATION_COUNT = 30;

    public static void main(String[] args) {
        hillClimbingDemo(2, new SixHumpCamelBackAlgorithm());
        // geneticAlgorithmDemo();
        // hybridizationDemo();
    }

    public static void hillClimbingDemo(int spaceDimension, FitnessAlgorithm algorithm) {
        AbstractExecution.log("HILL CLIMBING SPACE_DIMENSION=" + spaceDimension + " " + algorithm.getClass().getSimpleName());
        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;
        Double avg = 0d;

        for (int it = 0; it < ITERATION_COUNT; it++) {
            Individual[] startingConfig = new Individual[spaceDimension];
            for (int i = 0; i < spaceDimension; i++) {
                Individual newIndividual = Individual.generateNewIndividual(INDIVIDUAL_SIZE);
                startingConfig[i] = newIndividual;
            }
            HillClimbingExecution exec = new HillClimbingExecution(startingConfig, algorithm);
            Double iterationResult = exec.exec();
            AbstractExecution.log(it + ", " + exec.getFitnessEvaluationCount() + ", " + iterationResult);
            if (iterationResult < min) {
                min = new Double(iterationResult);
            } else if (max < iterationResult) {
                max = new Double(iterationResult);
            }
            avg += new Double(iterationResult);
        }
        avg = avg / ITERATION_COUNT;
        AbstractExecution.log("Average is: " + avg.toString());
        AbstractExecution.log("Minimum is: " + min.toString());
        AbstractExecution.log("Maximum is: " + max.toString() + "\n");
    }

    public static void geneticAlgorithmDemo() {
        GeneticAlgorithmExecution exec = new GeneticAlgorithmExecution(SPACE_DIMENSION, POPULATION_SIZE, INDIVIDUAL_SIZE, 0.8, 0.2,
                new RastriginAlgorithm());
        exec.exec();
    }

    public static void hybridizationDemo() {
        Individual[][] population = new Individual[POPULATION_SIZE][SPACE_DIMENSION];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            for (int j = 0; j < SPACE_DIMENSION; j++) {
                Individual newIndividual = Individual.generateNewIndividual(INDIVIDUAL_SIZE);
                population[i][j] = newIndividual;
            }
        }

        GeneticAlgorithmExecution exec1 = new GeneticAlgorithmExecution(SPACE_DIMENSION, POPULATION_SIZE, INDIVIDUAL_SIZE, 0.8, 0.2,
                new RastriginAlgorithm());
        exec1.exec(population);
        exec1.log("****************************************************************************************");
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
        }
        HybridAlgorithmExecution exec2 = new HybridAlgorithmExecution(SPACE_DIMENSION, POPULATION_SIZE, INDIVIDUAL_SIZE, 0.8, 0.2,
                new RastriginAlgorithm());
        exec2.exec(population);
    }

}
