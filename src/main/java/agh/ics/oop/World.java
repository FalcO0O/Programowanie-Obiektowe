package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Simulation;
import agh.ics.oop.model.Vector2d;

import java.util.List;

public class World {

    public static void main(String[] args) {
        Animal Zwierze = new Animal();
        System.out.println(Zwierze.toString());

        List<MoveDirection> directions = OptionsParser.parseMoveDirections(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        Simulation simulation = new Simulation(positions, directions);
        simulation.run();
        System.out.println("hgello world");
    }

}
