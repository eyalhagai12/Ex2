package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class saveGraph {
    private List<LinkedTreeMap<String, Object>> Edges;
    private List<LinkedTreeMap<String, Object>> Nodes;

    public saveGraph(DirectedWeightedGraph graph) {
        Nodes = new LinkedList<>();
        Edges = new LinkedList<>();

        // fill nodes
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData node = nodeIter.next();
            GeoLocation point = node.getLocation();
            LinkedTreeMap<String, Object> node_map = new LinkedTreeMap<>();
            String pos = point.x() + "," + point.y() + "," + point.z();
            node_map.put("pos", pos);
            node_map.put("id", node.getKey());
            Nodes.add(node_map);
        }

        // fill edges
        Iterator<EdgeData> edgeIter = graph.edgeIter();
        while (edgeIter.hasNext()) {
            EdgeData edge = edgeIter.next();
            LinkedTreeMap<String, Object> edge_map = new LinkedTreeMap<>();
            edge_map.put("src", edge.getSrc());
            edge_map.put("w", edge.getWeight());
            edge_map.put("dest", edge.getDest());
            Edges.add(edge_map);
        }
    }

    public boolean save(String path){
        File outFile = new File(path);
        try {
            FileWriter fw = new FileWriter(outFile);
            fw.write("");
            Gson json = new GsonBuilder().setPrettyPrinting().create();

            json.toJson(this, fw);

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
