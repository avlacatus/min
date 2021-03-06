package ro.infoiasi.min.ant;

import ro.infoiasi.min.graph.Edge;
import ro.infoiasi.min.graph.Graph;
import ro.infoiasi.min.graph.Vertex;

import java.util.*;

/**
 * Created by Alexandra on 04/05/14.
 */
public class ArtificialAnt {

    private Graph graph;
    private Vertex initialVertex;
    private Vertex currentVertex;
    private List<Vertex> nodesVisited = null;
    private int currentCost;


    public ArtificialAnt(Graph graph) {
        nodesVisited = new ArrayList<Vertex>();
        this.graph = graph;
    }

    public void setInitialVertex(Vertex initialVertex) {
        this.initialVertex = initialVertex;
        this.currentVertex = initialVertex;
    }

    public Vertex getInitialVertex() {
        return initialVertex;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    public List<Vertex> getVisitedNodes() {
        return nodesVisited;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    /**
     * Use heuristics to calculate the new node to visit and make the move
     */
    public Edge walk() {
        if (nodesVisited.size() == graph.getNodesCount()) {
            System.out.println("ant cycle has finished");
            return null;
        }
        Edge selectedEdge = null;
        Set<Edge> connectedEdges = graph.getEdges(currentVertex);
        Set<Edge> possibleEdges = new HashSet<Edge>();
        for (Edge e : connectedEdges) {
            Vertex otherVertex = (currentVertex.equals(e.getVertex1())) ? e.getVertex2() : e.getVertex1();
            if (!nodesVisited.contains(otherVertex) && !otherVertex.equals(initialVertex)) {
                possibleEdges.add(e);
            }
        }

        Vertex nextVertex = null;
        if (possibleEdges.isEmpty()) {
            nextVertex = initialVertex;
            /**
             * The walk has finished.
             */
            for (Edge e : connectedEdges) {
                Vertex otherVertex = (currentVertex.equals(e.getVertex1())) ? e.getVertex2() : e.getVertex1();
                if (otherVertex.equals(initialVertex)) {
                    selectedEdge = e;
                }
            }
        } else {
            Map<Edge, Double> transitionsProbabilities = new HashMap<Edge, Double>();
            for (Edge edge : possibleEdges) {
                transitionsProbabilities.put(edge, processEdgeProbability(edge, possibleEdges, false));
            }

            selectedEdge = selectRandomly(transitionsProbabilities);

            if (selectedEdge == null) {
                transitionsProbabilities = new HashMap<Edge, Double>();
                for (Edge edge : possibleEdges) {
                    transitionsProbabilities.put(edge, processEdgeProbability(edge, possibleEdges, true));
                }
                selectedEdge = selectRandomly(transitionsProbabilities);
            }

            nextVertex = (currentVertex.equals(selectedEdge.getVertex1())) ? selectedEdge.getVertex2() : selectedEdge.getVertex1();
        }

        nodesVisited.add(currentVertex);
        currentCost += selectedEdge.getCost();
        currentVertex = nextVertex;
        return selectedEdge;
    }

    private Edge selectRandomly(Map<Edge, Double> edgeProbabilities) {
        List<Map.Entry<Edge, Double>> orderedProbabilities = new ArrayList<Map.Entry<Edge, Double>>(edgeProbabilities.entrySet());
        Collections.sort(orderedProbabilities, new Comparator<Map.Entry<Edge, Double>>() {
            @Override
            public int compare(Map.Entry<Edge, Double> o1, Map.Entry<Edge, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Edge selectedEdge = null;
        Random r = new Random();
        double randomDouble = r.nextDouble();
        double currentSum = 0;
        for (Map.Entry<Edge, Double> entry : orderedProbabilities) {
            if (randomDouble >= currentSum && randomDouble < currentSum + entry.getValue()) {
                selectedEdge = entry.getKey();
                break;
            }
            currentSum += entry.getValue();
        }
        return selectedEdge;
    }

    private Double processEdgeProbability(Edge e, Set<Edge> possibilities, boolean considerDistanceOnly) {
        Double output;
        if (!considerDistanceOnly) {
            output = Math.pow(e.getPheromoneLevel(), AntColonySimulation.PHEROMONE_CONTROL_PARAM) * Math.pow((double) 1 / e.getCost(), AntColonySimulation.DESIRABILITY_CONTROL_PARAM);
        } else {
            output = Math.pow((double) 1 / e.getCost(), AntColonySimulation.DESIRABILITY_CONTROL_PARAM);
        }

        double sum = 0;
        for (Edge edge : possibilities) {
            if (!considerDistanceOnly) {
                sum += Math.pow(edge.getPheromoneLevel(), AntColonySimulation.PHEROMONE_CONTROL_PARAM) * Math.pow((double) 1 / edge.getCost(), AntColonySimulation.DESIRABILITY_CONTROL_PARAM);
            } else {
                sum += Math.pow((double) 1 / edge.getCost(), AntColonySimulation.DESIRABILITY_CONTROL_PARAM);
            }
        }

        output = output / sum;
        return output;
    }

    public void reset() {
        nodesVisited = new ArrayList<Vertex>();
        initialVertex = null;
        currentVertex = null;
        currentCost = 0;
    }

    @Override
    public String toString() {
        StringBuffer output = new StringBuffer("");
        for (Vertex vertex : getVisitedNodes()) {
            output.append(vertex + " -> ");
        }
        output.append(getCurrentVertex() + " -> ");
        output.append(getCurrentCost());
        return output.toString();
    }
}
