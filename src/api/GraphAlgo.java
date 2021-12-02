package api;

import java.util.*;

public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    private Graph graph;

    public GraphAlgo() {
    }

    /**
     * This function takes a graph and returns a list sorted by topological sort
     *
     * @param g The graph to sort
     * @return A list of type LinkedList<NodeData> sorted by topological sort
     */
    public LinkedList<NodeData> TopologicalSort(DirectedWeightedGraph g) {
        // init an empty list
        LinkedList<NodeData> list = new LinkedList<>();

        // iterate over all nodes and init them properly
        Iterator<NodeData> reset = g.nodeIter();
        while (reset.hasNext()) {
            Node node = (Node) reset.next();
            node.setTag(0);
            node.setFinishTime(0); // init in case it wasn't initiated before
        }

        // iterate over the nodes again and sort
        int time = 0;
        Iterator<NodeData> sort = g.nodeIter();
        LinkedList<NodeData> result = new LinkedList<>();

        while (sort.hasNext()) {
            Node node = (Node) sort.next();
            if (node.getTag() == 0) {
                time = DFSForTopSort(g, node, time, result);
            }

        }

        Collections.reverse(result);

        return result;
    }

    /**
     * A dfs like function that gets a graph, a node to start from, time which is 0 at the start
     * and a list to add elements to
     *
     * @param graph The graph on which we operate
     * @param node  The node from which to start
     * @param time  The time from which to start counting
     * @param list  The list to add the elements to
     * @return An integer representing the time it took to find this current node
     */
    private int DFSForTopSort(DirectedWeightedGraph graph, NodeData node, int time, List<NodeData> list) {
        node.setTag(1);
        time += 1;

        for (int next : ((Node) node).getOut_edges().keySet()) {
            Node nextNode = (Node) graph.getNode(next);
            if (nextNode.getTag() == 0) {
                time = DFSForTopSort(graph, nextNode, time, list);
                time += 1;
            }
        }

        ((Node) node).setFinishTime(time);
        list.add(node);

        return time;
    }

    public int DFS(DirectedWeightedGraph g) {
        // reset all node tags and finish times
        Iterator<NodeData> reset = g.nodeIter();
        while (reset.hasNext()) {
            Node node = (Node) reset.next();
            node.setTag(0);
            node.setFinishTime(0);
        }

        // iterate over all the nodes and run dfs
        int time = 0;
        Iterator<NodeData> sort = g.nodeIter();

        while (sort.hasNext()) {
            Node node = (Node) sort.next();
            if (node.getTag() == 0) {
                time = visit(g, node, time);
            }

        }

        return time;

    }

    /**
     * Visit all the unvisited neighbors of a given node
     *
     * @param graph The graph on which we operate
     * @param node  The node from which to start
     * @param time  The time from which to start
     * @return The time it took to finish processing that node
     */
    private int visit(DirectedWeightedGraph graph, NodeData node, int time) {
        node.setTag(1);
        time += 1;

        for (int next : ((Node) node).getOut_edges().keySet()) {
            Node nextNode = (Node) graph.getNode(next);
            if (nextNode.getTag() == 0) {
                time = visit(graph, nextNode, time);
                time += 1;
            }
        }

        ((Node) node).setFinishTime(time);

        return time;
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
        LinkedList<NodeData> list = TopologicalSort(graph);
        Node node = (Node) list.get(0);
        int time = DFS(graph);

        return node.getFinishTime() == time;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        Graph graph = new Graph(file);
        return graph != null;
    }
}
