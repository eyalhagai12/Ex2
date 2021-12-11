package api;

import java.io.File;
import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    private Graph graph;

    public GraphAlgo() {
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        Iterator<NodeData> nodeIter = g.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData origin = nodeIter.next();
            Node node = (Node) origin;

            // transform to our implementation
            node.setId(origin.getKey());
            node.setWeight(0);
            node.setLocation(origin.getLocation());
            node.setLocation(g.getNode(node.getKey()).getLocation());

            graph.addNode(node);
        }

        Iterator<EdgeData> edgeIter = g.edgeIter();
        int i = 0;
        while (edgeIter.hasNext()) {
            EdgeData origin = edgeIter.next();

            // transform to our implementation
            graph.connect(origin.getSrc(), origin.getDest(), origin.getWeight());
        }
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
        // check exceptions
        if (graph.edgeSize() < graph.nodeSize() - 1) return false;
        Iterator<NodeData> iterator = graph.nodeIter();
        while (iterator.hasNext()) {
            NodeData node = iterator.next();
            if (((Node) node).getIn_edges().size() == 0 || ((Node) node).getOut_edges().size() == 0)
                return false;
        }
        // get transposed graph
        Graph gTrans = graph.getTranspose();

        // use topological sort twice
        // once on the original graph and once on the transposed graph
        // ad if the times of the nodes that took the longest time is equal then we have a strongly connected graph
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
        Iterator<NodeData> iter = graph.nodeIter();
        for (; iter.hasNext(); ) {
            NodeData n = iter.next();
            n.setTag(0);
        }
        double[] weights = new double[graph.nodeSize()];
        if (weights.length > 100) {
            DjikstraThread thread1 = new DjikstraThread(graph, weights, 0, (weights.length / 4));
            DjikstraThread thread2 = new DjikstraThread((Graph) this.copy(), weights, (weights.length / 4), 2 * (weights.length / 4));
            DjikstraThread thread3 = new DjikstraThread((Graph) this.copy(), weights, 2 * (weights.length / 4), 3 * (weights.length / 4));
            DjikstraThread thread4 = new DjikstraThread((Graph) this.copy(), weights, 3 * (weights.length / 4), (weights.length));
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            while (thread1.isAlive() || thread2.isAlive() || thread3.isAlive() || thread4.isAlive()) {
                continue;
            }
            thread1.interrupt();
            thread2.interrupt();
            thread3.interrupt();
            thread4.interrupt();
        } else {
            iter = graph.nodeIter();
            for (int i = 0; i < weights.length; i++) {
                NodeData n = iter.next();
                weights[i] = utils.Dijkstra(graph, n);
            }
        }
        double min = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < weights.length; i++) {
            if (min > weights[i]) {
                min = weights[i];
                index = i;
            }
        }
        return graph.getNode(index);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return utils.customSearch(graph, cities);
    }

    @Override
    public boolean save(String file) {
        String path;
        if (!file.contains("/")) {
            path = "saved_graphs/" + file;
            jsonParser.validateFilePath(path);
        } else {
            path = file;
        }
        SaveGraph sGraph = new SaveGraph(graph);
        return sGraph.save(path);
    }

    @Override
    public boolean load(String file) {
        graph = new Graph(file);
        return graph != null;
    }
}
