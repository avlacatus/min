package ro.infoiasi.min;

import ro.infoiasi.min.algorithm.GrienwangkAlgorithm;
import ro.infoiasi.min.algorithm.RastriginAlgorithm;
import ro.infoiasi.min.algorithm.RosenbrockAlgorithm;
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
        hillClimbingDemo(new RastriginAlgorithm());
        hillClimbingDemo(new GrienwangkAlgorithm());
        hillClimbingDemo(new RosenbrockAlgorithm());
        hillClimbingDemo(new SixHumpCamelBackAlgorithm());

        geneticAlgorithmDemo(new RastriginAlgorithm());
        geneticAlgorithmDemo(new GrienwangkAlgorithm());
        geneticAlgorithmDemo(new RosenbrockAlgorithm());
        geneticAlgorithmDemo(new SixHumpCamelBackAlgorithm());

        hybridAlgoritmDemo(new RastriginAlgorithm());
        hybridAlgoritmDemo(new GrienwangkAlgorithm());
        hybridAlgoritmDemo(new RosenbrockAlgorithm());
        hybridAlgoritmDemo(new SixHumpCamelBackAlgorithm());
    }

    public static void hillClimbingDemo(FitnessAlgorithm alg) {
        if (alg instanceof SixHumpCamelBackAlgorithm) {
            hillClimbingDemo(2, alg);
            AbstractExecution.log("************************************************");
        } else {
            hillClimbingDemo(5, alg);
            AbstractExecution.log("************************************************");
            hillClimbingDemo(10, alg);
            AbstractExecution.log("************************************************");
            hillClimbingDemo(30, alg);
        }
    }

    public static void geneticAlgorithmDemo(FitnessAlgorithm alg) {
        if (alg instanceof SixHumpCamelBackAlgorithm) {
            geneticAlgorithmDemo(2, alg);
            AbstractExecution.log("************************************************");
        } else {
            geneticAlgorithmDemo(5, alg);
            AbstractExecution.log("************************************************");
            geneticAlgorithmDemo(10, alg);
            AbstractExecution.log("************************************************");
            geneticAlgorithmDemo(30, alg);
        }
    }

    public static void hybridAlgoritmDemo(FitnessAlgorithm algo) {
        if (algo instanceof SixHumpCamelBackAlgorithm) {
            hybridAlgorithmDemo(2, algo);
            AbstractExecution.log("************************************************");
        } else {
            hybridAlgorithmDemo(5, algo);
            AbstractExecution.log("************************************************");
            hybridAlgorithmDemo(10, algo);
            AbstractExecution.log("************************************************");
            hybridAlgorithmDemo(30, algo);
        }
    }

    public static void geneticAlgorithmDemo(int spaceDimension, FitnessAlgorithm algorithm) {
        AbstractExecution.log("GENETIC ALGORITHM SPACE_DIMENSION=" + spaceDimension + " " + algorithm.getClass().getSimpleName());
        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;
        Double avg = 0d;

        for (int it = 0; it < ITERATION_COUNT; it++) {
            GeneticAlgorithmExecution exec = new GeneticAlgorithmExecution(spaceDimension, POPULATION_SIZE, INDIVIDUAL_SIZE, 0.8, 0.3,
                    algorithm);
            Double iterationResult = exec.exec();
            AbstractExecution.log(exec.getFitnessEvaluationCount() + ", " + iterationResult);
            if (iterationResult < min) {
                min = new Double(iterationResult);
            } else if (max < iterationResult) {
                max = new Double(iterationResult);
            }
            avg += new Double(iterationResult);
        }
        avg = avg / ITERATION_COUNT;
        AbstractExecution.log("");
        AbstractExecution.log("Average, " + avg.toString());
        AbstractExecution.log("Minimum, " + min.toString());
        AbstractExecution.log("Maximum, " + max.toString() + "\n");

    }

    public static void hybridAlgorithmDemo(int spaceDimension, FitnessAlgorithm algorithm) {
        AbstractExecution.log("HYBRID ALGORITHM SPACE_DIMENSION=" + spaceDimension + " " + algorithm.getClass().getSimpleName());
        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;
        Double avg = 0d;

        for (int it = 0; it < ITERATION_COUNT; it++) {

            GeneticAlgorithmExecution exec = new HybridAlgorithmExecution(spaceDimension, POPULATION_SIZE, INDIVIDUAL_SIZE, 0.8, 0.2,
                    algorithm, 0.3);
            Double iterationResult = exec.exec();
            AbstractExecution.log(exec.getFitnessEvaluationCount() + ", " + iterationResult);
            if (iterationResult < min) {
                min = new Double(iterationResult);
            } else if (max < iterationResult) {
                max = new Double(iterationResult);
            }
            avg += new Double(iterationResult);
        }
        avg = avg / ITERATION_COUNT;
        AbstractExecution.log("");
        AbstractExecution.log("Average, " + avg.toString());
        AbstractExecution.log("Minimum, " + min.toString());
        AbstractExecution.log("Maximum, " + max.toString() + "\n");

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
            AbstractExecution.log(exec.getFitnessEvaluationCount() + ", " + iterationResult);
            if (iterationResult < min) {
                min = new Double(iterationResult);
            } else if (max < iterationResult) {
                max = new Double(iterationResult);
            }
            avg += new Double(iterationResult);
        }
        avg = avg / ITERATION_COUNT;
        AbstractExecution.log("");
        AbstractExecution.log("Average, " + avg.toString());
        AbstractExecution.log("Minimum, " + min.toString());
        AbstractExecution.log("Maximum, " + max.toString() + "\n");
    }


    public static void hybridization() {
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
                new RastriginAlgorithm(), 0.3);
        exec2.exec(population);
    }

}
