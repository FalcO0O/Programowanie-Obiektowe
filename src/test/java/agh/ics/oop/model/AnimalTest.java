package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    public void initialOrientationAndPosition() {
        Animal animal = new Animal();
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Start orientation should be NORTH");
        assertEquals(new Vector2d(2, 2), animal.getPosition(), "Start position should be (2, 2)");
    }

    @Test
    public void customInitialPosition() {
        Animal animal = new Animal(3, 3);
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Start orientation should be NORTH");
        assertEquals(new Vector2d(3, 3), animal.getPosition(), "Start position should be (3, 3)");
    }

    @Test
    public void moveForwardWithinBounds() {
        // facing NORTH
        Animal animal = new Animal(2, 2);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 3), animal.getPosition(), "Animal should be at (2, 3)");

        // facing EAST
        animal = new Animal(2, 2);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(3, 2), animal.getPosition(), "Animal should be at (3, 2)");

        // facing SOUTH
        animal = new Animal(2, 2);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 1), animal.getPosition(), "Animal should be at (2, 1)");

        // facing WEST
        animal = new Animal(2, 2);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(1, 2), animal.getPosition(), "Animal should be at (1, 2)");
    }

    @Test
    public void moveBackwardWithinBounds() {
        // facing NORTH
        Animal animal = new Animal(2, 2);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(2, 1), animal.getPosition(), "Animal should be at (2, 1)");

        // facing EAST
        animal = new Animal(2, 2);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(1, 2), animal.getPosition(), "Animal should be at (1, 2)");

        // facing SOUTH
        animal = new Animal(2, 2);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(2, 3), animal.getPosition(), "Animal should be at (2, 3)");

        // facing WEST
        animal = new Animal(2, 2);
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(3, 2), animal.getPosition(), "Animal should be at (3, 2)");
    }

    @Test
    public void turnLeft() {
        Animal animal = new Animal(2, 2);
        animal.move(MoveDirection.LEFT);
        assertEquals(MapDirection.WEST, animal.getOrientation(), "Animal should face WEST");

        animal.move(MoveDirection.LEFT);
        assertEquals(MapDirection.SOUTH, animal.getOrientation(), "Animal should face SOUTH");

        animal.move(MoveDirection.LEFT);
        assertEquals(MapDirection.EAST, animal.getOrientation(), "Animal should face EAST");

        animal.move(MoveDirection.LEFT);
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Animal should face NORTH");
    }

    @Test
    public void turnRight() {
        Animal animal = new Animal(2, 2);
        animal.move(MoveDirection.RIGHT);
        assertEquals(MapDirection.EAST, animal.getOrientation(), "Animal should face EAST");

        animal.move(MoveDirection.RIGHT);
        assertEquals(MapDirection.SOUTH, animal.getOrientation(), "Animal should face SOUTH");

        animal.move(MoveDirection.RIGHT);
        assertEquals(MapDirection.WEST, animal.getOrientation(), "Animal should face WEST");

        animal.move(MoveDirection.RIGHT);
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Animal should face NORTH");
    }

    @Test
    public void moveOutsideBounds() {
        // left down corner
        Animal animal = new Animal(0, 0);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(new Vector2d(0, 0), animal.getPosition(), "Animal should not move outside the map");

        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(0, 0), animal.getPosition(), "Animal should not move outside the map");

        // right upper corner
        animal = new Animal(4, 4);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 4), animal.getPosition(), "Animal should not move outside the map");

        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 4), animal.getPosition(), "Animal should not move outside the map");

    }

    @Test
    public void moveForwardToMapBoundary() {
        Animal animal = new Animal(4, 3);
        animal.move(MoveDirection.FORWARD);
        assertEquals(new Vector2d(4, 4), animal.getPosition(), "Animal outside the map");
    }

    @Test
    public void isAt() {
        Animal animal = new Animal(2, 2);
        Animal animal2 = new Animal(0, 0);
        assertTrue(animal.isAt(new Vector2d(2, 2)), "Animal should be at (2, 2)");
        assertTrue(animal2.isAt(new Vector2d(0, 0)), "Animal should be at (0, 0)");
        assertFalse(animal.isAt(new Vector2d(2, 3)), "Animal should not be at (2, 3)");
        assertFalse(animal2.isAt(new Vector2d(1, 0)), "Animal should not be at (1, 0)");
    }


    @Test
    public void complexMovement() {
        Animal animal = new Animal(2, 2);
        String[] commands = {"f", "r", "f", "l", "b"};
        List<MoveDirection> directions = OptionsParser.parseMoveDirections(commands);
        for (MoveDirection direction : directions) {
            animal.move(direction);
        }
        assertEquals(new Vector2d(3, 2), animal.getPosition(), "Animal should be at (3, 2)");
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Animal should face NORTH");
    }
}