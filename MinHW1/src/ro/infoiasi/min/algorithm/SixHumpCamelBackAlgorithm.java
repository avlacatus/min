package ro.infoiasi.min.algorithm;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class SixHumpCamelBackAlgorithm extends FitnessAlgorithm {

    private static final double a1 = -3;
    private static final double b1 = +3;
    private static final double a2 = -2;
    private static final double b2 = +2;
    private static final double EPSILON = 0.0001;

    @Override
    public double evaluateFunction(Individual[] individual) {
        if (individual.length != 2) {
            throw new IllegalArgumentException("Six-hump camel back function can be performed only with 2 arguments");
        }

        double output = 0;
        double realVal1 = mapIndividualToReal(a1, b1, individual[0]);
        double realVal2 = mapIndividualToReal(a2, b2, individual[1]);

        output = 4 - 2.1 * Math.pow(realVal1, 2) + pow13(realVal1);
        output *= Math.pow(realVal1, 2);

        output += realVal1 * realVal2;
        output += (-4 + 4 * Math.pow(realVal2, 2)) * Math.pow(realVal2, 2);

        if (output == 0) {
            output = EPSILON;
        }
        return 1 / output;
    }
    
    private double pow13(double input) {
    	double output = Math.pow(input, 4);
    	return Math.cbrt(output);
    }

}
