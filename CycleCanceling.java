import java.util.*;

import static java.lang.Math.min;

public class CycleCanceling {

    GraphList graph;

    public CycleCanceling(int n, int s, int t, GraphList gr) {
        this.graph = gr;
    }

    protected static final int INF = Integer.MAX_VALUE / 2;

    protected void solve() {
        //Initialize max flow by using the Ford Fulkerson Algorithm
        FordFulkersonMaxFlow maxFlow = new FordFulkersonMaxFlow(graph);
        maxFlow.solve();
        //While the residual graph contains a negative cycle, search for the cycle and cancel it
        do {
            //Print graph after every iteration
/*            graph.printGraph();
            System.out.println("----------------------------------------------------------------------------");*/
            // If there is no negative cycle, terminate
        } while (cancleCycle() == false);
    }

    //Scan the graph for negative Cycles with Bellman Ford Algorithm
    private boolean cancleCycle() {
        //Initialize the distances to all nodes with infinite, except the start node with 0
        long[] dist = new long[graph.n];
        Arrays.fill(dist, INF);
        dist[graph.t] = 0;
        //Array with all edges needed for the shortest path
        Edge[] prev = new Edge[graph.n];
        Edge e = new Edge(0, 0, 0,0);
        LinkedList<Edge> path = new LinkedList<>();
        //List with all edges of the circle
        LinkedList<Edge> circle = new LinkedList<>();


        // For each vertex, apply relaxation for all the edges
        for (int i = 0; i < graph.n - 1; i++) {
            for (List<Edge> edges : graph.graph)
                for (Edge edge : edges) {
                    if (edge.remainingCapacity() > 0 && dist[edge.from] + edge.cost < dist[edge.to] && edge != null) {
                        dist[edge.to] = dist[edge.from] + edge.cost;
                        prev[edge.to] = edge;

                    }
                }
        }


        //Run the Algo a second time, to detect a negative cycle
        for (int i = 0; i < graph.n - 1; i++) {
            for (int from = 0; from < graph.n; from++) {
                for (Edge edge : graph.graph[from]) {
                    //If there there is a better path beyond the optimal solution, it exists a negative cycle
                    if (edge.remainingCapacity() > 0 && dist[edge.from] + edge.cost < dist[edge.to]) {

                        int j = 0;

                        graph.markAllNodesAsUnvisited();
                        //Find a path in the array that contains a negative circle
                        for (int k = 0; k < graph.n - 1; k++) {
                            e = prev[j];
                            j = prev[j].from;
                            //As soon as an edge was visited for the second time, a circle was found
                            if (true == graph.visited(e.to)) {
                                break;
                            }
                            path.add(e);
                            /* System.out.println("Circle:" + e.toString(0, 0));*/
                            graph.visit(e.to);
                        }

                        graph.markAllNodesAsUnvisited();
                        j = path.getLast().from;

                        //Apply only the edges that belong to the circle
                        for (int k = 0; k < path.size(); k++) {
                            e = prev[j];
                            j = prev[j].from;

                            //As soon as an edge is visited for the second time, the end of the circle is found
                            if (true == graph.visited(e.to)) {
                                graph.markAllNodesAsUnvisited();
                                break;
                            }
                            circle.add(e);
                            //Print circle
                            //System.out.println("Circle: " + e.toString(0, 0));

                            graph.visit(e.to);
                        }
                        //Find the maximum possible flow in the circle
                        int bottlNeck = findBottlneck(circle);
                        //Send this flow through the circle and eliminate the circle
                        augumentFlow(circle, bottlNeck);
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private int findBottlneck(LinkedList<Edge> edgeList) {
        int bottleNeck = INF;
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            bottleNeck = min(bottleNeck, edge.remainingCapacity());
        }
        return bottleNeck;
    }

    private void augumentFlow(LinkedList<Edge> edgeList, int bottlNeck) {
        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            if (edge.isResidual()) {
                edge.residual.augment(-bottlNeck);
            } else edge.augment(bottlNeck);
        }
    }
}
