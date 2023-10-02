package RIP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

record Distance(Subnet subnet, Integer distance) {}

public class Router {
    Set<Router> neighbours = new HashSet<>();
    HashMap<Subnet, Vector> routingTable = new HashMap<>();

    void connect(Router other) {
        neighbours.add(other);
    }

    void tick() {
        for (Router neighbor: neighbours) {
            send(neighbor);
        }
    }


    void addRoute(Subnet net, Vector vec) {
        if (vec.distance() < routingTable.getOrDefault(net, new Vector(null, Integer.MAX_VALUE)).distance()) routingTable.put(net, vec);
    }

    Set<Subnet> local = new HashSet<>();
    void addRoute(Subnet net) {
        local.add(net);
        addRoute(net, new Vector(this, 0));
    }

    void receive(Router sender, List<Distance> message) {
        for (Distance d: message) addRoute(d.subnet(), new Vector(sender, d.distance() + 1));
    }

    private void send(Router destination) {
        destination.receive(this, routingTable.entrySet().stream().filter(e -> !e.getValue().nextHop().equals(destination)).map(e -> new Distance(e.getKey(), e.getValue().distance())).toList());
    }


    void send(Packet message) {
        for (int prefixLen = 32; prefixLen >= 0; prefixLen--) {
            if (routingTable.containsKey(new Subnet(message.destination, prefixLen))) {
                Vector vec = routingTable.get(new Subnet(message.destination, prefixLen));
                if (message.TTL <= 0) {
                    send(new Packet(message.source, local.isEmpty() ? 0 : local.iterator().next().network, TTL_REACHED, TTL, message.type));
                } else {
                    if (vec.nextHop() == this) {
                        process(message);
                    } else {
                        vec.nextHop().send(message.next());
                    }
                }
                return;
            }
        }
    }

    final int PING = 0;
    final int ECHO = 1;
    final int TTL_REACHED = 2;
    final int TTL = 255;
    void process(Packet message) {
        switch (message.type) {
            case PING -> send(new Packet(message.source, message.destination, ECHO, TTL, 0));
            case ECHO -> System.out.println("Ping");
            case TTL_REACHED -> {if (message.message == PING) System.out.println("TTL reached at: " + message.source);}
        }
    }

    void ping(Subnet destination) {
        send(new Packet(destination.network, local.isEmpty() ? 0 : local.iterator().next().network, PING, TTL, 0));
    }

    void tracert(Subnet destination, int d) {
        for (int i = 0; i <= d; i++) {
            send(new Packet(destination.network, local.isEmpty() ? 0 : local.iterator().next().network, PING, i, 0));
        }
    }
}
