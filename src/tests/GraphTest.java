package tests;

import api.Edge;
import api.GeoPoint;
import api.Graph;
import api.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private static Graph g;

    @BeforeEach
    void init(){
       g = new Graph("data/G3.json");
    }

    @Test
    void copy() {
        Graph copy = new Graph(g);
        assert (copy.hashCode() != g.hashCode());
    }

    @org.junit.jupiter.api.Test
    void getNode() {
        for (int i = 0; i < g.nodeSize(); ++i){
            Node node = (Node) g.getNode(i);
            Node test = new Node(i);
            test.setPos(node.getPos());
            test.parsePosition();

            GeoPoint p_node = node.getLocation();
            GeoPoint p_test = test.getLocation();

            assert(node.getKey() == i);

            assert(p_node.x() == p_test.x());
            assert(p_node.y() == p_test.y());
            assert(p_node.z() == p_test.z());
        }
    }

    @org.junit.jupiter.api.Test
    void getEdge() {
        for (int i = 0; i < g.nodeSize(); ++i){
            for (int j = 0; j < g.nodeSize(); ++j){
                Edge edge = (Edge) g.getEdge(i, j);

                boolean flag = false;
                if (edge != null) {
                     flag = edge.getSrc() == i && edge.getDest() == j;
                }

                assert (edge == null || flag);
            }
        }
    }

    @org.junit.jupiter.api.Test
    void addNode() {
        int nodeSize = g.nodeSize();
        GeoPoint point = new GeoPoint(10, 12, 0);

        Node node = new Node(nodeSize, point);

        g.addNode(node);

        assert (g.nodeSize() == nodeSize + 1);
    }

    @org.junit.jupiter.api.Test
    void connect() {
        g.connect(0,2, 3.2);

        Edge edge = (Edge) g.getEdge(0, 2);

        assert (edge != null);
        assert (edge.getW() == 3.2);
    }

    @org.junit.jupiter.api.Test
    void removeNode() { // might need to fix this test
        for (int i = 0; i < g.nodeSize(); ++i) {
            int nodeSize = g.nodeSize();
            int edgeSize = g.edgeSize();

            Node rem = (Node) g.removeNode(i);

            assert (g.nodeSize() == nodeSize - 1);
            assert (g.edgeSize() == edgeSize - (rem.outSize() + rem.inSize()));
        }
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
        int edgeSize = g.edgeSize();

        Edge edge = (Edge) g.removeEdge(0, 1);

        assert (g.edgeSize() == edgeSize - 1);
        assert (edge.getSrc() == 0 && edge.getDest() == 1);
    }

    @Test
    void getTranspose(){
        Graph g = new Graph("data/CustomGraph.json");

        Graph gTrans = new Graph(g);
        gTrans = gTrans.getTranspose();

        System.out.println("Done!");
    }
}