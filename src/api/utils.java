package api;

import java.util.*;

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

    public static int DFS(DirectedWeightedGraph g) {
        // reset all node tags and finish times
        resetNodes(g);

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
    public static int visit(DirectedWeightedGraph graph, NodeData node, int time) {
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
        while (n != null) {
            result.add(n);
            n = (Node) n.getPreviousNode();
        }

        Collections.reverse(result);
        return result;
    }

    public static double Dijkstra(Graph g, NodeData source) {
        for (int i = 0; i < g.nodeSize(); i++) {
            g.getNode(i).setWeight(Double.MAX_VALUE);
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
        for (int i = 0; i < g.nodeSize(); i++) {
			if(maxWeight<g.getNode(i).getWeight()) {
				maxWeight=g.getNode(i).getWeight();
				index=i;
			}
				
		}
        // reset tags of nodes
        for (int i = 0; i < g.nodeSize(); i++) {
            g.getNode(i).setTag(0);
        }
        return g.getNode(index).getWeight();
    }

    public static List<NodeData> nearestNeighbor(DirectedWeightedGraph graph, List<NodeData> nodes) {
        resetNodes(graph);
        List<NodeData> result = new LinkedList<>();

        for (NodeData node : nodes) {
            if (node.getTag() == 0) {
                processNode(graph, node, result);
            }
        }

        return result;
    }


    /**
     * This function goes over all nodes and gets the closest
     *
     * @param graph The graph we work on
     * @param node The node to process
     * @param list THe list to update
     */
    private static void processNode(DirectedWeightedGraph graph, NodeData node, List<NodeData> list) {
        // set node as visited
        node.setTag(1);
        list.add(node);

        // iterate over out going nodes
        Iterator<EdgeData> iter = graph.edgeIter(node.getKey());
        Double minWeight = Double.MAX_VALUE;
        NodeData bestNode = null;

        while (iter.hasNext()) {
            EdgeData edge = iter.next();
            NodeData next = graph.getNode(edge.getDest());

            if (next.getTag() == 0 && edge.getWeight() < minWeight) {
                if (list.contains(next)) {
                    bestNode = next;
                    break;
                }
                minWeight = edge.getWeight();
                bestNode = next;
            }
        }

        if (bestNode != null)
            processNode(graph, bestNode, list);
    }

    // not entirely correct
    public static List<NodeData> closestInsertion(DirectedWeightedGraph graph, List<NodeData> cities){
        // rest nodes
        resetNodes(graph);

        // create result list
        List<NodeData> result = new LinkedList<>();
        int counter = 0;

        // add the first node to the result
        cities.get(0).setTag(1);
        result.add(cities.get(0));

        while (counter < cities.size()){
            double dist = Double.MAX_VALUE;
            NodeData next = null;
            NodeData prevNode = null;

            // iterate over all the nodes that the result tour can reach
            for (NodeData current : result){
                Iterator<EdgeData> iter = graph.edgeIter(current.getKey());
                while (iter.hasNext()){
                    EdgeData edge = iter.next();
                    NodeData node = graph.getNode(edge.getDest());

                    if (node.getTag() == 0 && edge.getWeight() < dist){
                        dist = edge.getWeight();
                        next = node;
                        prevNode = current;
                    }
                }
            }

            if (next != null) {
                next.setTag(1);
                counter = cities.contains(next) ? counter + 1 : counter;
                result.add(result.indexOf(prevNode) + 1, next);
            }
        }

        return result;
    }

    private static boolean check(List<NodeData> cities){
        for (NodeData city : cities){
            if (city.getTag() == 0){
                return true;
            }
        }
        return false;
    }
}
