package MinimumCostFLow;

import java.util.Date;

public class MinimumCostFlow {

    public static void main(String[] args) {
        //Generate graph
        GraphFactory gen = new GraphFactory();
        GraphList graph = gen.generateRandomGraph();
        int startNode = gen.getStartNode();
        int end = gen.getEndNode();
        int nrOfNodes = gen.nrOfNodes;
        //Create objects of both algorithms
        SuccessiveShortestPath Algo1 = new SuccessiveShortestPath(startNode, end, nrOfNodes, graph);
        CycleCanceling Algo2 = new CycleCanceling(startNode, end, nrOfNodes, graph);


        //Print graph
        //graph.printGraph();
        //Execute algorithm 1
        //System.out.println("Anzahl Kanten: " + graph.nrOfEdges());
        System.out.println("Successive Shortest Path");
        long start = new Date().getTime();
        Algo1.solve();
        long runningTime = new Date().getTime() - start;
        //Output min costs
        System.out.println("Min Cost: " + graph.countCost());
        //Output max flow
        System.out.println("Max Flow: " + graph.countFlow());
        //Output runtime
        System.out.println("Laufzeit: " + runningTime + "ms");
        System.out.println("Iterationen: " + Algo1.iter);


        //Reset flow of the graph
        graph.resetGraph();
        System.out.println("---------------");

        //Execute algorithm 2
        System.out.println("Cycle Canceling");
        start = new Date().getTime();
        Algo2.solve();
        runningTime = new Date().getTime() - start;

        //Output min costs
        System.out.println("Min Cost: " + graph.countCost());
        //Output max flow
        System.out.println("Max Flow: " + graph.countFlow());
        //Output runtime
        System.out.println("Laufzeit: " + runningTime + "ms");
        System.out.println("Iterationen: " + Algo2.iter);

    }
}
