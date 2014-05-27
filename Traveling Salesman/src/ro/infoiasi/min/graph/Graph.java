package ro.infoiasi.min.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<String, Vertex> nodes;
    private Set<Edge> edges;

    public Graph() {
        nodes = new HashMap<String, Vertex>();
        edges = new HashSet<Edge>();
    }

    public Map<String, Vertex> getNodes() {
        return nodes;
    }

    public Set<Edge> getAllEdges() {
        return edges;
    }

    public int getNodesCount() {
        if (nodes != null) {
            return nodes.size();
        }
        return 0;
    }

    public Set<Edge> getEdges(Vertex vertex) {
        Set<Edge> output = new HashSet<Edge>();
        for (Edge e : edges) {
            if (e.getVertex1().equals(vertex) || e.getVertex2().equals(vertex)) {
                output.add(e);
            }
        }
        return output;
    }

    public void clearPheromone() {
        for (Edge e : edges) {
            e.setPheromoneLevel(0);
        }
    }

    public void addNode(Vertex n) {
        nodes.put(n.getName(), n);
    }

    public void addEdge(Vertex vertex1, Vertex vertex2, double cost) {
        edges.add(new Edge(vertex1, vertex2, cost));
    }

    public Vertex getNode(String name) {
        if (nodes != null) {
            return nodes.get(name);
        }
        return null;
    }

    public double getCostBetweenNodes(Vertex vertex1, Vertex vertex2) {
        if (vertex1.equals(vertex2)) {
            return 0;
        }
        Edge selectedEdge = getEdgeBetweenNodes(vertex1, vertex2);
        if (selectedEdge != null) {
            return selectedEdge.getCost();
        }
        return Integer.MAX_VALUE;
    }

    public Edge getEdgeBetweenNodes(Vertex vertex1, Vertex vertex2) {
        if (vertex1.equals(vertex2)) {
            return null;
        }
        for (Edge e : edges) {
            if (e.getVertex1().equals(vertex1) || e.getVertex1().equals(vertex2)) {
                if (e.getVertex2().equals(vertex1) || e.getVertex2().equals(vertex2)) {
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                "edges=" + edges +
                '}';
    }
}
