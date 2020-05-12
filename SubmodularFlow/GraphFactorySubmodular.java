package SubmodularFlow;

import java.util.Random;

public class GraphFactorySubmodular {


    protected int startNode, endNode, nrOfNodes;

    protected int getStartNode() {
        return startNode;
    }

    protected int getEndNode() {
        return endNode;
    }


    protected GraphSubmodular generateGraph() {


        //Small Test Graph
        startNode = 0;
        //endNode = 3;
        nrOfNodes = 3;

        GraphSubmodular g;
        g = new GraphSubmodular(nrOfNodes, startNode, endNode);



        g.addEdge(startNode, 1, 2,1,1, false);
        g.addEdge(1, 2, 2,0,1, false);
        g.addEdge(2, startNode, 2,0,1, false);
        g.addEdge(startNode, 2, 2,0,1, false);


        return g;

    }

    protected GraphSubmodular generateRandomGraph() {
        //Randomly generated graph
        //Number of nodes and edges can be specified variably
        startNode = 0;
        endNode = 31;
        nrOfNodes = endNode + 1;

        GraphSubmodular g;
        g = new GraphSubmodular(nrOfNodes, startNode, endNode);

/*        //start edges
        for (int i = 0; i < startEndEdges; i++) {
            g.addEdge(startNode, getRandomNumber(), getRandomNumber(), getRandomNumber());
        }
        //sink edges
        for (int i = 0; i < startEndEdges; i++) {
            g.addEdge(getRandomNumber(), endNode, getRandomNumber(), getRandomNumber());
        }*/
        //from edges
        for (int i = 0; i < endNode; i++) {
            g.addEdge(i, getRandomNumber(), getRandomNumber(), 1,getRandomNumber(),false);

        }
        //to edges
        for (int i = 0; i <endNode; i++) {
            g.addEdge(getRandomNumber(), i, getRandomNumber(), 0,getRandomNumber(), false);

        }
        return g;
    }

    protected GraphSubmodular generateLineGraph() {
        //Randomly generated graph
        //Number of nodes and edges can be specified variably
        startNode = 0;
        endNode = 31;
        nrOfNodes = endNode + 1;

        GraphSubmodular g;
        g = new GraphSubmodular(nrOfNodes, startNode, endNode);
        //Number of edges from start node/to the end node
        int startEndEdges = 5;
        //Number of edges in the graph
        int edges = endNode / 2;
        //Prevents array overflow
        if (edges > endNode) edges = endNode;
        if (startEndEdges > endNode) edges = endNode;


        //g.addEdge(0,  1, getRandomNumber() ,0,getRandomNumber(),false);
        //start edges
        for (int i = 0; i < endNode; i++) {
            g.addEdge(i, i + 1, getRandomNumber() ,0,getRandomNumber(),false);
        }
        return g;
    }

    protected GraphSubmodular generateSymGraph() {
        //Randomly generated graph
        //Number of nodes and edges can be specified variably
        startNode = 0;
        endNode = 10;
        nrOfNodes = endNode + 1;

        GraphSubmodular g;
        g = new GraphSubmodular(nrOfNodes, startNode, endNode);
        //Number of edges from start node/to the end node
        int startEndEdges = 50;
        //Number of edges in the graph
        int edges = endNode / 2;
        //Prevents array overflow
        if (edges > endNode) edges = endNode;
        if (startEndEdges > endNode) edges = endNode;
        int s, t, opcapacity, co;

        //start edges
        for (int i = 0; i < startEndEdges; i++) {

            t = getRandomNumber();
            opcapacity = getRandomNumber();
            co = getRandomNumber();
            g.addEdge(startNode, t, opcapacity, 0, co, false);
            g.addEdge(t, startNode, opcapacity, 0, co, false);
        }
        //sink edges
        for (int i = 0; i < startEndEdges; i++) {
            s = getRandomNumber();
            t = getRandomNumber();
            opcapacity = getRandomNumber();
            co = getRandomNumber();
            g.addEdge(s, endNode, opcapacity, 1, co, false);
            g.addEdge(endNode, s, opcapacity, 1, co,false);
        }
        //from edges
        for (int i = 0; i < edges; i++) {
            s = getRandomNumber();
            t = getRandomNumber();
            opcapacity = getRandomNumber();
            co = getRandomNumber();
            g.addEdge(s, t, opcapacity, 0,co, false);
            g.addEdge(t, s, opcapacity, 0, co, false);
        }

        return g;
    }



    private static int getRandomNumber() {
        Random randomgenerator = new Random();
        int out = randomgenerator.nextInt(10) + 1;
        return out;
    }
}
