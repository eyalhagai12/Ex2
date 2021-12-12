package api;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class has some graph common algorithms
 */
public class utils {

    /**
     * This function takes a graph and returns a list sorted by topological sort
     *
     * @param g The graph to sort
     * @return A list of type LinkedList<NodeData> sorted by topological sort
     */
    public static LinkedList<NodeData> TopologicalSort(DirectedWeightedGraph g) {
        // init an empty list
        LinkedList<NodeData> list = new LinkedList<>();

        // iterate over all nodes and init them properly
        resetNodes(g);

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
    private static int DFSForTopSort(DirectedWeightedGraph graph, NodeData node, int time, List<NodeData> list) {
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


    /**
     * Reset the nodes tags
     *
     * @param g The graph containing the nodes
     */
    public static void resetNodes(DirectedWeightedGraph g) {
        // reset all node tags and finish times
        Iterator<NodeData> reset = g.nodeIter();
        while (reset.hasNext()) {
            Node node = (Node) reset.next();
            node.setTag(0);
            node.setFinishTime(0);
            node.setWeight(0);
            node.setInfo("");
        }
    }

    /**
     * A function that does a breadth first search and returns a list of nodes representing the path
     *
     * @param graph The graph on which we operate
     * @param start The node from which we start
     * @param end   The node to which we want to find a path to
     * @return A list of nodes
     */
    public static List<NodeData> BFSShortestPath(DirectedWeightedGraph graph, int start, int end) {
        // reset the tags of the nodes in the graph
        resetNodes(graph);

        // init result list
        List<NodeData> result = new LinkedList<>();

        //init queue
        Queue<NodeData> nodes = new LinkedList<>();

        // get relevant nodes
        NodeData startNode = graph.getNode(start);
        NodeData endNode = graph.getNode(end);

        // add the first node to the queue
        nodes.add(startNode);
        NodeData currentNode;
        boolean found = false;
        startNode.setTag(1);

        while (!nodes.isEmpty()) {
            currentNode = nodes.remove(); // save current node

            // if node was found, break
            if (currentNode == endNode) {
                found = true;
                break;
            } else { // add all children to the queue and save the previous node for each node
                Node node = (Node) currentNode;
                HashMap<Integer, EdgeData> next_edges = node.getOut_edges();

                for (EdgeData edge : next_edges.values()) {
                    Node nextNode = (Node) graph.getNode(edge.getDest());
                    double pathWeight = currentNode.getWeight() + edge.getWeight();

                    if (nextNode.getTag() == 0) {
                        nextNode.setTag(1);
                        nodes.add(nextNode);
                        nextNode.setPreviousNode(currentNode);
                        nextNode.setWeight(pathWeight);
                    } else { // check if this route to the node is cheaper than its original route
                        if (pathWeight < nextNode.getWeight()) {
                            nextNode.setPreviousNode(currentNode);
                            nextNode.setWeight(pathWeight);
                        }
                    }


                }
            }
        }

        if (!found) {
            return null;
        }

        // backtrack all nodes and add them to the list
        Node n = (Node) endNode;
        ((Node) startNode).setPreviousNode(null);
        while (n != null) {
            result.add(n);
            n = (Node) n.getPreviousNode();
        }

        Collections.reverse(result);
        return result;
    }

    public static double Dijkstra(DirectedWeightedGraph g, NodeData source) {
        Iterator<NodeData> iterator = g.nodeIter();
        for (; iterator.hasNext(); ) {
            iterator.next().setWeight(Double.MAX_VALUE);
        }
        PriorityQueue<NodeData> minHeap = new PriorityQueue<NodeData>(new Comparator<NodeData>() {
            @Override
            public int compare(NodeData o1, NodeData o2) {
                return Double.compare(((Node) o1).getWeight(), ((Node) o2).getWeight());
            }
        });
        source.setWeight(0);
        minHeap.add(source);
        NodeData temp;
        while (!minHeap.isEmpty()) {
            temp = minHeap.remove();
            if (temp.getTag() == 1)
                continue;
            EdgeData[] tempEdges = new EdgeData[((Node) temp).getOut_edges().values().size()];
            tempEdges = ((Node) temp).getOut_edges().values().toArray(tempEdges);
            for (int i = 0; i < tempEdges.length && temp.getTag() != 1; i++) {
                // get weight of edge + weight of neighbor
                double weight = temp.getWeight() + tempEdges[i].getWeight();
                // if weight of temp + edge < weight of neighbor, set weight of temp + edge as weight of neighbor
                if (weight < g.getNode(tempEdges[i].getDest()).getWeight()) {
                    g.getNode(tempEdges[i].getDest()).setWeight(weight);
                }
            }

            temp.setTag(1);
            for (int i = 0; i < tempEdges.length; i++) {
                if (g.getNode(tempEdges[i].getDest()).getTag() == 0)
                    minHeap.add(g.getNode(tempEdges[i].getDest()));
            }
        }
        double maxWeight = Double.MIN_VALUE;
        int index = -1;
        iterator = g.nodeIter();
        for (; iterator.hasNext(); ) {
            NodeData n = iterator.next();
            if (maxWeight < n.getWeight()) {
                maxWeight = n.getWeight();
                index = n.getKey();
            }
        }
        // reset tags of nodes
        iterator = g.nodeIter();
        for (; iterator.hasNext(); ) {
            NodeData n = iterator.next();
            n.setTag(0);
        }
        return g.getNode(index).getWeight();
    }


    public static List<NodeData> customSearch(DirectedWeightedGraph graph, List<NodeData> cities) {
        List<NodeData> result = new LinkedList<>();
        NodeData bestNode = cities.get(0);

        while (bestNode != null) {
            // get the next node in cities
            resetNodes(graph);
            bestNode = addClosest(graph, bestNode, cities, result);
        }


        result = result.stream().distinct().collect(Collectors.toList());

        return result;
    }

    private static NodeData addClosest(DirectedWeightedGraph graph, NodeData src, List<NodeData> cities, List<NodeData> result) {
        // use dijkstra
        Dijkstra(graph, src);

        double minDist = Double.MAX_VALUE;
        NodeData bestNode = null;
        for (NodeData node : cities) {
            if (node != null && node != src && !result.contains(node)) {
                if (node.getWeight() < minDist) {
                    minDist = node.getWeight();
                    bestNode = node;
                }
            }
        }

        if (bestNode != null) {
            List<NodeData> path = BFSShortestPath(graph, src.getKey(), bestNode.getKey());
            result.addAll(path);
        }

        return bestNode;
    }
}
