package ro.infoiasi.min.hillclimbing;

import ro.infoiasi.min.AbstractExecution;
import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class HillClimbingExecution extends AbstractExecution {
    private FitnessAlgorithm fitnessAlgorithm;
    private Individual[] startingConfig;
    private int fitnessEvaluationCount = 0;
    public HillClimbingExecution(Individual[] startingConfig,
                                 FitnessAlgorithm fitnessAlgorithm) {
        super();
        this.startingConfig = startingConfig;
        this.fitnessAlgorithm = fitnessAlgorithm;
    }

    public Double exec() {
        Double output = null;

        Individual[] currentConfig = startingConfig;
        double currentConfigEval = fitnessAlgorithm.evaluateFunction(currentConfig);
        fitnessEvaluationCount = 1;
        while (true) {
            Individual[][] neighbors = computeNeighbors(currentConfig);
            Individual[] nextConfig = null;
            double nextEval = Double.MAX_VALUE;
            for (Individual[] config : neighbors) {
                double possibleNextEval = fitnessAlgorithm.evaluateFunction(config);
                if (possibleNextEval < nextEval) {
                    nextConfig = config;
                    nextEval = possibleNextEval;
                }
            }

            if (nextEval >= currentConfigEval) {
                output = currentConfigEval;
                break;
            }
            currentConfig = nextConfig;
            currentConfigEval = fitnessAlgorithm.evaluateFunction(currentConfig);
            fitnessEvaluationCount++;
        }

        return output;
    }

    private Individual[][] computeNeighbors(Individual[] config) {
        Individual[][] output = new Individual[config[0].getInitialBitCount() * config.length][config.length];
        int it = 0;
        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config[i].getInitialBitCount(); j++) {
                Individual[] neighbor = new Individual[config.length];

                for (int r = 0; r < config.length; r++) {
                    neighbor[r] = Individual.clone(config[r]);
                }

                Individual swapped = Individual.clone(config[i]);
                swapped.flip(j);
                neighbor[i] = swapped;
                output[it] = neighbor;
                it++;
            }
        }
        return output;
    }

    @Override
    public int getFitnessEvaluationCount() {
        return fitnessEvaluationCount;
    }
}
