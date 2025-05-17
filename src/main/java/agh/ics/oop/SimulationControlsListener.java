package agh.ics.oop;

public interface SimulationControlsListener {

    void stop();

    void resume();

    void setTickRate(int tickRate);

}
