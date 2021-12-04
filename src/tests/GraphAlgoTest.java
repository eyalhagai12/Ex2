package tests;

import api.Graph;
import api.GraphAlgo;
import api.Node;
import api.NodeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgoTest {
    static GraphAlgo[] algo;
    static Graph[] graphs;

    @BeforeEach
    void init_test() {
        algo = new GraphAlgo[4];
        graphs = new Graph[4];
        algo[0] = new GraphAlgo();
        algo[1] = new GraphAlgo();
        algo[2] = new GraphAlgo();
        algo[3] = new GraphAlgo();

        graphs[0] = new Graph("data/G1.json");
        graphs[1] = new Graph("data/G2.json");
        graphs[2] = new Graph("data/G3.json");
        graphs[3] = new Graph("data/CustomGraph.json");

        algo[0].init(graphs[0]);
        algo[1].init(graphs[1]);
        algo[2].init(graphs[2]);
        algo[3].init(graphs[3]);
    }

    @Test
    void topologicalSort() {
        LinkedList<NodeData> list1 = algo[0].TopologicalSort(graphs[0]);
        LinkedList<NodeData> list2 = algo[1].TopologicalSort(graphs[1]);
        LinkedList<NodeData> list3 = algo[2].TopologicalSort(graphs[2]);
        LinkedList<NodeData> list4 = algo[3].TopologicalSort(graphs[3]);

        assert (isSorted(list1));
        assert (isSorted(list2));
        assert (isSorted(list3));
        assert (isSorted(list4));
    }

    boolean isSorted(LinkedList<NodeData> list) {
        for (int i = 1; i < list.size(); ++i) {
            Node current = (Node) list.get(i);
            Node previous = (Node) list.get(i - 1);
            if (current.getFinishTime() > previous.getFinishTime()){
                return false;
            }
        }

        return true;
    }

    @Test
    void DFS() {
    }

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void isConnected() {
        assert (algo[0].isConnected());
        assert(algo[1].isConnected());
        assert(algo[2].isConnected());
        assert(!algo[3].isConnected());
    }
}