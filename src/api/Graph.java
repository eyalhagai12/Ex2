package api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class Graph implements DirectedWeightedGraph {

    private Node[] nodes;
    private Edge[] edges;

    /**
     * Create a new graph from a given file
     *
     * @param path Path to the file
     */
    public Graph(String path) {
        // create a json parser to parse the json file
        jsonParser parser = new jsonParser(path);

        // extract the arrays of nodes and edges
        nodes = (Node[]) parser.getNodes();
        edges = (Edge[]) parser.getEdges();

        // for the nodes array we need to translate the pos string to a GeoLocation object (in our case a GeoPoint)
        for (Node n : nodes){
            n.parsePosition();
        }
    }

    @Override
    public NodeData getNode(int key) {
        return null;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(NodeData n) {

    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
