package api;

import java.util.Iterator;

public class DjikstraThread extends Thread{

	private Graph graph;
	private double[] weights;
	private int startIndex;
	private int endIndex;
	
	public DjikstraThread(Graph g, double[] w, int s, int e) {
		graph = g;
		weights = w;
		startIndex = s;
		endIndex = e;
	}
	
	@Override
	public void run() {
		Iterator<NodeData> iterator = graph.nodeIter();
		for (int i = startIndex; i > 0; i--) {
			iterator.next();
		}
		for (int i = startIndex; i < endIndex; i++) {
			weights[i] = utils.Dijkstra(graph, iterator.next());
		}
		
//		for (int i = startIndex; i < endIndex; i++) {
//			weights[i] = utils.Dijkstra(graph, graph.getNode(i));
//		}
	}
}
