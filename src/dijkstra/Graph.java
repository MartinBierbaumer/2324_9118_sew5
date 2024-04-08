package dijkstra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//@author Martin Bierbaumer

class Graph {
    private final List<Node> nodes = new ArrayList<>();
    public Graph(Path p) throws IOException {
        readGraphFromAdjacencyMatrixFile(p);
    }

    public void readGraphFromAdjacencyMatrixFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i < lines.size(); i++) {
            Node n = new Node(lines.get(i).split(";")[0]);
            nodes.add(n);
        }
        System.out.println(nodes);
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(";");

            Node node = getNode(parts[0]);

            for (int j = 1; j < parts.length; j++) {
                if (!parts[j].isEmpty()) {
                    int distance = Integer.parseInt(parts[j]);
                    Node neighbor = nodes.get(j - 1);
                    node.addEdge(new Edge(distance, neighbor));
                }
            }
        }
    }

    private Node getNode(String id) {
        for (Node n: nodes) {
            if (Objects.equals(n.getId(), id))
                return n;
        }
        return null;
    }

    public void calcWithDijkstra(String startNodeId) {
        for (Node node : nodes) {
            node.init();
        }
        Node startNode = getNode(startNodeId);
        assert startNode != null;
        startNode.setStartNode();
        PriorityQueue<Node> pq = new PriorityQueue<>(Node::compareTo);
        pq.add(startNode);

        while (pq.size() > 0) {
            Node currentNode = pq.poll();
            if (currentNode.visited()) continue;
            currentNode.visit(pq);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        Node startNode = nodes.stream().filter(n -> n.getPrevious() == null && n.getDistance() == 0).findFirst().orElse(null);

        for (Node node : nodes)
            builder.append(node == startNode ? String.format("%s ---> is start node %s\n", node.getId(), node.edgesToStr()) : String.format("%s [totalDistance: %s] %s\n", node.getId(), node.getDistance() != Integer.MAX_VALUE ? node.getDistance() : "?", node.edgesToStr()));

        return builder.toString();
    }

    public String getAllPaths() {
        StringBuilder builder = new StringBuilder();

        Node startNode = nodes.stream().filter(n -> n.getPrevious() == null && n.getDistance() == 0).findFirst().orElse(null);

        for (Node node : nodes) {
            if (node == startNode) {
                builder.append(String.format("%s ---> is start node %s\n", node.getId(), node.edgesToStr()));
            } else if (node.getDistance() == Integer.MAX_VALUE) {
                builder.append(String.format("%s not reachable\n", node.getId()));
            } else {
                builder.append(String.format("%s %s\n", startNode.getId(), getPath(node)));
            }
        }

        return builder.toString();
    }

    private String getPath(Node node) {
        List<String> path = new ArrayList<>();
        for (Node cur = node; cur.getPrevious() != null; cur = cur.getPrevious()) {
            path.add("--(" + cur.getDistance() + ")-> " + cur.getId());
        }
        return String.join(" ", path.reversed());
    }

    public static void main(String[] args) throws IOException {
        Graph g = new Graph(Path.of("src/dijkstra/graphs/unzusammenhaengend_Graph_A-M.csv"));
        System.out.println(g);
        System.out.println(g.getAllPaths());
        g.calcWithDijkstra("A");
        System.out.println(g);
        System.out.println(g.getAllPaths());
    }
}