import java.util.*;

public class GraphList {

    protected final int n, s, t;
    protected long maxFlow;
    protected List<Edge>[] graph;
    protected HashMap<String, Edge> mapGraph;
    private int visitedToken = 1;
    private int[] visited;

    public GraphList(int n, int s, int t) {
        this.n = n;
        this.s = s;
        this.t = t;
        initializeGraph();
        initializeGraphMap();
        visited = new int[n];
    }

    //Construct an empty graph with n nodes including the source and sink nodes.
    private void initializeGraph() {
        graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<Edge>();
    }


    private void initializeGraphMap() {
        mapGraph = new HashMap<>();
    }


    protected void addEdgeMap(int from, int to, int capacity, int cost) {

        Edge e1 = new Edge(from, to, capacity, cost);
        if (mapGraph.containsKey(e1.toHashcode()))
            return;
        Edge e2 = new Edge(to, from, 0, -cost);
        e1.residual = e2;
        e2.residual = e1;

        graph[from].add(e1);
        graph[to].add(e2);

        mapGraph.put(e1.toHashcode(), e1);
    }

    protected void resetGraph() {
        //Sets remaining capacity back to initial value
        for (List<Edge> path : graph) {
            for (Edge e : path) {
                e.flow = 0;
            }
        }
    }

    protected void printGraph() {
        for (int i = 0; i < graph.length; i++) {
            for (Edge edge : graph[i]) {
                if (edge.isResidual() == false)
                    System.out.println(edge.toString(0, 0));
            }
        }
    }

    protected void printResidualGraph() {
        for (int i = 0; i < graph.length; i++) {
            for (Edge edge : graph[i]) {
                System.out.println(edge.toString(0, 0));
            }
        }
    }

    public int countFlow() {
        int out = 0;
        for (List<Edge> path : graph) {
            for (Edge e : path) {
                if (e.from == 0) {
                    out += e.flow;
                }
            }
        }
        return out;
    }

    public int countCost() {
        int out = 0;
        for (List<Edge> path : graph) {
            for (Edge e : path) {
                out += e.flow * e.originalCost;
            }
        }
        return out / 2;
    }

    //Marks node 'i' as visited.
    public void visit(int i) {
        visited[i] = visitedToken;
    }

    //Returns whether or not node 'i' has been visited.
    public boolean visited(int i) {
        return visited[i] == visitedToken;
    }

    //Resets all nodes as unvisited
    public void markAllNodesAsUnvisited() {
        visitedToken++;
    }

}
