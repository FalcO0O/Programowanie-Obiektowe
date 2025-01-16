package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionsParser {
    public static List<MoveDirection> parseMoveDirections(String[] moveDirection) {
        return Stream.of(moveDirection).map((direction) ->
                switch (direction) {
                    case "f" -> MoveDirection.FORWARD;
                    case "r" -> MoveDirection.RIGHT;
                    case "l" -> MoveDirection.LEFT;
                    case "b" -> MoveDirection.BACKWARD;
                    default -> throw new IllegalArgumentException(direction + " is not legal move specification");
                }
                ).collect(Collectors.toCollection(LinkedList::new));
    }
}
