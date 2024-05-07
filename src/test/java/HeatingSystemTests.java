import org.example.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HeatingSystemTests {
    CentralizedSystem CS;

    @BeforeEach
    public void setUp() {
        CS = new CentralizedSystem();
    }

    @Test
    public void boilerStartsOffAndCold() {
        var sut = new Boiler(CS);

        assertFalse(sut.getState());
        assertEquals(0, sut.getTemperature());
    }

    @Test
    public void boilerTurnsOnWhenRoomsNeedHeat() {
        var sut = new Boiler(CS);
        CS.needHeat();
        sut.tick();
        assertTrue(sut.getState());
    }

    @Test
    public void boilerTurnOffWhenRoomsDontNeedHeat() {
        var sut = new Boiler(CS);
        CS.needHeat();
        CS.dontNeedHeat();
        sut.tick();
        assertFalse(sut.getState());
    }

    @Test
    public void boilerTurnsOffWhenTooHot() {
        var sut = new Boiler(CS);
        CS.needHeat();
        for (int i = 0; i < 51; i++)
            sut.tick();
        assertFalse(sut.getState());
    }

    @Test
    public void boilerDoesntRestartUntilCooldown() {
        var sut = new Boiler(CS);
        CS.needHeat();
        for (int i = 0; i < 51; i++)
            sut.tick();
        assertFalse(sut.getState());
        for (int i = 0; i < 51; i++)
            sut.tick();
        assertTrue(sut.getState());
    }

    @Test
    public void fanTurnsOnAndTranspireWhenReachedTemperature() {
        var measurable = mock(Measurable.class);
        when(measurable.getTemperature()).thenReturn(30);
        var sut = new Fan(new Termometer(measurable));
        sut.tick();
        assertEquals(30, sut.transpire());
    }

    @Test
    public void fanDoesNotTurnOnWhenTemperatureIsNotReached() {
        var measurable = mock(Measurable.class);
        when(measurable.getTemperature()).thenReturn(8);
        var sut = new Fan(new Termometer(measurable));
        sut.tick();
        assertEquals(0, sut.transpire());
    }

    @Test
    public void valveTranspiresWhenOpen() {
        var gate = mock(Gate.class);
        when(gate.transpire()).thenReturn(10);
        var sut = new Valve(gate);
        sut.open();
        assertEquals(10, sut.transpire());
    }

    @Test
    public void valveDoesNotTranspireByDefault() {
        var gate = mock(Gate.class);
        when(gate.transpire()).thenReturn(10);
        var sut = new Valve(gate);
        assertEquals(0, sut.transpire());
    }

    @Test
    public void roomClosesValveWhenNoHeat() {
        var valve = mock(Valve.class);
        var sut = new Room(valve,CS);
        sut.tick();
        verify(valve).close();
    }

    @Test
    public void roomOpensValveWhenHeat() {
        var valve = mock(Valve.class);
        var sut = new Room(valve,CS);
        sut.setTemperature(20);
        sut.tick();
        verify(valve).open();
    }

    @Test
    public void roomTranspiresTemperature() {
        var valve = mock(Valve.class);
        when(valve.transpire()).thenReturn(10);
        var sut = new Room(valve,CS);
        sut.setTemperature(20);
        sut.tick();
        assertEquals(15 + 1, sut.getTemperature());
    }

    @Test
    public void whenANewRequiredTempLowerThanActualIsRecevedRoomDoesNotRequestsHeat() {
        var valve = mock(Valve.class);
        var sut = new Room(valve, CS);
        assertEquals(15, sut.getTemperature());
        sut.setTemperature(0);
        assertEquals(0, CS.getRoomsThatNeedHeat());
    }

    @Test
    public void whenANewRequiredTempLowerThanActualIsRecevedRoomClosesValve() {
        var valve = mock(Valve.class);
        var sut = new Room(valve, CS);
        sut.setTemperature(0);
        sut.tick();
        verify(valve).close();
    }

    @Test
    public void whenANewRequiredTempHigherThanActualIsRecevedRoomRequestsHeat() {
        var valve = mock(Valve.class);
        var sut = new Room(valve, CS);
        sut.setTemperature(20);
        assertEquals(1, CS.getRoomsThatNeedHeat());
    }

    @Test
    public void whenANewRequiredTempHigherThanActualIsRecevedRoomOpensValvet() {
        var valve = mock(Valve.class);
        var sut = new Room(valve, CS);
        sut.setTemperature(20);
        sut.tick();
        verify(valve).open();
    }

    @Test
    public void whenTheFirstRoomRequestsHeatBoilerISSwitchedOn (){
        var sut = new Boiler(CS);
        var valve = mock(Valve.class);
        var room = new Room(valve, CS);
        room.setTemperature(20);
        room.tick();
        sut.tick();
        assertTrue(sut.getState());
    }
    @Test
    public void whenTheLastRoomRequestingHeatIsWarmBoilerISSwitchedOff (){
        var sut = new Boiler(CS);
        var valve = mock(Valve.class);
        var room = new Room(valve, CS);
        room.setTemperature(20);
        for (int i = 0; i < 5; i++)
            room.tick();
        room.setTemperature(0);
        room.tick();
        sut.tick();
        assertFalse(sut.getState());
    }
    @Test
    public void whenTheTemperatureExceedsTheMaximumBoilerISSwitchedOff (){
        var sut = new Boiler(CS);
        var valve = mock(Valve.class);
        var room = new Room(valve, CS);
        room.setTemperature(20);
        room.tick();
        for (int i = 0; i < 51; i++)
            sut.tick();
        assertFalse(sut.getState());
    }
    @Test
    public void whenTheTemperatureIsLowerThanTheOperationTemperatureTheFanIsOff (){
        var measurable = mock(Measurable.class);
        when(measurable.getTemperature()).thenReturn(0);
        var sut = new Fan(new Termometer(measurable));
        sut.tick();
        assertEquals(0, sut.transpire());
    }

     @Test
     public void whenTheTemperatureIsHigherThanTheOperationTemperatureTheFanIsOn (){
        var measurable = mock(Measurable.class);
        when(measurable.getTemperature()).thenReturn(31);
        var sut = new Fan(new Termometer(measurable));
        sut.tick();
        assertEquals(31, sut.transpire());
     }
}
