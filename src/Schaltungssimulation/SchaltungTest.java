package Schaltungssimulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SchaltungTest {
    public void tick(Schaltung schaltungen[]) {
        for (Schaltung s: schaltungen) {
            s.tick();
        }
    }

    @Test
    public void test() {
        Taster t1 = new Taster(), t2 = new Taster();
        FlipFlop f = new FlipFlop();
        Led l1 = new Led(), l2 = new Led();

        f.connect(t1, 0, 0);
        f.connect(t2, 0, 1);

        l1.connect(f, 0, 0);
        l2.connect(f, 1, 0);

        Schaltung s[] = new Schaltung[]{t1, t2, f, l1, l2};

        tick(s);
        Assertions.assertEquals(l1.getState(), false);
        Assertions.assertEquals(l2.getState(), true);

        t1.set(true);
        tick(s);
        t1.set(false);
        tick(s);
        Assertions.assertEquals(l1.getState(), true);
        Assertions.assertEquals(l2.getState(), false);

        t2.set(true);
        tick(s);
        t2.set(false);
        tick(s);
        Assertions.assertEquals(l1.getState(), false);
        Assertions.assertEquals(l2.getState(), true);
    }
}