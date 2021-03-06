package api;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import api.Edge;

public class Graph implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> nodes;
    private final HashMap<Integer, EdgeData> edges;
    private int mc;

    /**
     * Default constructor
     */
    public Graph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        mc = 0;
    }

    /**
     * Create a new graph from a given file
     *
     * @param path Path to the file
     */
    public Graph(String path) {
        if (!path.contains("/")) {

            // check if the file is at the "data" directory
            if (new File("data", path).exists()) {
                path = "data/" + path;
            } else if (new File("saved_graphs/", path).exists()) { //check if the file is in the "saved_graphs" directory
                path = "saved_graphs/" + path;
            }


            jsonParser.validateFilePath(path);
        }

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

        mc++;
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

        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        Iterator<NodeData> iterator = new Iterator<NodeData>() {
            private Iterator<NodeData> iterator = nodes.values().iterator();
            private int modeC = mc;

            @Override
            public void remove() {
                if (modeC != mc) throw new RuntimeException();
                NodeData node = iterator.next();
                removeNode(node.getKey());
                modeC++;
                iterator.remove();
            }

            @Override
            public boolean hasNext() {
                if (modeC != mc) throw new RuntimeException();
                return iterator.hasNext();
            }

            @Override
            public NodeData next() {
                if (modeC != mc) throw new RuntimeException();
                return iterator.next();
            }
        };
        return iterator;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        Iterator<EdgeData> iterator = new Iterator<EdgeData>() {
            private Iterator<EdgeData> iterator = edges.values().iterator();
            private int modeC = mc;

            @Override
            public void remove() {
                if (modeC != mc) throw new RuntimeException();
                EdgeData edge = iterator.next();
                removeEdge(edge.getSrc(), edge.getDest());
                modeC++;
                iterator.remove();
            }

            @Override
            public boolean hasNext() {
                if (modeC != mc) throw new RuntimeException();
                return iterator.hasNext();
            }

            @Override
            public EdgeData next() {
                if (modeC != mc) throw new RuntimeException();
                return iterator.next();
            }
        };
        return iterator;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Iterator<EdgeData> iterator = new Iterator<EdgeData>() {
            private Iterator<EdgeData> iterator = ((Node) nodes.get(node_id)).getOut_edges().values().iterator();
            private int modeC = mc;

            @Override
            public void remove() {
                if (modeC != mc) throw new RuntimeException();
                EdgeData edge = iterator.next();
                removeEdge(edge.getSrc(), edge.getDest());
                modeC++;
                iterator.remove();
            }

            @Override
            public boolean hasNext() {
                if (modeC != mc) throw new RuntimeException();
                return iterator.hasNext();
            }

            @Override
            public EdgeData next() {
                if (modeC != mc) throw new RuntimeException();
                return iterator.next();
            }
        };
        return iterator;
    }

    @Override
    public NodeData removeNode(int key) {
        // get node
        Node node = (Node) nodes.get(key);

        // delete all out going nodes
        Iterator<EdgeData> edges = edgeIter(key);
        while (edges.hasNext()) {
            Edge edge = (Edge) edges.next();
            this.edges.remove(edge.getId());
            Node nextNode = (Node) getNode(edge.getDest());
            nextNode.deleteEdgeFrom(key);
        }

        for (EdgeData edgeData : node.getIn_edges().values()) {
            Edge edge = (Edge) edgeData;
            this.edges.remove(edge.getId());
            Node prevNode = (Node) getNode(edge.getSrc());

            if (prevNode != null) {
                prevNode.deleteEdgeTo(node.getKey());
            }
        }

        // remove node from map
        nodes.remove(key);

        mc++;

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

        mc++;

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
        return mc;
    }
}
