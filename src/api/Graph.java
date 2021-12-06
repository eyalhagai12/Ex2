package api;

import java.util.HashMap;
import java.util.Iterator;

public class Graph implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> nodes;
    private final HashMap<Integer, EdgeData> edges;

    /**
     * Default constructor
     */
    public Graph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

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
        for (int i = 0; i < edges_arr.length; ++i) {
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
                    n.addEdgeOut(e);
                }
                if (e.getDest() == n.getKey()) {
                    n.addEdgeIn(e);
                }
            }

            // add to the collection of nodes
            nodes.put(n.getKey(), n);
        }

    }

    /**
     * A duplicating constructor
     *
     * @param g The graph to duplicate
     */
    public Graph(DirectedWeightedGraph g) {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();

        // duplicate nodes
        Iterator<NodeData> nodes = g.nodeIter();
        while (nodes.hasNext()) {
            Node node = (Node) nodes.next();

            GeoPoint point = node.getLocation();
            int id = node.getKey();
            this.nodes.put(id, new Node(id, point));
        }

        // duplicate edges
        Iterator<EdgeData> edges = g.edgeIter();
        while (edges.hasNext()) {
            Edge edge = (Edge) edges.next();
            Edge newEdge = new Edge(edge.getSrc(), edge.getDest(), edge.getWeight());
            newEdge.setId(edge.getId());

            Node src = (Node) getNode(newEdge.getSrc());
            Node dest = (Node) getNode(newEdge.getDest());

            src.addEdgeOut(newEdge);
            dest.addEdgeIn(newEdge);
            this.edges.put(newEdge.getId(), newEdge);
        }

    }

    public Graph getTranspose() {
        Graph gTranspose = new Graph(this);

        for (NodeData node : gTranspose.nodes.values()) {
            ((Node) node).transposeNode();
        }

        return gTranspose;
    }

    @Override
    public NodeData getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (nodes.get(src) == null) {
            return null;
        }
        return ((Node) nodes.get(src)).getEdgeTo(dest);
    }

    @Override
    public void addNode(NodeData n) {
        if (nodes.containsKey(n.getKey())) {
            System.out.println("Key " + n.getKey() + " already exists");
        } else {
            nodes.put(n.getKey(), n);
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

        // add to the relevant nodes
        ((Node) nodes.get(src)).addEdgeOut(edge);
        ((Node) nodes.get(dest)).addEdgeIn(edge);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return edges.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return ((Node) nodes.get(node_id)).getOut_edges().values().iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        // get node
        Node node = (Node) nodes.get(key);

        // delete all edges that go out from that node
        for (EdgeData e : node.getOut_edges().values()) {
            edges.remove(((Edge) e).getId());
        }

        // delete all nodes coming in
        for (EdgeData e : node.getIn_edges().values()) {
            Node fromNode = (Node) getNode(e.getSrc());
            fromNode.deleteEdgeTo(key);
            edges.remove(((Edge) e).getId());
        }

        // remove node from map
        nodes.remove(key);

        return node;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        // get the edge
        EdgeData edge = ((Node) nodes.get(src)).getEdgeTo(dest);

        // remove from edges map
        edges.remove(((Edge) edge).getId());

        // delete edge in the source node
        ((Node) nodes.get(src)).deleteEdgeTo(dest);

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
