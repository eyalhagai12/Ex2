package api;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class TempFrame_UI extends JFrame implements ActionListener {

    private Graph_UI frame;

    JTextField tf;
    JLabel l;
    JButton b;
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

        b = new JButton("Find Path");
        b.setBounds(100, 90, 110, 30);

        l = new JLabel("<html>Enter source node and destination</br> as follows: key1,key2</html>");
        l.setBounds(10, 25, 300, 30);

        add(b);
        add(tf);
        add(l);

        b.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b) {
            int src = Integer.parseInt(tf.getText().split(",")[0]);
            int dst = Integer.parseInt(tf.getText().split(",")[1]);
            path = "";
            List<NodeData> list = frame.getAlgo().shortestPath(src, dst);
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    path += "" + list.get(i).getKey() + " -> ";
                } else path += "" + list.get(i).getKey();
            }
            dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            JFrame show = new TempFrame_UI(frame);
            JLabel x = new JLabel(path);
            x.setBounds(10, 60, 1000, 20);
            x.setVisible(true);
            show.add(x);
            show.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            show.setVisible(true);
        }
    }

}
