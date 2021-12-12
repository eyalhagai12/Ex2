package api;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

public class TempFrame_UI extends JFrame implements ActionListener {

    private Graph_UI frame;

    JTextField tf;
    JLabel l;
    JButton b1;
    JButton b3;
    JButton b5;
    JButton b2;
    JButton b4;
    JButton b6;
    String path;
    Dimension frameSize;

    public TempFrame_UI(Graph_UI g) {

        frame = g;
        frameSize = frame.getContentPane().getSize();

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
        b3 = new JButton("OK!");
        b3.setBounds(100, 90, 110, 30);

        l = new JLabel("<html>Press on a location at the screen to add a new node</html>");
        l.setBounds(10, 25, 300, 30);

        add(b3);
        add(l);

        b3.addActionListener(this);
    }

    public void addEdge() {
        tf = new JTextField();
        tf.setBounds(105, 80, 100, 20);

        b4 = new JButton("Add edge");
        b4.setBounds(100, 110, 110, 30);

        l = new JLabel("<html>Enter edge's id and source node, destination node </br>as follows: src,dst,weight</html>");
        l.setBounds(10, 25, 300, 45);

        add(b4);
        add(tf);
        add(l);

        b4.addActionListener(this);
    }

    public void removeNode() {
        tf = new JTextField();
        tf.setBounds(105, 80, 100, 20);

        b5 = new JButton("Remove node");
        b5.setBounds(80, 110, 150, 30);

        l = new JLabel("<html>Enter node's id that you want to delete:</html>");
        l.setBounds(10, 25, 300, 45);

        add(b5);
        add(tf);
        add(l);

        b5.addActionListener(this);
    }

    public void removeEdge() {
        tf = new JTextField();
        tf.setBounds(105, 80, 100, 20);

        b6 = new JButton("Remove edge");
        b6.setBounds(80, 110, 150, 30);

        l = new JLabel("<html>Enter edge's source and destination</br> to delete:</html>");
        l.setBounds(10, 25, 300, 45);

        add(b6);
        add(tf);
        add(l);

        b6.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) { // shortest path
            if (!tf.getText().contains(",") || tf.getText().split(",").length != 2 || !validate(tf.getText().split(","))) {
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                l.setText("<html>Invalid Input. Enter: key1,key2</html>");
                setVisible(true);
            } else {
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
                JTextArea x = new JTextArea(path + " | distance is: " + dist);
                // code from https://stackoverflow.com/questions/26420428/how-to-word-wrap-text-in-jlabel
                x.setWrapStyleWord(true);
                x.setLineWrap(true);
                x.setBounds(10, 50, 200, 200);
                x.setOpaque(false);
                x.setEditable(false);
                x.setFocusable(false);
                x.setBackground(UIManager.getColor("Label.background")); //
                x.setFont(UIManager.getFont("Label.font"));
                x.setBorder(UIManager.getBorder("Label.border"));
                x.setVisible(true);
                show.add(x);
                show.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                show.setVisible(true);
            }
        }

        if (e.getSource() == b2) { // tsp
            if (!tf.getText().contains(",") || !validate(tf.getText().split(","))) {
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                l.setText("<html>Invalid Input. Enter: key1,key2...</html>");
                setVisible(true);
            } else {
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
                x.setWrapStyleWord(true);
                x.setLineWrap(true);
                x.setBounds(10, 50, 200, 200);
                x.setOpaque(false);
                x.setEditable(false);
                x.setFocusable(false);
                x.setBackground(UIManager.getColor("Label.background")); //
                x.setFont(UIManager.getFont("Label.font"));
                x.setBorder(UIManager.getBorder("Label.border"));
                x.setVisible(true);
                show.add(x);
                show.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                show.setVisible(true);
            }
        }

        if (e.getSource() == b3) { // add node
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }

        if (e.getSource() == b4) { // add edge
            if (!tf.getText().contains(",") || tf.getText().split(",").length != 3 || !validate(tf.getText().split(","))) {
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                l.setText("<html>Invalid Input. Enter: src,dest,weight</html>");
                setVisible(true);
            } else {
                int src = Integer.parseInt(tf.getText().split(",")[0]);
                int dst = Integer.parseInt(tf.getText().split(",")[1]);
                double weight = Double.parseDouble(tf.getText().split(",")[2]);
                Graph g = (Graph) frame.getAlgo().copy();
                g.connect(src, dst, weight);
                DirectedWeightedGraphAlgorithms a = new GraphAlgo();
                a.init(g);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Graph_UI test = new Graph_UI(a);
                test.resetFrame((int) frameSize.getWidth(), (int) frameSize.getHeight());
            }
        }

        if (e.getSource() == b5) { // remove node
            if (!validate(tf.getText())) {
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                l.setText("<html>Invalid Input. Enter: id</html>");
                setVisible(true);
            } else {
                int id = Integer.parseInt(tf.getText().split(",")[0]);
                DirectedWeightedGraph g = frame.getAlgo().copy();
                g.removeNode(id);
                DirectedWeightedGraphAlgorithms a = new GraphAlgo();
                a.init(g);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Graph_UI test = new Graph_UI(a);
                test.resetFrame((int) frameSize.getWidth(), (int) frameSize.getHeight());
            }
        }

        if (e.getSource() == b6) { // remove edge
            if (!tf.getText().contains(",") || tf.getText().split(",").length != 2 || !validate(tf.getText().split(","))) {
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                l.setText("<html>Invalid Input. Enter: src,dest</html>");
                setVisible(true);
            } else {
                int src = Integer.parseInt(tf.getText().split(",")[0]);
                int dst = Integer.parseInt(tf.getText().split(",")[1]);
                Graph g = (Graph) frame.getAlgo().copy();
                g.removeEdge(src, dst);
                DirectedWeightedGraphAlgorithms a = new GraphAlgo();
                a.init(g);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Graph_UI test = new Graph_UI(a);
                test.resetFrame((int) frameSize.getWidth(), (int) frameSize.getHeight());
            }
        }
    }

    public boolean validate(String[] str) {
        for (int i = 0; i < str.length; i++) {
            try {
                double d = Double.parseDouble(str[i]);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return true;
    }

    public boolean validate(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
