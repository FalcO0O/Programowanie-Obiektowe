package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest { // stare testy dalej działają z interfejsem WorldMap

    @Test
    void placeAnimal() {
        RectangularMap map = new RectangularMap(5, 5);
        Vector2d vector2d = new Vector2d(1,1);
        Animal animal = new Animal(vector2d);
        assertDoesNotThrow(() -> map.place(animal), "Animal should be placed on the map at (1,1)");
        assert map.objectAt(vector2d).isPresent();
        assertEquals(animal, map.objectAt(vector2d).get(), "Animal should be accessible at (1,1)");
    }

    @Test
    void cannotPlaceAnimalOnOccupiedPosition() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        Animal animal2 = new Animal(new Vector2d(1, 1));
        assertDoesNotThrow(() -> map.place(animal1));
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2), "Should not place another animal at (1,1)");
    }

    @Test
    void moveAnimalWithinBounds() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));
        assertDoesNotThrow(() -> map.place(animal), "Animal should be placed at (2,2)");
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 3), animal.getPosition(), "Animal should move to (2,3)");
    }


    @Test
    void isOccupied() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));
        assertDoesNotThrow(() -> map.place(animal), "Animal should be placed at (2,2)");
        assertTrue(map.isOccupied(new Vector2d(2, 2)), "Position (2,2) should be occupied");
    }

    @Test
    void isNotOccupied() {
        RectangularMap map = new RectangularMap(5, 5);
        assertFalse(map.isOccupied(new Vector2d(3, 3)), "Position (3,3) should not be occupied");
    }

    @Test
    void canMoveToEmptyPosition() {
        RectangularMap map = new RectangularMap(5, 5);
        assertTrue(map.canMoveTo(new Vector2d(3, 3)), "Should be able to move to empty position (3,3)");
    }

    @Test
    void moveAnimalLeftAndRight() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(new Vector2d(2, 2));
        assertDoesNotThrow(() -> map.place(animal), "Animal should be placed at (2,2)");
        map.move(animal, MoveDirection.LEFT);
        assertEquals(MapDirection.WEST, animal.getOrientation(), "Animal should turn LEFT to face WEST");
        map.move(animal, MoveDirection.RIGHT);
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Animal should turn RIGHT to face NORTH again");
    }

    @Test
    void moveAnimalToOccupiedPosition() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 3));
        assertDoesNotThrow(() -> map.place(animal1), "Animal should be placed at (2,2)");
        assertDoesNotThrow(() -> map.place(animal2), "Animal should be placed at (2,2)");
        map.move(animal1, MoveDirection.FORWARD); // Try moving animal1 into animal2's position
        assertEquals(new Vector2d(2, 2), animal1.getPosition(), "Animal1 should not move into an occupied position");
    }

    @Test
    void multipleAnimalsOnMap() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(new Vector2d(1, 1));
        Animal animal2 = new Animal(new Vector2d(3, 3));
        Animal animal3 = new Animal(new Vector2d(4, 4));
        assertDoesNotThrow(() -> map.place(animal1), "Animal should be placed at (2,2)");
        assertDoesNotThrow(() -> map.place(animal2), "Animal should be placed at (2,2)");
        assertDoesNotThrow(() -> map.place(animal3), "Animal should be placed at (2,2)");
        assertTrue(map.isOccupied(new Vector2d(1, 1)), "Position (1,1) should be occupied by animal1");
        assertTrue(map.isOccupied(new Vector2d(3, 3)), "Position (3,3) should be occupied by animal2");
        assertTrue(map.isOccupied(new Vector2d(4, 4)), "Position (4,4) should be occupied by animal3");
    }

    @Test
    void mapBoundaries() {
        RectangularMap map = new RectangularMap(5, 5);
        assertFalse(map.canMoveTo(new Vector2d(-1, 0)), "Map should not allow movement outside its lower boundaries");
        assertFalse(map.canMoveTo(new Vector2d(0, -1)), "Map should not allow movement outside its lower boundaries");
        assertFalse(map.canMoveTo(new Vector2d(5, 0)), "Map should not allow movement outside its upper boundaries");
        assertFalse(map.canMoveTo(new Vector2d(0, 5)), "Map should not allow movement outside its upper boundaries");
    }

    @Test
    void isOrderedCollectionSorted()
    {
        RectangularMap map = new RectangularMap(10, 10);
        Animal animal1 = new Animal(new Vector2d(2, 6));
        Animal animal2 = new Animal(new Vector2d(3, 9));
        Animal animal3 = new Animal(new Vector2d(9, 5));
        Animal animal4 = new Animal(new Vector2d(2, 4));
        ArrayList<Animal> animalsToPlace = new ArrayList<>(List.of(animal1, animal2, animal3, animal4));
        animalsToPlace.forEach(
                animal -> assertDoesNotThrow(
                        () -> map.place(animal), "Error while placing animals"
                ));

        assertEquals(map.getOrderedAnimals(), List.of(animal4, animal1, animal2, animal3));
    }
}