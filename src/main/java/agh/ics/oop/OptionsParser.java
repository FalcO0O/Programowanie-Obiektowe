package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static MoveDirection[] parseMoveDirections(String[] moveDirection) {
        List<MoveDirection> moveDirections = new ArrayList<>();
        for (String direction : moveDirection) {
            switch (direction) {
                case "f" -> moveDirections.add(MoveDirection.FORWARD);
                case "r" -> moveDirections.add(MoveDirection.RIGHT);
                case "l" -> moveDirections.add(MoveDirection.LEFT);
                case "b" -> moveDirections.add(MoveDirection.BACKWARD);
            }
        }
        return moveDirections.toArray(new MoveDirection[0]);
    }
}
