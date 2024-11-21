package agh.ics.oop.model;

public interface WorldElement {
    /**
     * @return Vector2d representation of position
     */
    Vector2d getPosition();

    /**
     * @return String representation of particular object
     */
    String toString();
}
