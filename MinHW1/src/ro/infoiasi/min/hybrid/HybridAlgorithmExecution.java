package ro.infoiasi.min.hybrid;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;
import ro.infoiasi.min.genetic.GeneticAlgorithmExecution;
import ro.infoiasi.min.hillclimbing.HillClimbingExecution;

public class HybridAlgorithmExecution extends GeneticAlgorithmExecution {

    public HybridAlgorithmExecution(int spaceSize, int populationCount, int invididualSize, double crossOverRate, double mutationRate, FitnessAlgorithm fitnessAlgorithm) {
        super(spaceSize, populationCount, invididualSize, crossOverRate, mutationRate, fitnessAlgorithm);
    }

    @Override
    protected Double[] evaluate(Individual[][] population) {
        Double[] output = null;
        if (getFitnessAlgorithm() != null && population != null) {
            output = new Double[population.length];
            for (int i = 0; i < population.length; i++) {
                HillClimbingExecution exec = new HillClimbingExecution(population[i], getFitnessAlgorithm());
                output[i] = exec.exec();
            }
        }
        return output;
    }
}
