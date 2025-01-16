package agh.ics.oop.model;

import javafx.scene.image.Image;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;

    private final static Image UP_IMAGE = new Image("/up.png");
    private final static Image DOWN_IMAGE = new Image("/down.png");
    private final static Image LEFT_IMAGE = new Image("/left.png");
    private final static Image RIGHT_IMAGE = new Image("/right.png");

    public Animal()
    {
        position = new Vector2d(2, 2);
        orientation = MapDirection.NORTH;
    }
    public Animal(Vector2d position)
    {
        orientation = MapDirection.NORTH;
        this.position = position;
    }

    @Override
    public String toString() {
        return switch (orientation) {
            case NORTH -> "^";
            case SOUTH -> "v";
            case EAST -> ">";
            case WEST -> "<";
        };
    }

    public boolean isAt(Vector2d position)
    {
        return position.equals(this.position);
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        Vector2d newPosition = null;
        switch (direction) {
            case LEFT -> orientation = orientation.previous();
            case RIGHT -> orientation = orientation.next();
            case FORWARD -> newPosition = this.position.add(orientation.toUnitVector());
            case BACKWARD -> newPosition = this.position.add(orientation.toUnitVector().opposite());
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
        if (newPosition != null && validator.canMoveTo(newPosition)) {
            this.position = newPosition;
        }
    }

    public Vector2d getPosition()
    {
        return position;
    }

    @Override
    public Image getResource() {
        return switch(orientation)
        {
            case NORTH -> UP_IMAGE;
            case SOUTH -> DOWN_IMAGE;
            case EAST -> RIGHT_IMAGE;
            case WEST -> LEFT_IMAGE;
        };
    }

    public MapDirection getOrientation()
    {
        return orientation;
    }

}
