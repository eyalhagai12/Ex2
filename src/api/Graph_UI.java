package api;

import java.awt.event.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;


import java.awt.BorderLayout;
import java.awt.Toolkit;

public class Graph_UI extends JFrame {

    private final DirectedWeightedGraphAlgorithms algo;
    private final DirectedWeightedGraph graph;
    private final Nodes_UI nodes_ui;
    private final Edges_UI edges_ui;
    private Menu_UI menu;

    private static boolean flag = true;
    public static int nodeCounter = 0;
    public static int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
    public static int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);

    public Graph_UI(DirectedWeightedGraphAlgorithms algo) {
        this.setTitle("GUI");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.algo = algo;
        graph = algo.getGraph();

        if (flag) {
            nodeCounter = graph.nodeSize();
            flag = false;
        }

        this.nodes_ui = new Nodes_UI(this.algo, this);
        this.edges_ui = new Edges_UI(this.algo, this);
        menu = new Menu_UI(this);
        addMouseListener(menu);

        this.add(nodes_ui, BorderLayout.CENTER);
        //setVisible(true);
        this.add(edges_ui, BorderLayout.CENTER);
        //setVisible(true);
        setJMenuBar(menu.getMenuBar());
        //setVisible(true);
        
        setResizable(true);

        
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                nodes_ui.repaint();
                edges_ui.repaint();
        }});
    }

    public DirectedWeightedGraphAlgorithms getAlgo() {return algo;}
    
    public void resetFrame(int width, int height) {
    	WIDTH = width;
    	HEIGHT = height;
    	setSize(WIDTH,HEIGHT);
    	setLocationRelativeTo(null);
    	if(WIDTH == Toolkit.getDefaultToolkit().getScreenSize().getWidth() || HEIGHT == Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
    		setExtendedState(JFrame.MAXIMIZED_BOTH);
    	}
    	setVisible(true);
    }

    public static void main(String[] args) {
        Graph g = new Graph("data/G1.json");
        GraphAlgo algo = new GraphAlgo();
        algo.init(g);

        Graph_UI test = new Graph_UI(algo);
        test.setVisible(true);
    }
}
