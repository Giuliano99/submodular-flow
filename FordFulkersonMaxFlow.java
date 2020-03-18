import static java.lang.Math.min;

import java.util.List;

public class FordFulkersonMaxFlow {

    GraphList graph;

    public FordFulkersonMaxFlow(GraphList gr) {
        this.graph = gr;
    }

    protected static final long INF = Long.MAX_VALUE / 2;


    // Performs the Ford-Fulkerson method applying a depth first search as
    // a means of finding an augmenting path.
    public void solve() {

        // Find max flow by adding all augmenting path flows.
        for (long f = dfs(graph.t, INF); f != 0; f = dfs(graph.s, INF)) {
            graph.markAllNodesAsUnvisited();
            graph.maxFlow += f;
        }
        graph.maxFlow = graph.maxFlow - INF;
    }

    private long dfs(int node, long flow) {
        //At sink node, return augmented path flow.
        if (node == graph.t) return flow;
        List<Edge> edges = graph.graph[node];
        graph.visit(node);

        for (Edge edge : edges) {
            long rcap = edge.remainingCapacity();
            if (rcap > 0 && !graph.visited(edge.to)) {
                long bottleNeck = dfs(edge.to, min(flow, rcap));

                //Augment flow with bottle neck value
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }
}
