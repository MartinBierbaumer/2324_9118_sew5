package Schaltungssimulation;

public interface Schaltung {
    void connect(Schaltung source, int portSource, int portThis);
    void tick();
    Boolean get(int output);
}
