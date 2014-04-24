package ro.infoiasi.min.hybrid;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;
import ro.infoiasi.min.genetic.GeneticAlgorithmExecution;
import ro.infoiasi.min.hillclimbing.HillClimbingExecution;

import java.util.Random;

public class HybridAlgorithmExecution extends GeneticAlgorithmExecution {

    private double hybridFactor = 0.3;
    public HybridAlgorithmExecution(int spaceSize, int populationCount, int invididualSize, double crossOverRate, double mutationRate, FitnessAlgorithm fitnessAlgorithm, double hybridFactor) {
        super(spaceSize, populationCount, invididualSize, crossOverRate, mutationRate, fitnessAlgorithm);
        this.hybridFactor = hybridFactor;
    }

    @Override
    protected Double[] evaluate(Individual[][] population) {
        Double[] output = null;

        Random random = new Random();
        if (getFitnessAlgorithm() != null && population != null) {
            output = new Double[population.length];
            for (int i = 0; i < population.length; i++) {
                if (random.nextDouble() > hybridFactor) {
                    HillClimbingExecution exec = new HillClimbingExecution(population[i], getFitnessAlgorithm());
                    double returnedValue = 1 / (exec.exec() + getFitnessAlgorithm().EPSILON);
                    fitnessEvaluationCount += exec.getFitnessEvaluationCount();
                    output[i] = returnedValue;
                } else {
                    output[i] = getFitnessAlgorithm().evaluateFitness(population[i]);
                    fitnessEvaluationCount++;
                }
            }
        }
        return output;
    }
}
