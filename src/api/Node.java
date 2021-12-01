package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node implements NodeData {
    private final int id;
    private GeoPoint location;
    private double weight;
    private HashMap<Integer, Edge> out_edges;
    private String pos;
    private int tag;

    /**
     * Empty constructor
     */
    public Node() {
        id = 0;
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
        tag = 0;
    }

    /**
     * Init the list (the use of GSON doesnt use the constructor, so manual init is needed)
     */
    public void initEdges() {
        this.out_edges = new HashMap<>();
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
     * Add an edge to the list
     *
     * @param edge The new edge to add to the list
     */
    public void addEdge(EdgeData edge) {
        out_edges.put(edge.getDest(), (Edge) edge);
    }

    /**
     * Get the edge going to the dest node
     *
     * @param dest The index of the node we want to go to
     * @return An edge object if it exists, else it returns null
     */
    public Edge getEdgeTo(int dest) {
        if (!out_edges.containsKey(dest)){
            return null;
        }
        return out_edges.get(dest);
    }

    /**
     * Delete the edge going to a given index
     *
     * @param dest The index of this nodes destination
     */
    public void deleteEdgeTo(int dest) {
        out_edges.remove(dest);
    }

    public HashMap<Integer, Edge> getOut_edges() {
        return out_edges;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getPos() {
        return pos;
    }

    public int outSize(){
        return out_edges.size();
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
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
