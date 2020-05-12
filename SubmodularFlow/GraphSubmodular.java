package SubmodularFlow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphSubmodular {


    protected int n, s, t;
    protected long maxFlow;
    protected List<EdgeSubmodular>[] graph;
    protected HashMap<String, EdgeSubmodular> map;
    private int visitedToken = 1;
    private int[] visited;

    public GraphSubmodular(int n, int s, int t) {
        this.n = n;
        this.s = s;
        this.t = t;
        initializeGraph();
        visited = new int[n];
    }

    //Construct an empty graph with n nodes including the source and sink nodes
    private void initializeGraph() {
        map = new HashMap<String, EdgeSubmodular>();
        graph = new List[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();


    }


    protected void addEdge(int from, int to, int upperCapacity, int lowerCapacity, int cost, boolean help) {

        EdgeSubmodular e1 = new EdgeSubmodular(from, to, upperCapacity, lowerCapacity, cost, help);
        if (map.containsKey(e1.toHashcode()) || from == to)
            return;
        EdgeSubmodular e2 = new EdgeSubmodular(to, from, 0, lowerCapacity, -cost, help);
        e1.residual = e2;
        e2.residual = e1;

        graph[from].add(e1);
        graph[to].add(e2);

        map.put(e1.toHashcode(), e1);
        map.put(e2.toHashcode(), e2);
    }

    protected void addHelpEdge(int from, int to, int upperCapacity, int lowerCapacity, int cost, boolean help) {
        EdgeSubmodular e1 = new EdgeSubmodular(from, to, upperCapacity, lowerCapacity, cost, help);
        if (from == to)
            return;

        EdgeSubmodular e2 = new EdgeSubmodular(to, from, 0, lowerCapacity, -cost, help);
        e1.residual = e2;
        e2.residual = e1;

        graph[from].add(e1);
        graph[to].add(e2);
        map.put(e1.toHashcode(), e1);
        map.put(e2.toHashcode(), e2);
        graph[from].add(e1);
        map.put(e1.toHashcode(), e1);
    }

    protected boolean addHelpEdge2(int from, int to, int upperCapacity, int lowerCapacity, int cost, boolean help) {
        EdgeSubmodular e1 = new EdgeSubmodular(from, to, upperCapacity, lowerCapacity, cost, help);
        if (from == to || map.containsKey(e1.toHashcode()))
            return false;
        EdgeSubmodular e2 = new EdgeSubmodular(to, from, 0, lowerCapacity, -cost, help);
        e1.residual = e2;
        e2.residual = e1;
        graph[from].add(e1);
        graph[to].add(e2);
        map.put(e1.toHashcode(), e1);
        map.put(e2.toHashcode(), e2);
        return true;
    }


    protected void resetGraph() {
        //Sets remaining capacity back to initial value
        for (List<EdgeSubmodular> path : graph) {
            for (EdgeSubmodular e : path) {
                e.flow = 0;
            }
        }
    }

    protected void printGraph() {
        for (int i = 0; i < graph.length; i++) {
            System.out.println("Node" + i);
            for (EdgeSubmodular edge : graph[i]) {
                if (edge.isResidual() == false && edge.help == false)
                    System.out.println(edge.toString(0, 0));
            }
        }
    }

    protected int nrOfNodes() {
        int out = 0;
        for (int i = 0; i < graph.length; i++) {
            out++;
        }
        return out;
    }


    protected void printResidualGraph() {
        for (int i = 0; i < graph.length; i++) {
            System.out.println("Node" + i);
            for (EdgeSubmodular edge : graph[i]) {
                System.out.println(edge.toString(0, 0));
            }
        }
    }

    public int countFlow() {
        int out = 0;
        for (List<EdgeSubmodular> path : graph) {
            for (EdgeSubmodular e : path) {
                if (!e.isResidual()) {
                    out += e.flow;
                }
            }
        }
        return out;
    }

    public int countCost() {
        int out = 0;
        for (List<EdgeSubmodular> path : graph) {
            for (EdgeSubmodular e : path) {
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
