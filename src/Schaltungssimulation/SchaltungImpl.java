package Schaltungssimulation;

record Port(Schaltung schaltung, int port) {}

public abstract class SchaltungImpl implements Schaltung {
    Port inputs[];
    Boolean outputs[];
    public SchaltungImpl(int inputs, int outputs) {
        this.inputs = new Port[inputs];
        this.outputs = new Boolean[outputs];
        tick();
    }

    @Override
    public void connect(Schaltung source, int portSource, int portThis) {
        inputs[portThis] = new Port(source, portSource);
    }

    @Override
    public void tick() {
        Boolean in[] = new Boolean[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            in[i] = inputs[i] != null && inputs[i].schaltung().get(inputs[i].port());
        }
        Boolean out[] = calc(in);
        if (out.length != outputs.length) throw new RuntimeException("Output hat falsche Länge");
        outputs = out;
    }

    protected abstract Boolean[] calc(Boolean input[]);

    @Override
    public Boolean get(int port) {
        return outputs[port];
    }
}
