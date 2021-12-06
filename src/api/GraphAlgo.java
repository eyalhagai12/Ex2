package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    private Graph graph;

    public GraphAlgo() {
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (Graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new Graph(graph);
    }

    @Override
    public boolean isConnected() {
        // get transposed graph
        Graph gTrans = graph.getTranspose();

        // use topological sort twice
        // once on the original graph and once on the transposed graph
        // ad if the times of the nodes that took the longest time is equal then we have a strongly connected graph
        // must say i am not sure about this algorithm
        LinkedList<NodeData> list = utils.TopologicalSort(graph);
        Node node = (Node) list.get(0);
        LinkedList<NodeData> transList = utils.TopologicalSort(gTrans);
        Node transNode = (Node) transList.get(0);

        return node.getKey() == transNode.getKey();
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        List<NodeData> list = utils.BFSShortestPath(graph, src, dest);
        if (list == null) {
            return -1;
        }

        return list.get(list.size() - 1).getWeight();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return utils.BFSShortestPath(graph, src, dest);
    }

    @Override
    public NodeData center() {
        if (!isConnected()) return null;
    	// reset tags of nodes
    	for (int i = 0; i < graph.nodeSize(); i++) {
			graph.getNode(i).setTag(0);
		}
        double[] weights = new double[graph.nodeSize()];
        DjikstraThread thread1 = new DjikstraThread(graph, weights, 0, weights.length/2);
        DjikstraThread thread2 = new DjikstraThread((Graph)this.copy(), weights, weights.length/2, weights.length);
        thread1.start();
        thread2.start();
        while(thread1.isAlive() || thread2.isAlive()) {continue;}
//        for (int i = 0; i < weights.length; i++) {
//			weights[i] = utils.Dijkstra(graph, graph.getNode(i));
//		}
        double min = Double.MAX_VALUE;
        int index=-1;
        for (int i = 0; i < weights.length; i++) {
			if(min>weights[i]) {
				min=weights[i];
				index=i;
			}
		}
        return graph.getNode(index);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        String path = file.contains("/") ? file : "saved_graphs" + file;
        File outFile = new File(path);
        try {
            FileWriter fw = new FileWriter(outFile);
            fw.write("");
            Gson json = new GsonBuilder().setPrettyPrinting().create();

            json.toJson(graph, fw);

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        graph = new Graph(file);
        return graph != null;
    }
}
