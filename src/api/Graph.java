package api;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class Graph implements DirectedWeightedGraph {

    HashMap<Integer, NodeData> nodes;

    public Graph(String path) {
        try {
            JSONArray[] data = jsonParser.parseJson(path);
            createNodes(data[0]);
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Iterate over the nodes json array
     *
     * @param nodes The JSONArray object on which we iterate
     */
    private void createNodes(JSONArray nodes) {
        while (nodes.iterator().hasNext()) {
            HashMap<String, String> map = (HashMap<String, String>) nodes.iterator().next();
            int id = Integer.parseInt(map.get("id"));

            String loc_str = map.get("pos");
            String[] split = loc_str.split(",");

            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            int z = Integer.parseInt(split[2]);
            GeoPoint point = new GeoPoint(x, y, z);

            Node newNode = new Node(id, point);
            this.nodes.put(id, newNode);
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
