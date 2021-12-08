import api.*;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import java.util.LinkedList;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {

    public static void main(String[] args) {
        System.out.println(args[0]);
//        runGUI(args[0]);
        DirectedWeightedGraphAlgorithms algo = getGraphAlgo(args[0]);
    }


    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGraph(String json_file) {
        DirectedWeightedGraph ans = new Graph(json_file);
        return ans;
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGraphAlgo(String json_file) {
        DirectedWeightedGraph graph = new Graph(json_file);
        DirectedWeightedGraphAlgorithms ans = new GraphAlgo();
        ans.init(graph);
        return ans;
    }

    /**
     * This static function will run your GUI using the json file.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGraphAlgo(json_file);
        Graph_UI ui = new Graph_UI(alg);
    }
}