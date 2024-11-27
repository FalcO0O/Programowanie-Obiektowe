package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import java.util.LinkedList;
import java.util.List;

public class OptionsParser {
    public static List<MoveDirection> parseMoveDirections(String[] moveDirection) {
        List<MoveDirection> moveDirections = new LinkedList<>(); // ze względu na częste dopisywanie lepszym wyborem jest LinkedList
        for (String direction : moveDirection) {
            switch (direction) {
                case "f" -> moveDirections.add(MoveDirection.FORWARD);
                case "r" -> moveDirections.add(MoveDirection.RIGHT);
                case "l" -> moveDirections.add(MoveDirection.LEFT);
                case "b" -> moveDirections.add(MoveDirection.BACKWARD);
                default -> throw new IllegalArgumentException(direction + " is not legal move specification");
            }
        }
        return moveDirections;
    }
}
