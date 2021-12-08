package api;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu_UI implements ActionListener {

    JMenuBar mb;
    JMenu file, edit, nodes, edges, operations;
    JMenuItem f1, f2, e1, e2, e3, e4, o1, o2, o3, o4;

    private DirectedWeightedGraphAlgorithms algo;
    private Graph_UI frame;

    public Menu_UI(Graph_UI g) {
        frame = g;
        algo = g.getAlgo();

        mb = new JMenuBar();
        file = new JMenu("File");
        f1 = new JMenuItem("Save");
        f2 = new JMenuItem("Load");
        file.add(f1);
        file.add(f2);

        edit = new JMenu("Edit");
        nodes = new JMenu("Nodes");
        edges = new JMenu("Edges");
        e1 = new JMenuItem("Add Node");
        e2 = new JMenuItem("Add Edge");
        e3 = new JMenuItem("Remove Node");
        e4 = new JMenuItem("Remove Edge");
        edit.add(nodes);
        edit.add(edges);
        nodes.add(e1);
        edges.add(e2);
        nodes.add(e3);
        edges.add(e4);

        operations = new JMenu("Operations");
        o1 = new JMenuItem("Is Connected");
        o2 = new JMenuItem("Shortest Path");
        o3 = new JMenuItem("Center");
        o4 = new JMenuItem("TSP");
        operations.add(o1);
        operations.add(o2);
        operations.add(o3);
        operations.add(o4);

        f1.addActionListener(this);
        f2.addActionListener(this);

        e1.addActionListener(this);
        e2.addActionListener(this);
        e3.addActionListener(this);
        e4.addActionListener(this);

        o1.addActionListener(this);
        o2.addActionListener(this);
        o3.addActionListener(this);
        o4.addActionListener(this);

        mb.add(file);
        mb.add(edit);
        mb.add(operations);
    }

    public JMenuBar getMenuBar() {
        return mb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        TempFrame_UI temp = new TempFrame_UI(frame);

        if (source == f1) { // save
            temp.setTitle("Save");
            JLabel l = new JLabel("Saved at \"saved_graphs/SavedGraph.json\"");
            l.setBounds(30, 50, 600, 60);
            temp.setBounds(100, 100, 400, 200);
            temp.add(l);
            algo.save("SavedGraph.json");
            temp.setVisible(true);
        }
        if (source == f2) // load
            System.out.println("TEST");


        if (source == e1) // add node
            System.out.println("TEST");

        if (source == e2) // add edge
            System.out.println("TEST");

        if (source == e3) // remove node
            System.out.println("TEST");

        if (source == e4) // remove edge
            System.out.println("TEST");


        if (source == o1) { // connectivity
            temp.setTitle("Is Connected");
            JLabel l = new JLabel(algo.isConnected() ? "TRUE" : "FALSE");
            l.setBounds(130, 60, 100, 30);
            temp.add(l);
            temp.setVisible(true);
        }

        if (source == o2) // shortest path
        {
            temp.setTitle("Shortest Path");
            temp.shortestPath();
            temp.setVisible(true);
        }

        if (source == o3) { // center
            temp.setTitle("Center");
            JLabel l = new JLabel("Center is: " + algo.center().getKey());
            l.setBounds(110, 60, 100, 30);
            temp.add(l);
            temp.setVisible(true);
        }

        if (source == o4) {// tsp
            temp.setTitle("TSP");
        }
    }
}
