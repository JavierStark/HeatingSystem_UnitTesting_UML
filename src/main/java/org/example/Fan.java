package org.example;

public class Fan implements Gate, Tickable{
    private final Termometer termometer;
    private boolean state;

    public Fan(Termometer termometer) {
        this.termometer = termometer;
    }

    public int transpire() {
        if(state)
            return termometer.measure();
        return 0;
    }

    private void on() {
        state = true;
    }

    private void off() {
        state = false;
    }

    public void tick(){
        if(termometer.measure() >= 30)
            on();
        else
            off();
    }
}
