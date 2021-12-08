package api;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.awt.Color;

public class Edges_UI extends JComponent{
	private final DirectedWeightedGraphAlgorithms algo;
	private final DirectedWeightedGraph graph;
	private Iterator<EdgeData> iterator;
	
	private static final int HEIGHT = Graph_UI.HEIGHT;
	private static final int WIDTH = Graph_UI.WIDTH;
	
	private double Xmax = Nodes_UI.Xmax;  private double Ymax = Nodes_UI.Ymax;
	private double Xmin = Nodes_UI.Xmin;  private double Ymin = Nodes_UI.Ymin;
	
	public Edges_UI(DirectedWeightedGraphAlgorithms algo) {
		this.algo = algo;
        graph = algo.getGraph();
        iterator = graph.edgeIter();
		
//		while (iterator.hasNext()) {
//			EdgeData edge = iterator.next();
//			
//			NodeData src = graph.getNode(edge.getSrc());
//			NodeData dst = graph.getNode(edge.getDest());
//			
//			if(Xmax < src.getLocation().x()) {
//				Xmax = src.getLocation().x();
//			}
//			if(Xmax < dst.getLocation().x()) {
//				Xmax = dst.getLocation().x();
//			}
//			if(Ymax < src.getLocation().y()) {
//				Ymax = src.getLocation().y();
//			}
//			if(Ymax < dst.getLocation().y()) {
//				Ymax = dst.getLocation().y();
//			}
//			if(Xmin > src.getLocation().x()) {
//				Xmin = src.getLocation().x();
//			}
//			if(Xmin > dst.getLocation().x()) {
//				Xmin = dst.getLocation().x();
//			}
//			if(Ymin > src.getLocation().y()) {
//				Ymin = src.getLocation().y();
//			}
//			if(Ymin > dst.getLocation().y()) {
//				Ymin = dst.getLocation().y();
//			}
//		}
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		   Graphics2D g2d = (Graphics2D)g;
		   g2d.setPaint(Color.BLACK);
		   
		   iterator = graph.edgeIter();
		   while(iterator.hasNext()) {
			   EdgeData edge = iterator.next();
			   
			   double x1 = graph.getNode(edge.getSrc()).getLocation().x() - Xmin;
			   double x2 = graph.getNode(edge.getDest()).getLocation().x() - Xmin;
			   double y1 = graph.getNode(edge.getSrc()).getLocation().y() - Ymin;
			   double y2 = graph.getNode(edge.getDest()).getLocation().y() - Ymin;
			   
			   x1 = (int)((x1/(Xmax-Xmin))*WIDTH*0.8)+(int)(0.08*WIDTH)+5;
			   x2 = (int)((x2/(Xmax-Xmin))*WIDTH*0.8)+(int)(0.08*WIDTH)+5;
			   y1 = (int)((y1/(Ymax-Ymin))*HEIGHT*0.8)+5;
			   y2 = (int)((y2/(Ymax-Ymin))*HEIGHT*0.8)+5;
			   
			   g2d.draw(new Line2D.Double(x1,y1,x2,y2));
		   }
		   
		   // draw point
//		   double x = point.x();
//		   double y = point.y();
//		   g2d.draw(new Line2D.Double(x,y,x,y));
	}
}
