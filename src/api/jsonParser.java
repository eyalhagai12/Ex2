package api;

import com.google.gson.*;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class jsonParser {
    private NodeData[] nodes;
    private EdgeData[] edges;

    /**
     * Create a jsonParser object
     *
     * @param path The path to the json file
     */
    public jsonParser(String path) {
        parseJson(path);
    }

    /**
     * Parse the json file and save in the attributes of this jsonParser object
     *
     * @param path The path to the file
     */
    private void parseJson(String path) {
        // get the file and create some variables
        File json = new File(path);
        StringBuilder str = new StringBuilder();
        String result = "";

        // read file to string
        try {
            Scanner scn = new Scanner(json);

            while (scn.hasNext()) {
                str.append(scn.nextLine()).append("\n");
            }

            // create a Gson object
            Gson parser = new Gson();

            // extract string from StringBuilder
            result = str.toString();

            // test
            HashMap map = parser.fromJson(result, HashMap.class);

            // parse to different arrays
            String edge_str = map.get("Edges").toString();
            edge_str = edge_str.replace("=", ":");
            edge_str = edge_str.replace("pos:", "pos:\"");
            edge_str = edge_str.replace(", id:", "\", id:");

            String node_str = map.get("Nodes").toString();
            node_str = node_str.replace("=", ":");
            node_str = node_str.replace("pos:", "pos:\"");
            node_str = node_str.replace(", id:", "\", id:");

            JsonReader edgeReader = new JsonReader(new StringReader(edge_str));
            JsonReader nodeReader = new JsonReader(new StringReader(node_str));

            edgeReader.setLenient(true);
            nodeReader.setLenient(true);

            EdgeData[] edges = parser.fromJson(edgeReader, Edge[].class);
            NodeData[] nodes = parser.fromJson(nodeReader, Node[].class);

            // save the results
            this.nodes = nodes;
            this.edges = edges;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Get the nodes that were parsed from the json
     *
     * @return A NodeData array
     */
    public NodeData[] getNodes(){
        return nodes;
    }

    /**
     * Get the edges that were parsed from the json
     *
     * @return An EdgeData array
     */
    public EdgeData[] getEdges(){
        return edges;
    }
}
