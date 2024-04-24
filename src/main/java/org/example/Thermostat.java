package org.example;

public class Thermostat {

    private int targetTemp;

    public void setTemperature(int targetTemp){
        this.targetTemp = targetTemp;
    }
    public int getTemperature(){
        return targetTemp;
    }
}
