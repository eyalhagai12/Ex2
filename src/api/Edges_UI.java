package api;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

public class Edges_UI extends JComponent {
    private final DirectedWeightedGraphAlgorithms algo;
    private final DirectedWeightedGraph graph;
    private Iterator<EdgeData> iterator;
    private Graph_UI graph_ui;
    private int HEIGHT;
    private int WIDTH;

    private double Xmax = Nodes_UI.Xmax;
    private double Ymax = Nodes_UI.Ymax;
    private double Xmin = Nodes_UI.Xmin;
    private double Ymin = Nodes_UI.Ymin;

    public Edges_UI(DirectedWeightedGraphAlgorithms algo, Graph_UI graph_ui) {
        this.algo = algo;
        graph = algo.getGraph();
        iterator = graph.edgeIter();
        this.graph_ui = graph_ui;

        WIDTH = graph_ui.getWidth();
        HEIGHT = graph_ui.getHeight();
    }

    private void updateSizes() {
        WIDTH = graph_ui.getWidth();
        HEIGHT = graph_ui.getHeight();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateSizes();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.BLACK);
        
        iterator = graph.edgeIter();
    	while (iterator.hasNext()) {
            EdgeData edge = iterator.next();

            double x1 = graph.getNode(edge.getSrc()).getLocation().x() - Xmin;
            double x2 = graph.getNode(edge.getDest()).getLocation().x() - Xmin;
            double y1 = graph.getNode(edge.getSrc()).getLocation().y() - Ymin;
            double y2 = graph.getNode(edge.getDest()).getLocation().y() - Ymin;

            x1 = (int) ((x1 / (Xmax - Xmin)) * WIDTH * 0.8) + (int) (0.08 * WIDTH) + 7;
            x2 = (int) ((x2 / (Xmax - Xmin)) * WIDTH * 0.8) + (int) (0.08 * WIDTH) + 7;
            y1 = (int) ((y1 / (Ymax - Ymin)) * HEIGHT * 0.8) + 15;
            y2 = (int) ((y2 / (Ymax - Ymin)) * HEIGHT * 0.8) + 15;

            Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
            String weightStr = String.format("%.3f", edge.getWeight());
            g2d.drawString(weightStr, (int) (x1 + x2) / 2, (int) (y1 + y2) / 2);
            
            int h = 15, d = 15; // h = height of arrow, d = width of arrow
            // code from https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
            
            int dx = (int)(x2 - x1), dy = (int)(y2 - y1);
            double D = Math.sqrt(dx*dx + dy*dy);
            double xm = D - 15, xn = xm, ym = 15, yn = -15, x;
            double sin = dy / D, cos = dx / D;

            x = xm*cos - ym*sin + x1;
            ym = xm*sin + ym*cos + y1;
            xm = x;

            x = xn*cos - yn*sin + x1;
            yn = xn*sin + yn*cos + y1;
            xn = x;

            int[] xpoints = {(int) x2, (int) xm, (int) xn};
            int[] ypoints = {(int) y2, (int) ym, (int) yn};

            g2d.draw(line);
            g.fillPolygon(xpoints, ypoints, 3);
    	}
    }
}
