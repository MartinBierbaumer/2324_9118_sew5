package Schaltungssimulation;

public class Taster extends SchaltungImpl {
    private Boolean state = false;

    public Taster() {
        super(0, 1);
    }

    @Override
    protected Boolean[] calc(Boolean input[]) {
        return new Boolean[]{state};
    }

    public void set(Boolean value) {
        state = value;
    }
}
