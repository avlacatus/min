/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.infoiasi.min.graph;

public class Edge {

    private Vertex vertex1;
    private Vertex vertex2;
    private double cost;
    private double pheromoneLevel;

    public Edge(Vertex sourceVertex, Vertex targetVertex, double cost) {
        this.vertex1 = sourceVertex;
        this.vertex2 = targetVertex;
        this.cost = cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public double getPheromoneLevel() {
        return pheromoneLevel;
    }

    public void setPheromoneLevel(double pheromoneLevel) {
        this.pheromoneLevel = pheromoneLevel;
    }

    @Override
    public String toString() {
        return "{" + vertex1.getName() +
                ", " + vertex2.getName() +
                ", " + cost +
                ", " + pheromoneLevel +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (Double.compare(edge.cost, cost) != 0) return false;
        if (Double.compare(edge.pheromoneLevel, pheromoneLevel) != 0) return false;
        if (!vertex1.equals(edge.vertex1)) return false;
        if (!vertex2.equals(edge.vertex2)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = vertex1.hashCode();
        result = 31 * result + vertex2.hashCode();
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pheromoneLevel);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
