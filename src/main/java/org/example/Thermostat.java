package org.example;

public class Thermostat {

    private int targetTemp;
    private CentralizedSystem cs;

    public Thermostat(CentralizedSystem cs) {
        super();
        this.cs = cs;
    }

    public void setTemperature(int targetTemp, int currentTemp){
        this.targetTemp = targetTemp;
        if(targetTemp > currentTemp){
            cs.needHeat();
        }
        else{
            cs.dontNeedHeat();
        }
    }
    public int getTemperature(){
        return targetTemp;
    }
}
