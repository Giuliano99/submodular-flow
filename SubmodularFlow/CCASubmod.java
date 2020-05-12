package SubmodularFlow;

import membershiptest.MembershipChecker;
import membershiptest.SetFunction;

import java.util.*;

import static java.lang.Math.min;

public class CCASubmod {

    GraphSubmodular graph;

    public CCASubmod(int n, int s, int t, GraphSubmodular gr) {
        this.graph = gr;
    }

    protected static final int INF = Integer.MAX_VALUE / 2;

    protected double[] buildBase(int laenge) {
        double[] vector = new double[laenge];
        ScanSubmodularFunction lesen = new ScanSubmodularFunction();
        String dateiName = "sm_function_2.txt";
        HashMap<Integer, Double> map = lesen.readFile(dateiName);
        for (int i = 0; i < laenge; i++) {
            vector[i] = mapFunction(map, i + 1) - mapFunction(map, i);
        }
        return vector;
    }

    protected void addEdgesC(double[] base) {
        CCASubmod.SMFunction fun = new CCASubmod.SMFunction();
        //Add edges from set C
        int count = graph.n;
        //Check all node pairs
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                double[] vector = copyArray(base);
                //excluding loops
                if (i != j) {
                    vector[i] = vector[i] - 1;
                    vector[j] = vector[j] + 1;
                    boolean xbase = MembershipChecker.is_base(base, vector, fun);
                    if (xbase == true) {
                        graph.addHelpEdge2(i, j, 10, 0, 1, true);
                    }
                }
            }

        }
    }

    private double[] copyArray(double[] array) {
        double[] out = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            out[i] = array[i];
        }
        return out;
    }

    protected void solve() {
        addEdgesC(buildBase(graph.n));
        int iter = 0;
        do {
            iter++;
        } while (cancleCycle() == false);
        System.out.println("Iterations: " + iter);
    }

    //Scan the graph for negative Cycles with Bellman Ford Algorithm
    private boolean cancleCycle() {
        //Initialize the distances to all nodes with infinite, except the start node with 0
        long[] dist = new long[graph.n];
        Arrays.fill(dist, INF);
        dist[graph.t] = 0;
        //Array with all edges needed for the shortest path
        EdgeSubmodular[] prev = new EdgeSubmodular[graph.n];
        EdgeSubmodular e = new EdgeSubmodular(0, 0, 0, 0, 0, false);
        LinkedList<EdgeSubmodular> path = new LinkedList<>();
        //List with all edges of the circle
        LinkedList<EdgeSubmodular> circle = new LinkedList<>();


        // For each vertex, apply relaxation for all the edges
        for (int i = 0; i < graph.n - 1; i++) {
            for (List<EdgeSubmodular> edges : graph.graph)
                for (EdgeSubmodular edge : edges) {
                    if (edge.remainingCapacity() > 0 && dist[edge.from] + edge.cost < dist[edge.to] && edge != null) {
                        dist[edge.to] = dist[edge.from] + edge.cost;
                        prev[edge.to] = edge;

                    }
                }
        }
        //Run the Algo a second time, to detect a negative cycle
        for (int i = 0; i < graph.n - 1; i++) {
            for (int from = 0; from < graph.n; from++) {
                for (EdgeSubmodular edge : graph.graph[from]) {
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


    private int findBottlneck(LinkedList<EdgeSubmodular> edgeList) {
        int bottleNeck = INF;
        for (int i = 0; i < edgeList.size(); i++) {
            EdgeSubmodular edge = edgeList.get(i);
            bottleNeck = min(bottleNeck, edge.remainingCapacity());
        }
        return bottleNeck;
    }

    private void augumentFlow(LinkedList<EdgeSubmodular> edgeList, int bottlNeck) {
        for (int i = 0; i < edgeList.size(); i++) {
            EdgeSubmodular edge = edgeList.get(i);
            if (edge.help == false) {
                if (edge.isResidual()) {
                    edge.residual.augment(-bottlNeck);
                } else edge.augment(bottlNeck);
            }
        }
    }


    public double mapFunction(HashMap<Integer, Double> map, int input) {
        return map.get(input);
    }

    class SMFunction implements SetFunction {
        ScanSubmodularFunction reader = new ScanSubmodularFunction();
        String fileName = "sm_function_2.txt";
        HashMap<Integer, Double> map = reader.readFile(fileName);

        public double evaluate(int[] set) {
            return mapFunction(map, set.length);
        }

    }
}
