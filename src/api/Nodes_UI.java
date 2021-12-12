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
    private final Graph_UI graph_ui;

    private int HEIGHT;
    private int WIDTH;

    public static double Xmax = Double.MIN_VALUE;
    public static double Ymax = Double.MIN_VALUE;
    public static double Xmin = Double.MAX_VALUE;
    public static double Ymin = Double.MAX_VALUE;

    public Nodes_UI(DirectedWeightedGraphAlgorithms algo, Graph_UI graph_ui) {
        this.algo = algo;
        graph = algo.getGraph();
        this.graph_ui = graph_ui;
        WIDTH = graph_ui.getWidth();
        HEIGHT = graph_ui.getHeight();
        getCoordBounds();
        setBounds(0, 0, WIDTH, HEIGHT);
    }

    private void getCoordBounds() {
        iterator = graph.nodeIter();

        while (iterator.hasNext()) {
            NodeData node = iterator.next();

            if (Xmax < node.getLocation().x()) {
                Xmax = node.getLocation().x();
            }
            if (Ymax < node.getLocation().y()) {
                Ymax = node.getLocation().y();
            }
            if (Xmin > node.getLocation().x()) {
                Xmin = node.getLocation().x();
            }
            if (Ymin > node.getLocation().y()) {
                Ymin = node.getLocation().y();
            }
        }
    }

    private void updateSizes() {
        WIDTH = graph_ui.getWidth();
        HEIGHT = graph_ui.getHeight();
        setBounds(0, 0, WIDTH, HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateSizes();
        Graphics2D g2d = (Graphics2D) g;

        iterator = graph.nodeIter();
        while (iterator.hasNext()) {
            NodeData node = iterator.next();
            // Linearly map the point
            double x = node.getLocation().x() - Xmin;
            double y = node.getLocation().y() - Ymin;

            double x_coord = ((x / (Xmax - Xmin)) * WIDTH * 0.8) + (0.08 * WIDTH);
            double y_coord = ((y / (Ymax - Ymin)) * HEIGHT * 0.8) + 5;

//            double x_test = (((x_coord - (0.08 * WIDTH)) * (Xmax - Xmin)) / (WIDTH * 0.8));
//            System.out.println("--------------------------");
//            System.out.println("x = " + x);
//            System.out.println("x test = " + x_test);
//            System.out.println(x_test == x);


            g2d.setPaint(Color.RED);
            g2d.fillOval((int) x_coord, (int) y_coord, 20, 20);
            g2d.setPaint(Color.BLUE);
            g2d.drawString("" + node.getKey(), (int) x_coord, (int) y_coord);
        }
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

}
