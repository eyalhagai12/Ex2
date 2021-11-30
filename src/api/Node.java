package api;

import java.util.ArrayList;
import java.util.List;

public class Node implements NodeData{
    private final int id;
    private GeoLocation location;
    private double weight;
    private final List<EdgeData> out_edges;
    private int tag;

    /**
     * Create a new node from an id and a location
     *
     * @param id The id of the node
     * @param location  The coordinates of the node
     */
    public Node(int id, GeoLocation location){
        this.id = id;
        this.location = location;
        out_edges = new ArrayList<>();
        tag = 0;
    }

    /**
     * Add an edge to the list
     * @param edge The new edge to add to the list
     */
    public void addEdge(EdgeData edge){
        out_edges.add(edge);
    }

    @Override
    public int getKey() {
        return id;
    }

    @Override
    public GeoLocation getLocation() {
        return location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location = p;
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
