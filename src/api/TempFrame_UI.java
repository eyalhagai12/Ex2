package api;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

public class TempFrame_UI extends JFrame implements ActionListener {

    private Graph_UI frame;

    JTextField tf;
    JLabel l;
    JButton b1; JButton b3;
    JButton b2; JButton b4;
    String path;

    public TempFrame_UI(Graph_UI g) {

        frame = g;

        setSize(300, 200);
        setLocationRelativeTo(frame);
        setResizable(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void shortestPath() {
        tf = new JTextField();
        tf.setBounds(105, 60, 100, 20);

        b1 = new JButton("Find Path");
        b1.setBounds(100, 90, 110, 30);

        l = new JLabel("<html>Enter source node and destination</br> as follows: key1,key2</html>");
        l.setBounds(10, 25, 300, 30);

        add(b1);
        add(tf);
        add(l);

        b1.addActionListener(this);
    }
    
    public void tsp() {
        tf = new JTextField();
        tf.setBounds(105, 60, 100, 20);

        b2 = new JButton("Find Path");
        b2.setBounds(100, 90, 110, 30);

        l = new JLabel("<html>Enter nodes' keys as follows:</br> key1,key2,key3,key4....</html>");
        l.setBounds(10, 25, 300, 30);

        add(b2);
        add(tf);
        add(l);

        b2.addActionListener(this);
    }
    
    public void addNode() {
    	tf = new JTextField();
        tf.setBounds(105, 60, 100, 20);

        b3 = new JButton("Add node");
        b3.setBounds(100, 90, 110, 30);

        l = new JLabel("<html>Enter node's id and coordinates as follows:</br> id,x,y</html>");
        l.setBounds(10, 25, 300, 30);

        add(b3);
        add(tf);
        add(l);

        b3.addActionListener(this);
    }
    
    public void addEdge() {
    	tf = new JTextField();
        tf.setBounds(105, 60, 100, 20);

        b4 = new JButton("Add edge");
        b4.setBounds(100, 90, 110, 30);

        l = new JLabel("<html>Enter edge's id and source node, destination node </br>as follows: src,dst,id</html>");
        l.setBounds(10, 25, 300, 30);

        add(b4);
        add(tf);
        add(l);

        b4.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) { // shortest path
            int src = Integer.parseInt(tf.getText().split(",")[0]);
            int dst = Integer.parseInt(tf.getText().split(",")[1]);
            double dist = frame.getAlgo().shortestPathDist(src, dst);
            path = "";
            List<NodeData> list = frame.getAlgo().shortestPath(src, dst);
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    path += "" + list.get(i).getKey() + " -> ";
                } else path += "" + list.get(i).getKey();
            }
            dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            JFrame show = new TempFrame_UI(frame);
            JTextArea x = new JTextArea(path+" | distance is: "+dist);
            // code from https://stackoverflow.com/questions/26420428/how-to-word-wrap-text-in-jlabel
            x.setWrapStyleWord(true); x.setLineWrap(true);
            x.setBounds(10, 50, 200, 200); x.setOpaque(false);
            x.setEditable(false);x.setFocusable(false);
            x.setBackground(UIManager.getColor("Label.background")); //
            x.setFont(UIManager.getFont("Label.font"));
            x.setBorder(UIManager.getBorder("Label.border"));
            x.setVisible(true);
            show.add(x);
            show.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            show.setVisible(true);
        }
        
        if (e.getSource() == b2) { // tsp
        	List<NodeData> cities = new LinkedList<NodeData>();
        	for (int i = 0; i < tf.getText().split(",").length; i++) {
        		int key = Integer.parseInt(tf.getText().split(",")[i]);
				cities.add(frame.getAlgo().getGraph().getNode(key));
			}
            path = "";
            List<NodeData> list = frame.getAlgo().tsp(cities);
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    path += "" + list.get(i).getKey() + " -> ";
                } else path += "" + list.get(i).getKey();
            }
            dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            JFrame show = new TempFrame_UI(frame);
            JTextArea x = new JTextArea(path);
            // code from https://stackoverflow.com/questions/26420428/how-to-word-wrap-text-in-jlabel
            x.setWrapStyleWord(true); x.setLineWrap(true);
            x.setBounds(10, 50, 200, 200); x.setOpaque(false);
            x.setEditable(false);x.setFocusable(false);
            x.setBackground(UIManager.getColor("Label.background")); //
            x.setFont(UIManager.getFont("Label.font"));
            x.setBorder(UIManager.getBorder("Label.border"));
            x.setVisible(true);
            show.add(x);
            show.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            show.setVisible(true);
        }
        
        if(e.getSource()==b3) { // add node
        	int id = Integer.parseInt(tf.getText().split(",")[0]);
        	double x = Double.parseDouble(tf.getText().split(",")[1]);
        	double y = Double.parseDouble(tf.getText().split(",")[2]);
        	NodeData node = new Node(id, new GeoPoint(x, y,0));
        	DirectedWeightedGraph g = frame.getAlgo().copy();
        	g.addNode(node);
        	DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        	a.init(g);
        	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        	new Graph_UI(a);
        }
        
        if(e.getSource()==b4) { // add edge
        	int src = Integer.parseInt(tf.getText().split(",")[0]);
        	int dst = Integer.parseInt(tf.getText().split(",")[1]);
        	double weight = Double.parseDouble(tf.getText().split(",")[2]);
        	Graph g = (Graph)frame.getAlgo().copy();
        	g.connect(src,dst,weight);
        	DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        	a.init(g);
        	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        	new Graph_UI(a);
        }
    }

}
