package Schaltungssimulation;

public class Led extends SchaltungImpl {
    private Boolean state = false;

    public Led() {
        super(1, 0);
    }

    @Override
    protected Boolean[] calc(Boolean[] input) {
        state = input[0];
        return new Boolean[0];
    }

    public Boolean getState() {
        return state;
    }
}
