package ro.infoiasi.min.hillclimbing;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class HillClimbingExecution {
    private int individualSize;
    private int populationSize;
    private int spaceSize;
    private Individual[][] population;
    private FitnessAlgorithm fitnessAlgorithm;

    public HillClimbingExecution(int spaceSize, int populationSize, int invididualSize,
            FitnessAlgorithm fitnessAlgorithm) {
        super();
        this.spaceSize = spaceSize;
        this.populationSize = populationSize;
        this.individualSize = invididualSize;
        this.fitnessAlgorithm = fitnessAlgorithm;
    }

    public void exec() {
        long intialTime = System.currentTimeMillis();
        log("Scheme: " + fitnessAlgorithm.getClass().getSimpleName());
        init();
        log(population);
        int startNode = (int) (Math.random() * populationSize);
        log("Starting node is: " + startNode);
        Individual[] curentConfig = population[startNode];
        log("Starting config is: ");
        log(curentConfig);
        for (Individual i : curentConfig) {
            System.out.print(i.getDecimalValue() + " ");
        }
        log("");
        double[] doubleValues = new double[curentConfig.length];
        for (int i = 0; i < curentConfig.length; i++) {
            double realVal = fitnessAlgorithm.mapIndividualToReal(-5.12, 5.12, curentConfig[i]);
            System.out.print(realVal + " ");
            doubleValues[i] = realVal;
        }
        log("");
        log("initial fitness: " + fitnessAlgorithm.evaluateFitness(doubleValues));
        log("initial fitness: " + fitnessAlgorithm.evaluateFunction(curentConfig));
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
                log("Result is: ");
                log(curentConfig);
                log(fitnessAlgorithm.evaluateFunction(curentConfig));
                break;
            }
            curentConfig = nextConfig;

        }

        log("Time duration: " + (System.currentTimeMillis() - intialTime) + " millis");

    }

    private final void init() {
        population = new Individual[populationSize][spaceSize];
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < spaceSize; j++) {
                Individual newIndividual = Individual.generateNewIndividual(individualSize);
                population[i][j] = newIndividual;
            }
        }
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

    public void log(Object obj) {
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
