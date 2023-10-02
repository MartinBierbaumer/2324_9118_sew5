package RIP;

import java.util.List;
import java.util.TreeSet;

record Pair<T, U>(T first, Integer second) {}

public class RoutingTable {
    TreeSet<RoutingEntry> routingTable = new TreeSet<>();

    void update(Router r, List<Pair<Subnet, Integer>> updates) {
        routingTable.removeIf(e -> e.nextHop.equals(r));
        for (Pair<Subnet, Integer> v: updates) {
            RoutingEntry candidate = new RoutingEntry(v.first(), r, v.second() + 1);
            RoutingEntry compare = routingTable.ceiling(candidate);
            if (compare == null) {
                routingTable.add(candidate);
            } else {
                if (candidate.destination.mask != compare.destination.mask) {
                    routingTable.add(candidate);
                } else {
                    if (candidate.distance < compare.distance) {
                        routingTable.remove(compare);
                        routingTable.add(candidate);
                    }
                }
            }
        }
    }

    Router nextHop(int ip) {
        RoutingEntry result = routingTable.floor(new RoutingEntry(new Subnet(ip, 32), null, 0));
        if (result == null) return null;
        return (((result.destination.network ^ ip) & result.destination.mask) == 0) ? result.nextHop : null;
    }
}
