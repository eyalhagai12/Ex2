package api;

import javax.swing.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is for handling UI and presentation
 */
public class GraphUI extends JFrame {
    private final DirectedWeightedGraphAlgorithms algo;
    private final DirectedWeightedGraph graph;
    private List<NodeUI> node_list;
    private int width;
    private int height;
    private double[] b1;

    /**
     * A constructor that gets a "DirectedWeightedGraphAlgorithms" object
     * and creates a GraphsUI object from it
     *
     * @param algo The algorithm object to create out UI on
     */
    public GraphUI(DirectedWeightedGraphAlgorithms algo) {
        // init fields
        node_list = new LinkedList<>();
        this.algo = algo;
        graph = algo.getGraph();
        b1 = new double[2];

        width = 600;
        height = 400;

        // define size of window
        this.setSize(width, height);

        // add the nodes to the list
        Iterator<NodeData> nodes = graph.nodeIter();
        double[] max = new double[2];
        double[] min = new double[]{Double.MAX_VALUE, Double.MAX_VALUE};

        while (nodes.hasNext()) {
            // get node and its point
            NodeData node = nodes.next();
            GeoLocation p = node.getLocation();

            // save for transformation
            if (p.x() > max[0]) {
                max[0] = p.x();
            }
            if (p.y() > max[1]) {
                max[1] = p.x();
            }
            if (p.x() < min[0]) {
                min[0] = p.x();
            }
            if (p.y() < min[1]) {
                min[1] = p.y();
            }
        }

        // calculate the transformation
        calculateTransformation(max, min);

        // Transform nodes and add to the list
        // add the nodes to the list
        nodes = graph.nodeIter();

        while (nodes.hasNext()) {
            // get node and its point
            NodeData node = nodes.next();
            GeoLocation p = node.getLocation();

            // transform location and create UI object
            NodeUI node_ui = new NodeUI(node, 20, width, height);
            node_ui.setPoint(transform(p, max));

            // save and draw UI object
            node_list.add(node_ui);
            this.add(node_ui);
        }

        this.setVisible(true);
    }

    private void calculateTransformation(double[] max, double[] min) {
        double diff_x = max[0] - min[0];
        double diff_y = max[1] - min[1];

        b1[0] = width / diff_x;
        b1[1] = height / diff_y;
    }

    private GeoLocation transform(GeoLocation location, double[] max) {
        double old_x = max[0] - location.x();
        double old_y = max[1] - location.y();

        double x = old_x * b1[0];
        double y = old_y * b1[1];
        return new GeoPoint(x, y, 0.0);
    }

}
