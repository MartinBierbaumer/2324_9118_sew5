package dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

//@author Martin Bierbaumer

class Node implements Comparable<Node> {
    private final String id;
    private final List<Edge> edges = new ArrayList<>();
    private boolean visit = false;
    private Node previous = null;
    private int distance = Integer.MAX_VALUE;
    public String getId() {
        return id;
    }

    public boolean visited() {
        return visit;
    }

    public Node getPrevious() {
        return previous;
    }

    public int getDistance() {
        return distance;
    }

    public Node(String id) {
        this.id = id;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public int compareTo(Node other) {
        return this.getDistance() == other.getDistance() ? this.getId().compareTo(other.getId()) : Integer.compare(this.getDistance(), other.getDistance());
    }

    public void init() {
        visit = false;
        previous = null;
        distance = Integer.MAX_VALUE;
    }

    public void setStartNode() {
        distance = 0;
    }

    public void visit(PriorityQueue<Node> pq) {
        visit = true;
        for (Edge edge : edges) {
            int dist = distance + edge.distance();
            if (dist < edge.neighbour().distance) {
                pq.remove(edge.neighbour());
                edge.neighbour().previous = this;
                edge.neighbour().distance = dist;
                pq.add(edge.neighbour());
            }
        }
    }

    public String edgesToStr() {
        return String.join(" ",  edges.stream().map(it -> it.neighbour().id + ":" + it.distance()).toList());
    }
}