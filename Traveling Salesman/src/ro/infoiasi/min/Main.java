package ro.infoiasi.min;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.infoiasi.min.ant.AntColonySimulation;
import ro.infoiasi.min.graph.Graph;
import ro.infoiasi.min.graph.Vertex;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Alexandra on 04/05/14.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        try {
            double avg = 0;
            long now = System.currentTimeMillis();
            Graph g = readFromXML("fri26.xml");
            System.out.println("Reading finished in " + (double)(System.currentTimeMillis() - now)/1000);
            now = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                g.clearPheromone();
                AntColonySimulation simulation = new AntColonySimulation(g);
                double result = simulation.simulate(false);
                System.out.println("Best result: " + result);
                System.out.println("Result computed in " + (double)(System.currentTimeMillis() - now)/1000);
                now = System.currentTimeMillis();
                avg += result;
            }
            avg = avg / 20;
            System.out.println("Average: " + avg);
        } catch (Exception e) {
        }

    }

    private static Graph readFromFile(String fileName) throws IOException {
        Graph output = new Graph();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        while (in.ready()) {
            String s = in.readLine();
            if (s.startsWith("+")) {
                /**
                 * The line represents a vertex
                 */
                Vertex vertex = new Vertex(s.substring(1));
                output.addNode(vertex);
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
                Vertex vertex1 = output.getNode(node1Name);
                Vertex vertex2 = output.getNode(node2Name);
                if (vertex1 != null && vertex2 != null) {
                    if (output.getCostBetweenNodes(vertex1, vertex2) == Integer.MAX_VALUE) {
                        output.addEdge(vertex1, vertex2, edgeCost);
                    }

                }
            }
        }
        in.close();

        return output;
    }

    private static Graph readFromXML(String fileName) throws IOException, ParserConfigurationException, SAXException {
        Graph output = new Graph();
        File fXmlFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        NodeList vertices = doc.getElementsByTagName("vertex");
        int dimension = vertices.getLength();
        for (int i = 0; i < dimension; i++) {
            output.addNode(new Vertex("Vertex" + i));
        }

        for (int i = 0; i < vertices.getLength(); i++) {
            Vertex currentVertex = output.getNode("Vertex" + i);

            Element vertexNode = (Element) vertices.item(i);
            NodeList edges = vertexNode.getElementsByTagName("edge");

            for (int j = 0; j < edges.getLength(); j++) {
                Element edgeNode = (Element) edges.item(j);
                int vertexIndex = Integer.valueOf(edgeNode.getTextContent());

                Vertex refVertex = output.getNode("Vertex" + vertexIndex);

                String costAttribute = edgeNode.getAttribute("cost");
                Double cost = Double.valueOf(costAttribute);

                if (output.getCostBetweenNodes(currentVertex, refVertex) == Integer.MAX_VALUE) {
                    output.addEdge(currentVertex, refVertex, cost);
                }
            }
        }
        return output;
    }
}
