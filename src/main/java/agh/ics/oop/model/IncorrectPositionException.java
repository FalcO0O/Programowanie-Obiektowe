package agh.ics.oop.model;

public class IncorrectPositionException extends Exception {
    public IncorrectPositionException(Vector2d position) {
        System.out.println("Position %s is not correct".formatted(position));
    }
}
