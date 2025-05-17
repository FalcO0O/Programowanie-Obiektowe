package agh.ics.oop.model.map;

import agh.ics.oop.model.WorldMapStatisticsProvider;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.SortedAnimalGroup;
import agh.ics.oop.model.element.WorldElement;
import agh.ics.oop.model.util.Vector2d;

import java.util.List;
import java.util.UUID;

public interface ReadOnlyWorldMap extends MoveValidator {

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    boolean positionContainsElement(WorldElement element, Vector2d position);

    UUID getID();

    void addObserver(MapChangeListener observer);

    void mapUpdated(String message);

    /**
     * Returns animals grouped by positions and sorted using AnimalComparator
     */
    List<SortedAnimalGroup> getAnimalGroups();

    int getWidth();

    int getHeight();

    WorldMapStatisticsProvider getStatistics();

    WorldElement primaryObjectAt(Vector2d position);

    int getDay();

    List<Vector2d> getPriorityPlantPositions();

    List<Animal> getAnimalsWithDominantGenotype();

}
