package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class jsonParser {
    public static JSONArray[] parseJson(String path) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(path));

        JSONObject json = (JSONObject) obj;

        JSONArray edges = (JSONArray) json.get("Edges");
        JSONArray nodes = (JSONArray) json.get("Nodes");

        return new JSONArray[] {nodes, edges};
    }

    public static void main(String[] args) {
        try {
            parseJson("data/G1.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
