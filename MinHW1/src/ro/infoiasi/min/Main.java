package ro.infoiasi.min;

import ro.infoiasi.min.genetic.GeneticAlgorithmExecution;
import ro.infoiasi.min.genetic.RastriginAlgorithm;
import ro.infoiasi.min.hillclimbing.HillClimbingExecution;

public class Main {

	public static final int SPACE_DIMENSION = 2;

	public static void main(String[] args) {
		// hillClimbingDemo();
		geneticAlgorithmDemo();
	}

	public static final void hillClimbingDemo() {
		HillClimbingExecution exec = new HillClimbingExecution(SPACE_DIMENSION, 3, 10, new RastriginAlgorithm());
		exec.exec();
		exec.log("");
		// HillClimbingExecution exec1 = new HillClimbingExecution(SPACE_DIMENSION, 3, 5, new GrienwangkAlgorithm());
		// exec1.exec();
		// exec.log("");
		// HillClimbingExecution exec2 = new HillClimbingExecution(SPACE_DIMENSION, 3, 5, new RosenbrockAlgorithm());
		// exec2.exec();
		// exec.log("");
		// HillClimbingExecution exec3 = new HillClimbingExecution(SPACE_DIMENSION, 3, 5, new
		// SixHumpCamelBackAlgorithm());
		// exec3.exec();
	}

	public static final void geneticAlgorithmDemo() {
		GeneticAlgorithmExecution exec = new GeneticAlgorithmExecution(SPACE_DIMENSION, 5, 5, 0.8, 0.2,
				new RastriginAlgorithm());
		exec.exec();
	}

}
