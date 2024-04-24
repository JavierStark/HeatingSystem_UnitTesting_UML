package org.example;

public class Termometer {
    Measurable systemToMeasure;
    public Termometer(Measurable system) {
        systemToMeasure = system;
    }

    public int measure() {
        return systemToMeasure.getTemperature();
    }
}
