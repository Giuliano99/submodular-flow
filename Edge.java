public class Edge {
    protected int from, to;
    protected Edge residual;
    protected int flow, cost;
    protected final int capacity, originalCost;

    protected Edge(int from, int to, int capacity, int cost) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.originalCost = this.cost = cost;
    }

    protected boolean isResidual() {
        return capacity == 0;
    }

    protected int remainingCapacity() {
        return capacity - flow;
    }

    protected void augment(long bottleNeck) {
        flow += bottleNeck;
        residual.flow -= bottleNeck;
    }

    protected String toString(int s, int t) {
        String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
        String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
        return String.format(
                "Edge %s -> %s | flow = %d | capacity = %d |cost = %d | is residual: %s",
                u, v, flow, capacity, cost, isResidual());
    }

    protected String toHashcode() {
        String start = Integer.toString(from);
        String end = Integer.toString(to);
        String out = start + "-" + end;
        return out;
    }

}
