import java.util.Random;

public class GraphFactory {

    protected int startNode, endNode, nrOfNodes;

    protected int getStartNode() {
        return startNode;
    }

    protected int getEndNode() {
        return endNode;
    }


    protected GraphList generateGraphMap() {


        //Small Test Graph
        startNode = 0;
        endNode = 5;
        nrOfNodes = 6;

        GraphList g;
        g = new GraphList(nrOfNodes, startNode, endNode);
        //source edges
        g.addEdgeMap(startNode, 1, 12, 3);
        g.addEdgeMap(startNode, 2, 10, 1);

        //sink edges
        g.addEdgeMap(3, endNode, 20, 1);
        g.addEdgeMap(4, endNode, 4, 10);


        //edges
        g.addEdgeMap(1, 3, 22, 1);
        g.addEdgeMap(2, 1, 7, 1);
        g.addEdgeMap(2, 4, 14, 5);
        g.addEdgeMap(3, 2, 11, 2);
        g.addEdgeMap(4, 3, 7, 1);

        return g;

        //Randomly generated graph
        //Number of nodes and edges can be specified variably
       /* startNode = 0;
        endNode = 1000;
        nrOfNodes = endNode + 1;

        GraphList g;
        g = new GraphList(nrOfNodes, startNode, endNode);
        //Number of edges from start node/to the end node
        int startEndEdges = 100;
        //Number of edges in the graph
        int edges = endNode/2;
        //Prevents array overflow
        if (edges > endNode) edges = endNode;
        if (startEndEdges > endNode) edges = endNode;

        //start edges
        for (int i = 0; i < startEndEdges; i++) {
            g.addEdgeMap(startNode, getRandomNumber(), getRandomNumber(), getRandomNumber());
        }
        //sink edges
        for (int i = 0; i < startEndEdges; i++) {
            g.addEdgeMap(getRandomNumber(), endNode, getRandomNumber(), getRandomNumber());
        }
        //from edges
        for (int i = 0; i < edges; i++) {
            g.addEdgeMap(i, getRandomNumber(), getRandomNumber(), getRandomNumber());
        }
        //to edges
        for (int i = 0; i < edges; i++) {
            g.addEdgeMap(getRandomNumber(), i, getRandomNumber(), getRandomNumber());
        }
        return g;*/
    }


    private static int getRandomNumber() {
        Random randomgenerator = new Random();
        int out = randomgenerator.nextInt(1000) + 1;
        return out;
    }

}
