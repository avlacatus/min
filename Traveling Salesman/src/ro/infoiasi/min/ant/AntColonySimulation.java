package ro.infoiasi.min.ant;

import ro.infoiasi.min.graph.Edge;
import ro.infoiasi.min.graph.Graph;
import ro.infoiasi.min.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Cosmin on 04/05/14.
 */
public class AntColonySimulation {

    private Graph graph;
    private List<ArtificialAnt> ants;
    public static int ANT_COUNT = 8;
    public static int ITERATION_COUNT = 100;
    public static final double PHEROMONE_CONTROL_PARAM = 0.6;
    public static final double DESIRABILITY_CONTROL_PARAM = 0.3;
    public static final double PHEROMONE_EVAPORATION_PARAM = 0.01;
    public static final double LOCAL_TRAIL_PARAMETER = 0.0005;

    public AntColonySimulation(Graph graph) {
        this.graph = graph;
    }

    public double simulate(boolean verbose) {
        init();

        double min = Double.MAX_VALUE;

        for (int i = 0; i < ITERATION_COUNT; i++) {
            generateAntSolutions();
            /**
             * updating global trails
             */
            ArtificialAnt bestAnt = getBestPathAnt(ants);
            if (verbose) {
                System.out.println(getSystemState(i));
                System.out.println("Best ant: " + bestAnt);
            }

            for (int j = 0; j < bestAnt.getVisitedNodes().size() - 1; j++) {
                Edge selectedEdge = graph.getEdgeBetweenNodes(bestAnt.getVisitedNodes().get(j), bestAnt.getVisitedNodes().get(j + 1));
                if (selectedEdge != null) {
                    double newPheromoneLevel = (1 - PHEROMONE_EVAPORATION_PARAM) * selectedEdge.getPheromoneLevel() + PHEROMONE_EVAPORATION_PARAM * (1 / bestAnt.getCurrentCost());
                    selectedEdge.setPheromoneLevel(newPheromoneLevel);
                }
            }
            if (verbose) {
                System.out.println("===================");
            }

            if (bestAnt.getCurrentCost() < min) {
                min = bestAnt.getCurrentCost();
            }
        }
        return min;
    }

    private void init() {
        ants = new ArrayList<ArtificialAnt>();
        for (int i = 0; i < ANT_COUNT; i++) {
            ants.add(new ArtificialAnt(graph));
        }
    }

    private void generateAntSolutions() {
        /**
         * Reset Ant memory and position ant on random node
         */
        for (ArtificialAnt ant : ants) {
            ant.reset();
            ant.setInitialVertex(getRandomNode());
        }

        /**
         * Build solution incrementally
         */
        for (int i = 0; i < graph.getNodesCount(); i++) {
            for (ArtificialAnt ant : ants) {
                Edge selectedEdge = ant.walk();
                /**
                 * Updating local trails
                 */
                if (selectedEdge != null) {
                    double newPheromoneLevel = (1 - PHEROMONE_EVAPORATION_PARAM) * selectedEdge.getPheromoneLevel() + PHEROMONE_EVAPORATION_PARAM * LOCAL_TRAIL_PARAMETER;
                    selectedEdge.setPheromoneLevel(newPheromoneLevel);
                }
            }
        }
    }

    private ArtificialAnt getBestPathAnt(List<ArtificialAnt> ants) {
        int min = Integer.MAX_VALUE;
        ArtificialAnt output = null;
        for (ArtificialAnt ant : ants) {
            if (ant.getCurrentCost() < min) {
                min = ant.getCurrentCost();
                output = ant;
            }
        }
        return output;
    }

    private Vertex getRandomNode() {
        Random random = new Random();
        int randomIndex = random.nextInt(graph.getNodesCount());
        Vertex randomVertex = new ArrayList<Vertex>(graph.getNodes().values()).get(randomIndex);
        return randomVertex;
    }

    private String getSystemState(int it) {
        StringBuffer output = new StringBuffer("System state at iteration " + it + "\n");
        for (int i = 0; i < ants.size(); i++) {
            ArtificialAnt ant = ants.get(i);
            output.append("Ant " + i + " ");
            output.append(ant.toString());
            output.append("\n");
        }
        output.append("===================\n");
        output.append(graph.getAllEdges());
        output.append("\n===================");
        return output.toString();
    }
}
