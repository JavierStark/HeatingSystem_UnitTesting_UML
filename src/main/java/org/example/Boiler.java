package org.example;

public class Boiler implements Measurable, Tickable {
    private final CentralizedSystem system;
    private boolean state;
    private int temperature;

    private final int OFF_TEMPERATURE = 6;
    private final int TARGET_TEMPERATURE = 30;
    private final int DANGER_TEMPERATURE = 50;

    private boolean turningOff = false;

    public Boiler(CentralizedSystem system) {
        this.system = system;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean getState() {
        return state;
    }

    public void tick() {
        state = shouldBeOn();

        temperature += state? 1: -1;
        temperature = Math.max(temperature, 0);

        if(isCold()) {
            turningOff = false;
        }
        if(isDangerouslyHot()){
            turningOff = true;
        }
    }

    private boolean shouldBeOn() {
        return system.getRoomsThatNeedHeat() > 0 && !isDangerouslyHot() && !turningOff;
    }

    private boolean isDangerouslyHot() {
        return temperature >= DANGER_TEMPERATURE;
    }

    private boolean isCold() {
        return temperature <= OFF_TEMPERATURE;
    }
}
