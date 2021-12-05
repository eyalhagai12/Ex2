package api;

import java.util.HashMap;

public class Node implements NodeData {
    private final int id;
    private GeoPoint location;
    private double weight = 0;
    private HashMap<Integer, EdgeData> out_edges;
    private HashMap<Integer, EdgeData> in_edges;
    private String pos;
    private int tag;
    private int finishTime;
    private NodeData previousNode = null;

    /**
     * Empty constructor
     */
    public Node(int id) {
        this.id = id;
    }

    /**
     * Create a new node from an id and a location
     *
     * @param id       The id of the node
     * @param location The coordinates of the node
     */
    public Node(int id, GeoPoint location) {
        this.id = id;
        this.location = location;
        out_edges = new HashMap<>();
        in_edges = new HashMap<>();
        tag = 0;
        finishTime = 0;
    }

    /**
     * Init the list (the use of GSON doesn't use the constructor, so manual init is needed)
     */
    public void initEdges() {
        this.out_edges = new HashMap<>();
        this.in_edges = new HashMap<>();
    }

    /**
     * Parse the pos string to a GeoPoint and set location to be that GeoPoint
     */
    public void parsePosition() {
        // split pos to 3 numbers
        String[] split = pos.split(",");

        // parse them to doubles
        double x = Double.parseDouble(split[0]);
        double y = Double.parseDouble(split[1]);
        double z = Double.parseDouble(split[2]);

        // set location to be that new GeoPoint
        location = new GeoPoint(x, y, z);
    }

    /**
     * Add an edge to the map
     *
     * @param edge The new edge to add to the map
     */
    public void addEdgeOut(EdgeData edge) {
        out_edges.put(edge.getDest(), (Edge) edge);
    }

    /**
     * Add an edge to the map
     *
     * @param edge The new edge to add to the map
     */
    public void addEdgeIn(EdgeData edge) {
        in_edges.put(edge.getSrc(), (Edge) edge);
    }

    /**
     * Get the edge going to the dest node
     *
     * @param dest The index of the node we want to go to
     * @return An edge object if it exists, else it returns null
     */
    public EdgeData getEdgeTo(int dest) {
        if (!out_edges.containsKey(dest)) {
            return null;
        }
        return (Edge) out_edges.get(dest);
    }

    /**
     * Delete the edge going to a given index
     *
     * @param dest The index of this nodes destination
     */
    public void deleteEdgeTo(int dest) {
        out_edges.remove(dest);
    }

    /**
     * Delete the edge coming from a given index
     *
     * @param src The index of the source
     */
    public void deleteEdgeFrom(int src) {
        in_edges.remove(src);
    }

    /**
     * Return a transposed node
     *
     * @return A Node object representing the transpose of hte original node
     */
    public void transposeNode() {
        // swap in and out edges
        HashMap<Integer, EdgeData> temp = this.in_edges;
        this.in_edges = this.out_edges;
        this.out_edges = temp;

        // swap edge source and destination
        for (EdgeData edge : this.out_edges.values()) {
            ((Edge) edge).transposeEdge();
        }

    }

    /**
     * Set the previous node for the BFS
     *
     * @param node The node to set as the previous node
     */
    public void setPreviousNode(NodeData node) {
        this.previousNode = node;
    }

    /**
     * Get the previous node
     *
     * @return The previous node in the BFS algorithm
     */
    public NodeData getPreviousNode() {
        return previousNode;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public HashMap<Integer, EdgeData> getOut_edges() {
        return out_edges;
    }

    public HashMap<Integer, EdgeData> getIn_edges() {
        return out_edges;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getPos() {
        return pos;
    }

    public int outSize() {
        return out_edges.size();
    }

    public int inSize() {
        return in_edges.size();
    }

    @Override
    public int getKey() {
        return id;
    }

    @Override
    public GeoPoint getLocation() {
        return location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location = (GeoPoint) p;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public String toString(){
        String str = "";
        return str;
    }
}
