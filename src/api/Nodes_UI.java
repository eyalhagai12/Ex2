package api;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.awt.Color;

public class Nodes_UI extends JComponent {
	
	private final DirectedWeightedGraphAlgorithms algo;
	private final DirectedWeightedGraph graph;
	private Iterator<NodeData> iterator;
	
	private static final int HEIGHT = Graph_UI.HEIGHT;
	private static final int WIDTH = Graph_UI.WIDTH;
	
	public static double Xmax = Double.MIN_VALUE;  public static double Ymax = Double.MIN_VALUE;
	public static double Xmin = Double.MAX_VALUE;  public static double Ymin = Double.MAX_VALUE;
	
	public Nodes_UI(DirectedWeightedGraphAlgorithms algo) {
        this.algo = algo;
        graph = algo.getGraph();
        iterator = graph.nodeIter();
		
		while (iterator.hasNext()) {
			NodeData node = iterator.next();
			
			if(Xmax < node.getLocation().x()) {
				Xmax = node.getLocation().x();
			}
			if(Ymax < node.getLocation().y()) {
				Ymax = node.getLocation().y();
			}
			if(Xmin > node.getLocation().x()) {
				Xmin = node.getLocation().x();
			}
	        if(Ymin > node.getLocation().y()) {
	        	Ymin = node.getLocation().y();
	        }
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   Graphics2D g2d = (Graphics2D)g;
	   g2d.setPaint(Color.RED);
	   
	   iterator = graph.nodeIter();
	   while(iterator.hasNext()) {
		   NodeData node = iterator.next();
			// Linear map the point
			double x = node.getLocation().x()-Xmin;
			double y = node.getLocation().y()-Ymin;
		   g2d.fillOval((int)((x/(Xmax-Xmin))*WIDTH*0.8)+(int)(0.08*WIDTH),(int)((y/(Ymax-Ymin))*HEIGHT*0.8),10,10);
	   }
	}

}
