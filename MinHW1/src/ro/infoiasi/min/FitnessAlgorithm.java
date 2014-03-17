package ro.infoiasi.min;

public abstract class FitnessAlgorithm {

	public static final double EPSILON = 0.0001;

	public abstract double evaluateFunction(Individual[] solution);

	public double evaluateFitness(Individual[] sol) {
		double functionValue = evaluateFunction(sol);
		functionValue += EPSILON;
		return 1 / functionValue;
	}

	protected void log(Object obj) {
		if (obj != null) {
			if (obj instanceof Individual[]) {
				Individual.toString((Individual[]) obj);
			} else if (obj instanceof Individual[][]) {
				Individual.toString((Individual[][]) obj);
			} else {
				System.out.println(obj.toString());
			}
		} else {
			System.out.println("null");
		}
	}

	public double mapIndividualToReal(double a, double b, Individual individual) {
		double output = a + (b - a) * individual.getDecimalValue() / (Math.pow(2, individual.getInitialBitCount()) - 1);
		return output;
	}

	public double evaluateFitness(double[] individual) {
		return 0;
	}

}
