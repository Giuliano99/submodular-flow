public class Edge {
    public int from, to;
    public Edge residual;
    public int flow, cost;
    public final int capacity, originalCost;

    public Edge(int from, int to, int capacity) {
        this(from, to, capacity, 0 /* unused */);
    }

    public Edge(int from, int to, int capacity, int cost) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.originalCost = this.cost = cost;
    }

    public boolean isResidual() {
        return capacity == 0;
    }

    public int remainingCapacity() {
        return capacity - flow;
    }

    public void augment(long bottleNeck) {
        flow += bottleNeck;
        residual.flow -= bottleNeck;
    }

    public String toString(int s, int t) {
        String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
        String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
        return String.format(
                "Edge %s -> %s | flow = %d | capacity = %d |cost = %d | is residual: %s",
                u, v, flow, capacity, cost, isResidual());
    }
    public String toHashcode() {
        String start = Integer.toString(from);
        String end = Integer.toString(to);
        String out = start + "-" + end;
        return out;
    }

}
