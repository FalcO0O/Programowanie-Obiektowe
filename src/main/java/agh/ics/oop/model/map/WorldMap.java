package agh.ics.oop.model.map;

import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.map.exceptions.IncorrectPositionException;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends ReadOnlyWorldMap {

    /**
     * Place an animal on the map and register its birth.
     * @param animal The animal to place on the map.
     */
    void place(Animal animal) throws IncorrectPositionException;

    /**
     * Remove animal from the map and register its death.
     * @param animal The animal to remove
     */
    void remove(Animal animal);

    /**
     * Moves an animal (if it is present on the map) according to its genotype.
     */
    void move(Animal animal);

    /**
     * Makes animals eat available plants
     */
    void handlePlantConsumption();

    /**
     * Grows new plants
     */
    void createNewPlants();

    /**
     * Updates map statistics which need to be calculated daily
     */
    void updateStatistics();

    void setDay(int day);

    void runSpecialEvents();

}
