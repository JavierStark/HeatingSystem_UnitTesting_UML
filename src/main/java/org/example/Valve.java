package org.example;

public class Valve {
    private final Gate gate;
    private boolean open;

    public Valve(Gate gate) {
        this.gate = gate;
    }

    public int transpire() {
        if(open)
            return gate.transpire();
        return 0;
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }
}
