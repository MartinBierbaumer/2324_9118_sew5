package RIP;

public class Packet {
    int destination;
    int source;
    int TTL;
    int type;
    int message;

    public Packet(int destination, int source, int type, int TTL, int message) {
        this.destination = destination;
        this.source = source;
        this.type = type;
        this.TTL = TTL;
        this.message = message;
    }

    Packet next() {
        return new Packet(destination, source, type,TTL - 1, message);
    }
}
