package ro.infoiasi.min.genetic;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class RastriginAlgorithm extends FitnessAlgorithm {

	private static final double a = -5.12;
	private static final double b = +5.12;

	@Override
	public double evaluateFunction(Individual[] individual) {
		double output = 10 * individual.length;
		for (int i = 0; i < individual.length; i++) {
			double realVal = mapIndividualToReal(a, b, individual[i]);
			output += realVal * realVal - 10 * Math.cos(2 * Math.PI * realVal);
		}
		return output;
	}

	public double evaluateFitness(double[] individual) {
		double output = 10 * individual.length;
		for (int i = 0; i < individual.length; i++) {
			output += individual[i] * individual[i] - 10 * Math.cos(2 * Math.PI * individual[i]);
		}
		if (output == 0) {
			output = EPSILON;
		}
		return output;
	}

}
