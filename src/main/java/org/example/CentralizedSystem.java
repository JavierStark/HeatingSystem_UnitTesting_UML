package org.example;

public class CentralizedSystem {
    private int roomsThatNeedHeat;
    public int getHouseNeedsHeat(){
        return roomsThatNeedHeat;
    }
    public void needHeat(){
        roomsThatNeedHeat++;
    }
    public void dontNeedHeat(){
        roomsThatNeedHeat--;
        roomsThatNeedHeat = Math.max(0, roomsThatNeedHeat);
    }
    public int getRoomsThatNeedHeat(){
        return roomsThatNeedHeat;
    }
}
