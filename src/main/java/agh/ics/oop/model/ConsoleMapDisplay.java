package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int useNumber = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        System.out.println("Map ID: " + worldMap.getID());
        System.out.println(message);
        System.out.println(worldMap);
        System.out.printf("Total number of updates: %s\n\n", ++useNumber); // \n for better formatting
    }
}
