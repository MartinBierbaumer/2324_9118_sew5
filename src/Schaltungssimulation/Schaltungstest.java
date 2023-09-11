package Schaltungssimulation;

public class Schaltungstest {
    public static void tick(Schaltung schaltungen[]) {
        for (Schaltung s: schaltungen) {
            s.tick();
        }
    }

    public static void main(String[] args) {
        Taster t1 = new Taster(), t2 = new Taster();
        FlipFlop f = new FlipFlop();
        Led l1 = new Led(), l2 = new Led();

        f.connect(t1, 0, 0);
        f.connect(t2, 0, 1);

        l1.connect(f, 0, 0);
        l2.connect(f, 1, 0);

        Schaltung s[] = new Schaltung[]{t1, t2, f, l1, l2};

        tick(s);
        System.out.println("L1 " + l1.getState() + " L2 " + l2.getState());

        t1.set(true);
        tick(s);
        t1.set(false);
        tick(s);
        System.out.println("L1 " + l1.getState() + " L2 " + l2.getState());

        t2.set(true);
        tick(s);
        t2.set(false);
        tick(s);
        System.out.println("L1 " + l1.getState() + " L2 " + l2.getState());
    }
}
