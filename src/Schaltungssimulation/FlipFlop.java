package Schaltungssimulation;

public class FlipFlop extends SchaltungImpl {
    private Boolean state = false;

    public FlipFlop() {
        super(2, 2);
    }

    @Override
    protected Boolean[] calc(Boolean[] input) {
        if (input[0]) state = true;
        if (input[1]) state = false;
        return new Boolean[]{state, !state};
    }
}
