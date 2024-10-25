package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] parseMoveDirection(String[] moveDirection) {
        MoveDirection[] moveDirections = new MoveDirection[moveDirection.length];
        for (int i = 0; i < moveDirection.length; i++) {
            switch (moveDirection[i]) {
                case "f" -> moveDirections[i] = MoveDirection.FORWARD;
                case "r" -> moveDirections[i] = MoveDirection.RIGHT;
                case "l" -> moveDirections[i] = MoveDirection.LEFT;
                case "b" -> moveDirections[i] = MoveDirection.BACKWARD;
            }
        }
        return moveDirections;
    }
}
