package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void placeAnimal() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(1, 1);
        assertTrue(map.place(animal), "Animal should be placed on the map at (1,1)");
        assertEquals(animal, map.objectAt(new Vector2d(1, 1)), "Animal should be accessible at (1,1)");
    }

    @Test
    void cannotPlaceAnimalOnOccupiedPosition() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(1, 1);
        Animal animal2 = new Animal(1, 1);
        map.place(animal1);
        assertFalse(map.place(animal2), "Should not place another animal at (1,1)");
    }

    @Test
    void moveAnimalWithinBounds() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(2, 2);
        map.place(animal);
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 3), animal.getPosition(), "Animal should move to (2,3)");
    }


    @Test
    void isOccupied() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(2, 2);
        map.place(animal);
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
        Animal animal = new Animal(2, 2);
        map.place(animal);
        map.move(animal, MoveDirection.LEFT);
        assertEquals(MapDirection.WEST, animal.getOrientation(), "Animal should turn LEFT to face WEST");
        map.move(animal, MoveDirection.RIGHT);
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Animal should turn RIGHT to face NORTH again");
    }

    @Test
    void moveAnimalToOccupiedPosition() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(2, 2);
        Animal animal2 = new Animal(2, 3);
        map.place(animal1);
        map.place(animal2);
        map.move(animal1, MoveDirection.FORWARD); // Try moving animal1 into animal2's position
        assertEquals(new Vector2d(2, 2), animal1.getPosition(), "Animal1 should not move into an occupied position");
    }

    @Test
    void multipleAnimalsOnMap() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(1, 1);
        Animal animal2 = new Animal(3, 3);
        Animal animal3 = new Animal(4, 4);
        map.place(animal1);
        map.place(animal2);
        map.place(animal3);
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
}