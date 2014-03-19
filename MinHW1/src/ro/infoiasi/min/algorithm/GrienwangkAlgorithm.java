package ro.infoiasi.min.algorithm;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class GrienwangkAlgorithm extends FitnessAlgorithm {

    private static final double a = -600;
    private static final double b = +600;
    private static final double EPSILON = 0.0001;

    @Override
    public double evaluateFunction(Individual[] config) {
        double output = 0;
        for (Individual individual : config) {
            output += Math.pow(mapIndividualToReal(a, b, individual), 2);
        }
        output /= 4000;

        double aux = 1;
        for (int i = 0; i < config.length; i++) {
            aux *= Math.cos(mapIndividualToReal(aux, b, config[i]) / Math.sqrt(i+1));
        }

        output = output - aux + 1;

        if (output == 0) {
            output = EPSILON;
        }
        return output;
    }

}
