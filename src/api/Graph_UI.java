package api;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Toolkit;

public class Graph_UI extends JFrame {

    private final DirectedWeightedGraphAlgorithms algo;
    private final DirectedWeightedGraph graph;
    private final Nodes_UI nodes_ui;
    private final Edges_UI edge_ui;

    public static final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3);
    public static final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3);

    public Graph_UI(DirectedWeightedGraphAlgorithms algo) {
        this.setTitle("GUI");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.algo = algo;
        graph = algo.getGraph();

        this.nodes_ui = new Nodes_UI(this.algo, this);
        this.edge_ui = new Edges_UI(this.algo, this);

        this.add(nodes_ui, BorderLayout.CENTER);
        setVisible(true);

        this.add(edge_ui, BorderLayout.CENTER);
        setVisible(true);
        setResizable(true);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                nodes_ui.repaint();
                edge_ui.repaint();
            }
        });

    }

    public static void main(String[] args) {
        Graph g = new Graph("data/G2.json");
        GraphAlgo algo = new GraphAlgo();
        algo.init(g);

        Graph_UI test = new Graph_UI(algo);
    }
}
