package api;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class Graph implements DirectedWeightedGraph {

    private final HashMap<Integer, Node> nodes;
    private final HashMap<Integer, Edge> edges;

    /**
     * Create a new graph from a given file
     *
     * @param path Path to the file
     */
    public Graph(String path) {
        // init maps
        nodes = new HashMap<>();
        edges = new HashMap<>();

        // create a json parser to parse the json file
        jsonParser parser = new jsonParser(path);

        // extract the arrays of nodes and edges
        Node[] nodes_arr = (Node[]) parser.getNodes();
        Edge[] edges_arr = (Edge[]) parser.getEdges();

        // add ids to the edges
        for(int i = 0; i < edges_arr.length; ++i){
            edges_arr[i].setId(i);
            edges.put(i, edges_arr[i]);
        }

        // for the nodes_arr array we need to translate the pos string to a GeoLocation object (in our case a GeoPoint)
        // and add it to the nodes map
        for (Node n : nodes_arr) {
            // init the nodes edges collection
            n.initEdges();

            // parse the position of the node
            n.parsePosition();

            // add the edges going out of this node to the node
            for (Edge e : edges_arr) {
                if (e.getSrc() == n.getKey()) {
                    n.addEdge(e);
                }
            }

            // add to the collection of nodes
            nodes.put(n.getKey(), n);
        }

    }

    @Override
    public NodeData getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (nodes.get(src) == null){
            return null;
        }
        return nodes.get(src).getEdgeTo(dest);
    }

    @Override
    public void addNode(NodeData n) {
        if (nodes.containsKey(n.getKey())) {
            System.out.println("Key " + n.getKey() + " already exists");
        } else {
            nodes.put(n.getKey(), (Node)n);
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        // create a new edge
        Edge edge = new Edge(src, dest, w);

        // give new edge an id
        edge.setId(edges.size());

        // add te new edge to the edges map
        edges.put(edges.size(), edge);

        // add to the node from which this edge is going out
        nodes.get(src).addEdge(edge);
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
        // get node
        Node node = nodes.get(key);

        // delete all edges that go out from that node
        for (Edge e : node.getOut_edges().values()){
            edges.remove(e.getId());
        }

        // remove node from map
        nodes.remove(key);

        return node;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        // get the edge
        Edge edge = nodes.get(src).getEdgeTo(dest);

        // remove from edges map
        edges.remove(edge.getId());

        // delete edge in the source node
        nodes.get(src).deleteEdgeTo(dest);

        return edge;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public int getMC() {
        return 0;
    }
}
