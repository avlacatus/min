package ro.infoiasi.min.hillclimbing;

import ro.infoiasi.min.AbstractExecution;
import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class HillClimbingExecution extends AbstractExecution {
    private FitnessAlgorithm fitnessAlgorithm;
    private Individual[] startingConfig;

    public HillClimbingExecution(Individual[] startingConfig,
                                 FitnessAlgorithm fitnessAlgorithm) {
        super();
        this.startingConfig = startingConfig;
        this.fitnessAlgorithm = fitnessAlgorithm;
    }

    public Double exec() {
        Double output = null;

        Individual[] curentConfig = startingConfig;
        int iterationCount = 0;
        while (true) {
            Individual[][] neighbors = computeNeighbors(curentConfig);
            Individual[] nextConfig = null;
            double nextEval = Double.MAX_VALUE;
            for (Individual[] config : neighbors) {
                if (fitnessAlgorithm.evaluateFunction(config) < nextEval) {
                    nextConfig = config;
                    nextEval = fitnessAlgorithm.evaluateFunction(config);
                }
            }
            log("iteration " + iterationCount++ + " " + nextEval);
            if (nextEval >= fitnessAlgorithm.evaluateFunction(curentConfig)) {
                output = fitnessAlgorithm.evaluateFunction(curentConfig);
                log("Result is: ");
                log(curentConfig);
                log(output);
                break;
            }
            curentConfig = nextConfig;
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
}
