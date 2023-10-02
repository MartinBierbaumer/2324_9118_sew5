package RIP;

public class test {
    public static void main(String[] args) {
        Router a = new Router();
        Router b = new Router();
        Router c = new Router();

        a.connect(b);
        b.connect(a);

        b.connect(c);
        c.connect(b);

        a.addRoute(new Subnet("1.2.3.4"));
        b.addRoute(new Subnet("0.0.0.1"));
        c.addRoute(new Subnet("0.0.0.0/0"));

        for (int i = 0; i < 3; i++) {
            a.tick();
            b.tick();
            c.tick();
        }

        a.ping(new Subnet("1.1.1.1"));

        a.tracert(new Subnet("0.0.0.1"), 5);
        a.tracert(new Subnet("1.1.1.1"), 5);
    }
}
