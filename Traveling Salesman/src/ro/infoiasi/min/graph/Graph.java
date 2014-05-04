package ro.infoiasi.min.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<String, Node> nodes;
    private Set<Edge> edges;

    public Graph() {
        nodes = new HashMap<String, Node>();
        edges = new HashSet<Edge>();
    }

    public Map<String, Node> getNodes() {
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
    public Set<Edge> getEdges(Node node) {
        Set<Edge> output = new HashSet<Edge>();
        for (Edge e : edges) {
            if (e.getNode1().equals(node) || e.getNode2().equals(node)) {
                output.add(e);
            }
        }
        return output;
    }

    public void addNode(Node n) {
        nodes.put(n.getName(), n);
    }

    public void addEdge(Node node1, Node node2, int cost) {
        edges.add(new Edge(node1, node2, cost));
    }

    public Node getNode(String name) {
        if (nodes != null) {
            return nodes.get(name);
        }
        return null;
    }

    public int getCostBetweenNodes(Node node1, Node node2) {
        if (node1.equals(node2)) {
            return 0;
        }
        Edge selectedEdge = getEdgeBetweenNodes(node1, node2);
        if (selectedEdge != null) {
            return selectedEdge.getCost();
        }
        return Integer.MAX_VALUE;
    }

    public Edge getEdgeBetweenNodes(Node node1, Node node2) {
        if (node1.equals(node2)) {
            return null;
        }
        for (Edge e : edges) {
            if (e.getNode1().equals(node1) || e.getNode1().equals(node2)) {
                if (e.getNode2().equals(node1) || e.getNode2().equals(node2)) {
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
                "\nedges=" + edges +
                '}';
    }
}
