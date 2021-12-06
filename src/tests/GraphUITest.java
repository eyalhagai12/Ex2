package tests;

import api.Graph;
import api.GraphAlgo;
import api.GraphUI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphUITest {
    GraphUI UI;

    @BeforeEach
    void init(){
        Graph g = new Graph("data/G1.json");
        GraphAlgo algo = new GraphAlgo();
        algo.init(g);

        UI = new GraphUI(algo);
    }

    @Test
    void create(){
        System.out.println("hello world!");
    }
}