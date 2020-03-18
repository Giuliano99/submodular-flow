import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.min;

public class SuccessiveShortestPath {

    GraphList graph;

    public SuccessiveShortestPath(int n, int s, int t, GraphList gr) {
        this.graph = gr;
    }

    protected static final long INF = Long.MAX_VALUE / 2;

    protected void solve() {
        List<Edge> path;
        //As long as there is a way from s to t execute
        while ((path = getAugumentingpath()).size() != 0) {
            //Find bottle neck edge value along path
            long bottleNeck = Long.MAX_VALUE;
            for (Edge edge : path) {
                bottleNeck = min(bottleNeck, edge.remainingCapacity());
                //Print path
                //System.out.println("Path: " + edge.toString(0, 0));
            }

            //Retrace path while augmenting the flow
            for (Edge edge : path) {
                edge.augment(bottleNeck);
            }
            //Print graph after every iteration
/*            graph.printGraph();
            System.out.println("----------------------------------------------------------------------------");*/
        }

    }

    private List<Edge> getAugumentingpath() {
        long[] dist = new long[graph.n];
        Arrays.fill(dist, INF);
        dist[graph.s] = 0;

        Edge[] prev = new Edge[graph.n];

        // For each vertex, relax all the edges in the graph, by using the Bellman Ford Algorithm
        for (int i = 0; i < graph.n - 1; i++) {
            for (int from = 0; from < graph.n; from++) {
                for (Edge edge : graph.graph[from]) {
                    if (edge.remainingCapacity() > 0 && dist[edge.from] + edge.cost < dist[edge.to]) {
                        dist[edge.to] = dist[from] + edge.cost;
                        prev[edge.to] = edge;
                    }
                }
            }
        }
        // Retrace augmenting path from sink back to the source.
        LinkedList<Edge> path = new LinkedList<>();
        for (Edge edge = prev[graph.t]; edge != null; edge = prev[edge.from]) path.addFirst(edge);
        //Return the shortest path
        return path;
    }

}
