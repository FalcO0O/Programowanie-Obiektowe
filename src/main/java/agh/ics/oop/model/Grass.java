package agh.ics.oop.model;

import javafx.scene.image.Image;

public class Grass implements WorldElement {
    private final Vector2d position;
    private static final Image grassImage = new Image("grass.png");

    public Grass(Vector2d position)
    {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public Image getResource() {
        return grassImage;
    }

    @Override
    public String toString() {
        return "*";
    }
}
