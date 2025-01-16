package agh.ics.oop.model;

import javafx.scene.image.Image;

public interface WorldElement {
    /**
     * @return Vector2d representation of position
     */
    Vector2d getPosition();

    Image getResource();

    /**
     * @return String representation of particular object
     */
    String toString();
}
