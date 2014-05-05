/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.infoiasi.min.graph;

public class Edge {

    private Node node1;
    private Node node2;
    private int cost;
    private double pheromoneLevel;

    public Edge(Node sourceNode, Node targetNode, int cost) {
        this.node1 = sourceNode;
        this.node2 = targetNode;
        this.cost = cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public double getPheromoneLevel() {
        return pheromoneLevel;
    }

    public void setPheromoneLevel(double pheromoneLevel) {
        this.pheromoneLevel = pheromoneLevel;
    }

    @Override
    public String toString() {
        return "{" + node1.getName().substring(0,3) +
                ", " + node2.getName().substring(0,3) +
                ", " + cost +
                ", " + pheromoneLevel +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (cost != edge.cost) return false;
        if (Double.compare(edge.pheromoneLevel, pheromoneLevel) != 0) return false;
        if (!node1.equals(edge.node1)) return false;
        if (!node2.equals(edge.node2)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = node1.hashCode();
        result = 31 * result + node2.hashCode();
        result = 31 * result + cost;
        temp = Double.doubleToLongBits(pheromoneLevel);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
