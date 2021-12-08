package api;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Toolkit;

public class Graph_UI extends JFrame{
	
	private final DirectedWeightedGraphAlgorithms algo;
	private final DirectedWeightedGraph graph;
	
	public static final int HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3);
	public static final int WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3); 
	
	public Graph_UI(DirectedWeightedGraphAlgorithms algo) {
		this.setTitle("GUI");
		this.setLayout(new BorderLayout());
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.algo = algo;
        graph = algo.getGraph();
		
        this.add(new Nodes_UI(this.algo), BorderLayout.CENTER);
        setVisible(true);
        this.add(new Edges_UI(this.algo), BorderLayout.CENTER);
        setVisible(true);
        setResizable(true);
	 }
	 
	 public static void main(String[] args) {
		 	Graph g = new Graph("data/G3.json");
	        GraphAlgo algo = new GraphAlgo();
	        algo.init(g);
	        
	        Graph_UI test = new Graph_UI(algo);
	 }
}
