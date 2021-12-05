package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    private Graph graph;

    public GraphAlgo() {
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (Graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new Graph(graph);
    }

    @Override
    public boolean isConnected() {
        // get transposed graph
        Graph gTrans = graph.getTranspose();

        // use topological sort twice
        // once on the original graph and once on the transposed graph
        // ad if the times of the nodes that took the longest time is equal then we have a strongly connected graph
        // must say i am not sure about this algorithm
        LinkedList<NodeData> list = utils.TopologicalSort(graph);
        Node node = (Node) list.get(0);
        LinkedList<NodeData> transList = utils.TopologicalSort(gTrans);
        Node transNode = (Node) transList.get(0);

        return node.getKey() == transNode.getKey();
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        List<NodeData> list = utils.BFSShortestPath(graph, src, dest);
        if (list == null) {
            return -1;
        }

        return list.get(list.size() - 1).getWeight();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return utils.BFSShortestPath(graph, src, dest);
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        File outFile = new File("saved_graphs/" + file);
        try {
            FileWriter fw = new FileWriter(outFile);
            fw.write("");
            Gson json = new GsonBuilder().setPrettyPrinting().create();

            json.toJson(graph, fw);

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        graph = new Graph(file);
        return graph != null;
    }
}
