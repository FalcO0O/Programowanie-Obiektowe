package agh.ics.oop.model.map.exceptions;

import agh.ics.oop.model.util.Vector2d;

public class IncorrectPositionException extends Exception {
    public IncorrectPositionException(Vector2d position) {
        super("Position %s is not correct".formatted(position));
    }
}
