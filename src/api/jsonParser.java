package api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.JsonTreeReader;

import java.io.File;
import java.util.Arrays;
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        // create a Gson object
        Gson parser = new Gson();

        // extract string from StringBuilder
        result = str.toString();

        // do some editing to the string
        String[] json_str = result.split("],");
        json_str[0] = json_str[0].substring(13) + "\n]";
        json_str[1] = json_str[1].substring(12, json_str[1].length() - 2);

        // parse to different arrays
        EdgeData[] edges = parser.fromJson(json_str[0], Edge[].class);
        NodeData[] nodes = parser.fromJson(json_str[1], Node[].class);

        // save the results
        this.nodes = nodes;
        this.edges = edges;
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
