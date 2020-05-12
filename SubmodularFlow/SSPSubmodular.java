package SubmodularFlow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import membershiptest.*;

import static java.lang.Math.min;

public class SSPSubmodular {


    GraphSubmodular graph;
    GraphSubmodular graphHelp;


    public SSPSubmodular(int n, int s, int t, GraphSubmodular gr) {
        this.graph = gr;

    }

    protected static final long INF = Long.MAX_VALUE / 2;
    SMFunction submodFkt = new SMFunction();
    private EdgeSubmodular[] prev = null;
    int iter = 0;


    protected void solve() {
        initializeFlow();
        findFlow();
    }

    protected double[] buildBase(int laenge) {
        double[] vector = new double[laenge];
        ScanSubmodularFunction lesen = new ScanSubmodularFunction();
        String dateiName = "sm_function_3.txt";
        HashMap<Integer, Double> map = lesen.readFile(dateiName);
        for (int i = 0; i < laenge; i++) {
            vector[i] = mapFunction(map, i + 1) - mapFunction(map, i);
        }
        return vector;
    }


    //Fluss mit unterer Kap Grenze initialisieren
    protected void initializeFlow() {
        for (int i = 0; i < graph.n; i++) {
            for (EdgeSubmodular edge : graph.graph[i]) {
                if (edge.isResidual() == false)
                    edge.augment(edge.lowerCapacity);
            }
        }
        //System.out.println("Init Graph:");
        //graph.printGraph();
    }


    //2 Mengen von Knoten bilden, x+ und x- ausgehend - eingehend
    //ausgehende Kanten leicht von knoten aus zu finden
    //eingehende kanten, alle kanten absuchen

    protected void findFlow() {

        double[] vector = buildBase(graph.nrOfNodes());
        while (true) {
            iter++;
            List<Integer> sPositive = new LinkedList<>();
            List<Integer> sNegative = new LinkedList<>();

            for (int i = 0; i < graph.n; i++) {
                int ausgehend = 0;
                int eingehend = 0;
                int flussInKnotenI = 0;
                //leaving edges
                for (EdgeSubmodular edge : graph.graph[i]) {
                    if (edge.isResidual() == false) {
                        ausgehend += edge.flow * edge.cost;
                    }
                }
                //incoming edges
                for (int j = 0; j < graph.n; j++) {
                    for (EdgeSubmodular e2 : graph.graph[j]) {
                        if (e2.to == i && e2.isResidual() == false) {
                            eingehend += e2.flow * e2.cost;
                        }
                    }
                }
                flussInKnotenI = ausgehend - eingehend;
                double x = vector[i];
                //add nodes in S+
                if (flussInKnotenI < x) {
                    sPositive.add(i);
                } else if (flussInKnotenI > x) sNegative.add(i);
                else ;
            }
            if (sPositive.size() == 0 || sNegative.size() == 0) return;
            //build auxilary network
            buildAuxilaryNetwork(vector);
            int end = graph.nrOfNodes() - 1;
            int start = sPositive.get(0);
            graphHelp.s = start;
            int[] dist = getDistances();

            int shortestPath = Integer.MAX_VALUE / 2;
            for (int i = 0; i < dist.length; i++) {

                if (dist[i] > 0 && dist[i] < shortestPath && sNegative.contains(i)) {
                    end = i;
                    shortestPath = dist[i];
                }
            }
            if (shortestPath == Integer.MAX_VALUE / 2 || start == end) return;

            //augument path
            EdgeSubmodular edge;
            edge = prev[end];


            while (true) {
                if (edge == null) return;
                //Edges from set A or B
                if (edge.help == false) {
                    //Unterscheidung in A und B bei Residualkante Gegenkante suchen
                    String edgeFinder = edge.toHashcode();
                    EdgeSubmodular edgeOriginal = graph.map.get(edgeFinder);
                    edgeOriginal.augment(1);
                }
                //Edges from Set C
                else {
                    //update Base
                    vector[edge.to]++;
                    vector[edge.from]--;
                }
                if (edge.from == start) {
                    break;
                }
                edge = prev[edge.from];
            }

        }

    }

    protected void buildAuxilaryNetwork(double[] base) {
        graphHelp = new GraphSubmodular(graph.nrOfNodes(), 0, graph.nrOfNodes());
        //Kanten A und B einfügen
        for (int i = 0; i < graph.graph.length; i++) {
            for (EdgeSubmodular e : graph.graph[i]) {
                if (!e.isResidual()) {
                    graphHelp.addHelpEdge(e.from, e.to, e.upperCapacity, e.lowerCapacity, e.cost, false);
                    String edgeFinder = e.toHashcode();
                    EdgeSubmodular edgeHelp = graphHelp.map.get(edgeFinder);
                    edgeHelp.augment(e.flow);
                }
            }
        }
        //Add edges from set C
        int count = graph.nrOfNodes();
        //Check all node pairs
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                double[] vector = copyArray(base);
                //Excluding loops
                if (i != j) {
                    //Calculate vector
                    vector[i] = vector[i] - 1;
                    vector[j] = vector[j] + 1;
                    //check if vector in Base
                    boolean xbase = MembershipChecker.is_base(base, vector, submodFkt);
                    if (xbase == true) {
                        graphHelp.addHelpEdge(i, j, 1, 0, 1, true);
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

    protected int[] getDistances() {
        int[] dist = new int[graphHelp.n];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[graphHelp.s] = 0;

        EdgeSubmodular[] prev = new EdgeSubmodular[graphHelp.n];

        // For each vertex, relax all the edges in the graph, by using the Bellman Ford Algorithm
        for (int i = 0; i < graphHelp.n - 1; i++) {
            for (int from = 0; from < graphHelp.n; from++) {
                for (EdgeSubmodular edge : graphHelp.graph[from]) {
                    if (edge.remainingCapacity() > 0 && dist[edge.from] + edge.cost < dist[edge.to]) {
                        dist[edge.to] = dist[from] + edge.cost;
                        prev[edge.to] = edge;
                    }
                }
            }
        }

        this.prev = prev;
        return dist;
    }


    class SMFunction implements SetFunction {
        ScanSubmodularFunction reader = new ScanSubmodularFunction();
        String fileName = "sm_function_3.txt";
        HashMap<Integer, Double> map = reader.readFile(fileName);

        public double evaluate(int[] set) {
            //return submodFunction(set.length);
            return mapFunction(map, set.length);
        }

    }

    protected double submodFunction(int input) {
        double function = 3 * (min(2, input) - (2. / 3.) * input);
        return function;
    }

    protected double mapFunction(HashMap<Integer, Double> map, int input) {
        return map.get(input);
    }
}
