package api;

import javax.swing.*;
import java.awt.*;

public class NodeUI extends JComponent {
    private GeoLocation point;
    private int radius;
    private int width;
    private int height;

    public NodeUI(NodeData node, int radius, int width, int height) {
        point = node.getLocation();
        this.radius = radius;
        this.width = width;
        this.height = height;
    }

    public void setPoint(GeoLocation point){
        this.point = point;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        int x = (int) point.x() - radius;
        int y = (int) point.y() - radius;
        g.fillOval(x, y, (int) radius, radius);
    }
}
