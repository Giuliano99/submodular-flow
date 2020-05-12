package SubmodularFlow;

import java.util.Date;

public class SubmodularFlow {

    public static void main(String[] args) {
        //Generate graph
        GraphFactorySubmodular gen = new GraphFactorySubmodular();
        GraphSubmodular graph = gen.generateRandomGraph();
        int startNode = gen.getStartNode();
        int end = gen.getEndNode();
        int nrOfNodes = gen.nrOfNodes;
        //Create objects of both algorithms
        //MinimumCostFLow.SuccessiveShortestPath Algo1 = new MinimumCostFLow.SuccessiveShortestPath(startNode, end, nrOfNodes, graph);

        SSPSubmodular Algo1 = new SSPSubmodular(startNode, end, nrOfNodes, graph);
        CCASubmod Algo2 = new CCASubmod(startNode, end, nrOfNodes, graph);


        //Execute algorithm 1
        System.out.println("SuccessiveShortestPath");
        long start = new Date().getTime();
        Algo1.solve();
        long runningTime = new Date().getTime() - start;
        System.out.println("Iterations " + Algo1.iter);
        //Output min costs
        System.out.println("Min Cost: " + graph.countCost());
        //Output max flow
        System.out.println("Max Flow: " + graph.countFlow());
        //Output runtime
        System.out.println("Laufzeit: " + runningTime + "ms");



        //Reset flow of the graph
        //graph.resetGraph();
        System.out.println("---------------");

        //Execute algorithm 2
        System.out.println("CycleCanceling");
        start = new Date().getTime();
        Algo2.solve();
        runningTime = new Date().getTime() - start;

        //Output min costs
        System.out.println("Min Cost: " + graph.countCost());
        //Output max flow
        System.out.println("Max Flow: " + graph.countFlow());
        //Output runtime
        System.out.println("Laufzeit: " + runningTime + "ms");

    }
}



