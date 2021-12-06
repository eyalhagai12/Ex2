package api;

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
		for (int i = startIndex; i < endIndex; i++) {
			weights[i] = utils.Dijkstra(graph, graph.getNode(i));
		}
	}
}
