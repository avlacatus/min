package ro.infoiasi.min.genetic;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class RosenbrockAlgorithm extends FitnessAlgorithm {

    private static final double a = -2.048;
    private static final double b = +2.048;
    private static final double EPSILON = 0.0001;

    @Override
    public double evaluateFunction(Individual[] individual) {
        double output = 0;

        for (int i = 0; i < individual.length - 1; i++) {
            double realVal = mapIndividualToReal(a, b, individual[i]);
            double realVal1 = mapIndividualToReal(a, b, individual[i + 1]);

            double aux1 = 100 * Math.pow(realVal1 - Math.pow(realVal, 2), 2);
            double aux2 = Math.pow(1 - realVal, 2);
            output += aux1 + aux2;
        }

        if (output == 0) {
            output = EPSILON;
        }
        return output;
    }

}
