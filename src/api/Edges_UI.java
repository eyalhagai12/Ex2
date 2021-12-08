package api;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.awt.Color;

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

            x1 = (int) ((x1 / (Xmax - Xmin)) * WIDTH * 0.8) + (int) (0.08 * WIDTH) + 5;
            x2 = (int) ((x2 / (Xmax - Xmin)) * WIDTH * 0.8) + (int) (0.08 * WIDTH) + 5;
            y1 = (int) ((y1 / (Ymax - Ymin)) * HEIGHT * 0.8) + 5;
            y2 = (int) ((y2 / (Ymax - Ymin)) * HEIGHT * 0.8) + 5;

            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            String weight = String.format("%.2f", edge.getWeight());
            g2d.drawString(weight, (int) (x1 + x2) / 2, (int) ((y1 + y2) / 2) + 20);
        }

    }
}
