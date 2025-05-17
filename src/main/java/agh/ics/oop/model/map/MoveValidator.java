package agh.ics.oop.model.map;

import agh.ics.oop.model.util.Vector2d;

public interface MoveValidator {

    boolean isOutOfHorizontalBounds(Vector2d position);

    boolean isOutOfVerticalBounds(Vector2d position);

    Vector2d getCorrectedHorizontalPosition(Vector2d position);

}
