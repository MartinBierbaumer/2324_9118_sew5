package Schaltungssimulation;

public class FlipFlop extends SchaltungImpl {
    public boolean state = false;

    public FlipFlop() {
        super(2, 2);
    }

    public Boolean[] calc(Boolean[] input) {
        if (input[0]) state = true;
        if (input[1]) state = false;
        return new Boolean[]{state, !state};
    }
}
