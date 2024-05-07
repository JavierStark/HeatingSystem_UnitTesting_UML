package org.example;

public class Room implements Measurable, Tickable{
    private final Valve valve;
    private final Thermostat thermostat;
    private int temperature;

    public Room(Valve valve){
        this.valve = valve;
        this.thermostat = new Thermostat();
        this.temperature = 15;
    }

    public void tick(){
        if(thermostat.getTemperature() != 0 && temperature < thermostat.getTemperature())
            valve.open();
        else
            valve.close();

        if(valve.transpire() > 0)
            temperature++;
    }

    public void setTemperature(int targetTemp){
        thermostat.setTemperature(targetTemp, this.temperature);
    }

    public int getTemperature() {
        return temperature;
    }
}
