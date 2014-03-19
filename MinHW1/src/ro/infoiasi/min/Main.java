package ro.infoiasi.min;

import ro.infoiasi.min.algorithm.GrienwangkAlgorithm;
import ro.infoiasi.min.algorithm.RastriginAlgorithm;
import ro.infoiasi.min.algorithm.RosenbrockAlgorithm;
import ro.infoiasi.min.algorithm.SixHumpCamelBackAlgorithm;
import ro.infoiasi.min.genetic.*;
import ro.infoiasi.min.hillclimbing.HillClimbingExecution;
import ro.infoiasi.min.hybrid.HybridAlgorithmExecution;

public class Main {

    public static final int SPACE_DIMENSION = 2;
    public static final int INDIVIDUAL_SIZE = 5;
    public static final int POPULATION_SIZE = 10;

    public static void main(String[] args) {
//        hillClimbingDemo();
        // geneticAlgorithmDemo();
        hybridizationDemo();
    }

    public static final void hillClimbingDemo() {
        Individual[] startingConfig = new Individual[SPACE_DIMENSION];
        for (int i = 0; i < SPACE_DIMENSION; i++) {
            Individual newIndividual = Individual.generateNewIndividual(INDIVIDUAL_SIZE);
            startingConfig[i] = newIndividual;
        }

        HillClimbingExecution exec = new HillClimbingExecution(startingConfig, new RastriginAlgorithm());
        exec.exec();
        exec.log("");
        HillClimbingExecution exec1 = new HillClimbingExecution(startingConfig, new GrienwangkAlgorithm());
        exec1.exec();
        exec.log("");
        HillClimbingExecution exec2 = new HillClimbingExecution(startingConfig, new RosenbrockAlgorithm());
        exec2.exec();
        exec.log("");
        HillClimbingExecution exec3 = new HillClimbingExecution(startingConfig, new
                SixHumpCamelBackAlgorithm());
        exec3.exec();
    }

    public static final void geneticAlgorithmDemo() {
        GeneticAlgorithmExecution exec = new GeneticAlgorithmExecution(SPACE_DIMENSION, POPULATION_SIZE, INDIVIDUAL_SIZE, 0.8, 0.2,
                new RastriginAlgorithm());
        exec.exec();
    }

    public static final void hybridizationDemo() {
        HybridAlgorithmExecution exec = new HybridAlgorithmExecution(SPACE_DIMENSION, POPULATION_SIZE, INDIVIDUAL_SIZE, 0.8, 0.2,
                new RastriginAlgorithm());
        exec.exec();
    }

}
