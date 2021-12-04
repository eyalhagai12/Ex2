package api;

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

        double dist = list.get(list.size() - 1).getWeight();

        return dist;
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
        return false;
    }

    @Override
    public boolean load(String file) {
        Graph graph = new Graph(file);
        return graph != null;
    }
}
