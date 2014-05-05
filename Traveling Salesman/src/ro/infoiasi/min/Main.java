package ro.infoiasi.min;

import ro.infoiasi.min.ant.AntColonySimulation;
import ro.infoiasi.min.graph.Graph;
import ro.infoiasi.min.graph.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Cosmin on 04/05/14.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {
            Graph g = readFromFile("romania.test");
            System.out.println(g);
            AntColonySimulation simulation = new AntColonySimulation(g);
            simulation.simulate();
            System.in.read();
        }
    }

    private static Graph readFromFile(String fileName) throws IOException {
        Graph output = new Graph();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        while (in.ready()) {
            String s = in.readLine();
            if (s.startsWith("+")) {
                /**
                 * The line represents a node
                 */
                Node node = new Node(s.substring(1));
                output.addNode(node);
            } else if (s.startsWith("//")) {
                /**
                 * The line is commented; ignore
                 */
            } else {
                String[] tokens = s.split(" - ");
                String[] tokens2 = tokens[1].split(" ");
                String node1Name = tokens[0];
                String node2Name = tokens2[0];
                int edgeCost = Integer.parseInt(tokens2[1]);
                Node node1 = output.getNode(node1Name);
                Node node2 = output.getNode(node2Name);
                if (node1 != null && node2 != null) {
                    if (output.getCostBetweenNodes(node1, node2) == Integer.MAX_VALUE) {
                        output.addEdge(node1, node2, edgeCost);
                    }

                }
            }
        }
        in.close();

        return output;
    }
}
