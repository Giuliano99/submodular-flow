package SubmodularFlow;

public class EdgeSubmodular {

    protected int from, to;
    protected boolean help;
    protected EdgeSubmodular residual;
    protected int flow, cost;
    protected final int upperCapacity, lowerCapacity, originalCost;

    protected EdgeSubmodular(int from, int to, int upperCapacity, int lowerCapacity, int cost, boolean help) {
        this.from = from;
        this.to = to;
        this.help = help;
        this.upperCapacity = upperCapacity;
        this.lowerCapacity = lowerCapacity;
        this.originalCost = this.cost = cost;
    }

    protected boolean isResidual() {
        return cost < 0;
        //return upperCapacity == 0;
    }

    protected int remainingCapacity() {

        //return upperCapacity - flow - lowerCapacity;
        if (!isResidual()){
            return upperCapacity -flow;
        }
        else return -flow - lowerCapacity;

    }

    protected void augment(long bottleNeck) {
        flow += bottleNeck;
        residual.flow -= bottleNeck;
    }
    protected void augmentHelp(long bottleNeck) {
        flow += bottleNeck;
    }


    protected String toString(int s, int t) {
        String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
        String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
        return String.format(
                "MinimumCostFLow.Edge %s -> %s | flow = %d | uppercapacity = %d | lowercapacity = %d|cost = %d | is help: %s| is residual: %s",
                u, v, flow, upperCapacity, lowerCapacity, cost, help, isResidual());
    }

    protected String toHashcode() {
        String start = Integer.toString(from);
        String end = Integer.toString(to);
        String residual = Boolean.toString(isResidual());
        String out = start + "-" + end + "-" + residual;
        return out;
    }

}
