package tests;

import api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
        LinkedList<NodeData> list1 = utils.TopologicalSort(graphs[0]);
        LinkedList<NodeData> list2 = utils.TopologicalSort(graphs[1]);
        LinkedList<NodeData> list3 = utils.TopologicalSort(graphs[2]);
        LinkedList<NodeData> list4 = utils.TopologicalSort(graphs[3]);

        assert (isSorted(list1));
        assert (isSorted(list2));
        assert (isSorted(list3));
        assert (isSorted(list4));
    }

    boolean isSorted(LinkedList<NodeData> list) {
        for (int i = 1; i < list.size(); ++i) {
            Node current = (Node) list.get(i);
            Node previous = (Node) list.get(i - 1);
            if (current.getFinishTime() > previous.getFinishTime()) {
                return false;
            }
        }

        return true;
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
        assert (algo[1].isConnected());
        assert (algo[2].isConnected());
        assert (!algo[3].isConnected());
    }

    @Test
    void shortestPath() {
        List<NodeData> list = algo[3].shortestPath(0, 4);
        double dist = algo[3].shortestPathDist(0, 4);
        System.out.println();
    }


    @Test
    void save() {
        for (int i = 0; i < algo.length; ++i) {
            boolean flag = algo[i].save("G" + i + "_save");
            assert (flag);
        }
    }

    @Test
    void load(){
        for (int i = 0; i < algo.length; ++i){
            String file = "saved_graphs/G" + i + "_save.json";
            algo[i].load(file);
        }
    }

    @Test
    void center() {
        NodeData test1 = algo[0].center();
        NodeData test2 = algo[1].center();
        NodeData test3 = algo[2].center();
        NodeData test4 = algo[3].center();

        assertTrue(test1 != null);
        assertTrue(test2 != null);
        assertTrue(test3 != null);
        assertFalse(test4 != null);

        System.out.println(test1.getKey());
        System.out.println(test2.getKey());
        System.out.println(test3.getKey());
    }

    @Test
    void tsp() { // is working fine now to try and make it a little more efficient
        List<NodeData> nodes = new LinkedList<>();

        int[] indexes = new int[]{0, 5, 2};
        System.out.println("Tour should pass through " + Arrays.toString(indexes));

        for (int i = 0; i < algo.length; ++i) {
            for (int index : indexes) {
                nodes.add(graphs[i].getNode(index));
            }

            List<NodeData> tsp = algo[i].tsp(nodes);

            assert (validate(tsp, graphs[i]));
            System.out.println(tsp);
        }

    }

    boolean validate(List<NodeData> path, DirectedWeightedGraph graph){
        for (int i = 0; i < path.size() - 1; ++i){
            NodeData current = path.get(i);
            NodeData nextNode = path.get(i + 1);
            EdgeData connectingEdge = graph.getEdge(current.getKey(), nextNode.getKey());

            if (connectingEdge == null){
                return false;
            }
        }

        return true;
    }
}
