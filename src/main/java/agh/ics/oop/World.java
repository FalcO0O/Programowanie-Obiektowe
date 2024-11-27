package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {

    public static void main(String[] args) {
        GrassField map = new GrassField(10);
        ConsoleMapDisplay console = new ConsoleMapDisplay();
        map.addObserver(console);

        List<MoveDirection> directions;
        try
        {
            directions = OptionsParser.parseMoveDirections(args);
        } catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation = new Simulation(positions, directions, map);
        System.out.println(map); // print initial status
        simulation.run();
    }
}
