package RIP;

public class RoutingEntry implements Comparable<RoutingEntry> {
    Subnet destination;
    Router nextHop;
    int distance;

    public RoutingEntry(Subnet destination, Router nextHop, int distance) {
        this.destination = destination;
        this.nextHop = nextHop;
        this.distance = distance;
    }

    @Override
    public int compareTo(RoutingEntry o) {
        return destination.compareTo(o.destination);
    }
}
