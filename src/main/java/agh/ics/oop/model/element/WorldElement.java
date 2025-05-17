package agh.ics.oop.model.element;

import agh.ics.oop.model.WorldElementStatistics;
import agh.ics.oop.model.util.Vector2d;
import javafx.scene.layout.StackPane;

public interface WorldElement {
    /**
     * @return Vector2d representation of position
     */
    Vector2d getPosition();

    StackPane getResource();

    default boolean isSelectable() {
        return false;
    }

    default WorldElementStatistics getStatistics() {
        return null;
    }

}
