import java.util.Random;

public class GraphFactory {

    protected int startNode, endNode, nrOfNodes;

    protected int getStartNode() {
        return startNode;
    }

    protected int getEndNode() {
        return endNode;
    }


    protected GraphList generateGraph() {


        //Small Test Graph
        startNode = 0;
        endNode = 5;
        nrOfNodes = 6;

        GraphList g;
        g = new GraphList(nrOfNodes, startNode, endNode);
       /* //source edges
        g.addEdge(startNode, 1, 12, 3);
        g.addEdge(startNode, 2, 10, 1);

        //sink edges
        g.addEdge(3, endNode, 20, 1);
        g.addEdge(4, endNode, 4, 10);


        //edges
        g.addEdge(1, 3, 22, 1);
        g.addEdge(2, 1, 7, 1);
        g.addEdge(2, 4, 14, 5);
        g.addEdge(3, 2, 11, 2);
        g.addEdge(4, 3, 7, 1);

*/
        g.addEdge(startNode, 1, 12, 3);
        g.addEdge(startNode, 2, 10, 1);

        //sink edges
        g.addEdge(3, endNode, 20, 1);
        g.addEdge(4, endNode, 4, 10);


        //edges
        g.addEdge(1, 3, 15, 4);
        g.addEdge(2, 1, 7, 6);
        g.addEdge(2, 4, 12, 5);
        // g.addEdgeMap(3, 2, 11, 1);
        g.addEdge(4, 3, 9, 1);

        return g;

    }

    protected GraphList generateRandomGraph() {
        //Randomly generated graph
        //Number of nodes and edges can be specified variably
        startNode = 0;
        endNode = 1000;
        nrOfNodes = endNode + 1;

        GraphList g;
        g = new GraphList(nrOfNodes, startNode, endNode);
        //Number of edges from start node/to the end node
        int startEndEdges = 100;
        //Number of edges in the graph
        int edges = endNode / 2;
        //Prevents array overflow
        if (edges > endNode) edges = endNode;
        if (startEndEdges > endNode) edges = endNode;

        //start edges
        for (int i = 0; i < startEndEdges; i++) {
            g.addEdge(startNode, getRandomNumber(), getRandomNumber(), getRandomNumber());
        }
        //sink edges
        for (int i = 0; i < startEndEdges; i++) {
            g.addEdge(getRandomNumber(), endNode, getRandomNumber(), getRandomNumber());
        }
        //from edges
        for (int i = 0; i < edges; i++) {
            g.addEdge(i, getRandomNumber(), getRandomNumber(), getRandomNumber());
        }
        //to edges
        for (int i = 0; i < edges; i++) {
            g.addEdge(getRandomNumber(), i, getRandomNumber(), getRandomNumber());
        }
        return g;
    }


    protected GraphList generateLineGraph() {
        //Randomly generated graph
        //Number of nodes and edges can be specified variably
        startNode = 0;
        endNode = 1000;
        nrOfNodes = endNode + 1;

        GraphList g;
        g = new GraphList(nrOfNodes, startNode, endNode);
        //Number of edges from start node/to the end node
        int startEndEdges = 100;
        //Number of edges in the graph
        int edges = endNode / 2;
        //Prevents array overflow
        if (edges > endNode) edges = endNode;
        if (startEndEdges > endNode) edges = endNode;

        //start edges
        for (int i = 0; i < endNode; i++) {
            g.addEdge(i, i + 1, getRandomNumber() + 100, getRandomNumber());
        }
        return g;
    }

    protected GraphList generateSymGraph() {
        //Randomly generated graph
        //Number of nodes and edges can be specified variably
        startNode = 0;
        endNode = 500;
        nrOfNodes = endNode + 1;

        GraphList g;
        g = new GraphList(nrOfNodes, startNode, endNode);
        //Number of edges from start node/to the end node
        int startEndEdges = 50;
        //Number of edges in the graph
        int edges = endNode / 2;
        //Prevents array overflow
        if (edges > endNode) edges = endNode;
        if (startEndEdges > endNode) edges = endNode;
        int s, t, ca, co;

        //start edges
        for (int i = 0; i < startEndEdges; i++) {
            s = getRandomNumber();
            t = getRandomNumber();
            ca = getRandomNumber();
            co = getRandomNumber();
            g.addEdge(startNode, t, ca, co);
            g.addEdge(t, startNode, ca, co);
        }
        //sink edges
        for (int i = 0; i < startEndEdges; i++) {
            s = getRandomNumber();
            t = getRandomNumber();
            ca = getRandomNumber();
            co = getRandomNumber();
            g.addEdge(s, endNode, ca, co);
            g.addEdge(endNode, s, ca, co);
        }
        //from edges
        for (int i = 0; i < edges; i++) {
            s = getRandomNumber();
            t = getRandomNumber();
            ca = getRandomNumber();
            co = getRandomNumber();
            g.addEdge(s, t, ca, co);
            g.addEdge(t, s, ca, co);
        }

        return g;
    }


    private static int getRandomNumber() {
        Random randomgenerator = new Random();
        int out = randomgenerator.nextInt(100) + 1;
        return out;
    }

}
