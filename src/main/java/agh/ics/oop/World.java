package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {

    public static void main(String[] args) {
        ConsoleMapDisplay console = new ConsoleMapDisplay();
        List<MoveDirection> directions;
        try
        {
            directions = OptionsParser.parseMoveDirections(args);
            List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
            ArrayList<Simulation> simulations = new ArrayList<>();
            for (int i = 0; i < 1000; i++)
            {
                GrassField grassMap = new GrassField(10);
                RectangularMap rectangularMap = new RectangularMap(10, 10);
                grassMap.addObserver(console);
                rectangularMap.addObserver(console);
                simulations.add(new Simulation(positions, directions, grassMap));
                simulations.add(new Simulation(positions, directions, rectangularMap));
            }
            SimulationEngine Engine = new SimulationEngine(simulations);
            Engine.runAsyncInThreadPool();
            Engine.awaitSimulationsEnd();
        } catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("System finished running.");
    }
}
