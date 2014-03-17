package ro.infoiasi.min.genetic;

import ro.infoiasi.min.FitnessAlgorithm;
import ro.infoiasi.min.Individual;

public class GAAlgorithmImpl extends FitnessAlgorithm {

    @Override
    public double evaluateFunction(Individual[] individual) {
        return ((double) individual[0].cardinality()) / individual[0].size();
    }

}
