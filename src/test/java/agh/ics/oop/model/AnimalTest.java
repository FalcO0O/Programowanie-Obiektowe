package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void defaultConstructor() {
        Animal animal = new Animal();
        assertEquals(new Vector2d(2, 2), animal.getPosition(), "Animal should start at (2,2)");
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Animal should start facing NORTH");
    }

    @Test
    void parameterizedConstructor() {
        Animal animal = new Animal(new Vector2d(3,4));
        assertEquals(new Vector2d(3, 4), animal.getPosition(), "Animal should be at (3,4)");
        assertEquals(MapDirection.NORTH, animal.getOrientation(), "Animal should start facing NORTH");
    }

    @Test
    void moveForwardFacingNorth() {
        Animal animal = new Animal(new Vector2d(2,2));
        animal.move(MoveDirection.FORWARD, pos -> true); // wyrażenie lambda zwracające zawsze true, dzięki czemu nie trzeba tworzyć mapy
        assertEquals(new Vector2d(2, 3), animal.getPosition(), "Animal should move forward to (2,3)");
    }

    @Test
    void moveForwardFacingEast() {
        Animal animal = new Animal(new Vector2d(2,2));
        animal.move(MoveDirection.RIGHT, pos -> true); // turn EAST
        animal.move(MoveDirection.FORWARD, pos -> true);
        assertEquals(new Vector2d(3, 2), animal.getPosition(), "Animal should move forward to (3,2)");
    }

    @Test
    void moveForwardFacingSouth() {
        Animal animal = new Animal(new Vector2d(2,2));
        animal.move(MoveDirection.RIGHT, pos -> true); // turn EAST
        animal.move(MoveDirection.RIGHT, pos -> true); // turn SOUTH
        animal.move(MoveDirection.FORWARD, pos -> true);
        assertEquals(new Vector2d(2, 1), animal.getPosition(), "Animal should move forward to (2,1)");
    }

    @Test
    void moveForwardFacingWest() {
        Animal animal = new Animal(new Vector2d(2,2));
        animal.move(MoveDirection.LEFT, pos -> true); // turn WEST
        animal.move(MoveDirection.FORWARD, pos -> true);
        assertEquals(new Vector2d(1, 2), animal.getPosition(), "Animal should move forward to (1,2)");
    }

    @Test
    void blockedForwardMovement() {
        Animal animal = new Animal(new Vector2d(2,2));
        animal.move(MoveDirection.FORWARD, pos -> pos.getY() != 3); // Block y = 3
        assertEquals(new Vector2d(2, 2), animal.getPosition(), "Animal should not move");
    }

    @Test
    void rotateFullCircles() {
        Animal animal_r = new Animal();
        Animal animal_l = new Animal();

        animal_r.move(MoveDirection.RIGHT, pos -> true); // EAST
        animal_r.move(MoveDirection.RIGHT, pos -> true); // SOUTH
        animal_r.move(MoveDirection.RIGHT, pos -> true); // WEST
        animal_r.move(MoveDirection.RIGHT, pos -> true); // NORTH
        assertEquals(MapDirection.NORTH, animal_r.getOrientation(), "Animal should be facing NORTH after full rotation");

        animal_l.move(MoveDirection.LEFT, pos -> true); // WEST
        animal_l.move(MoveDirection.LEFT, pos -> true); // SOUTH
        animal_l.move(MoveDirection.LEFT, pos -> true); // EAST
        animal_l.move(MoveDirection.LEFT, pos -> true); // NORTH
        assertEquals(MapDirection.NORTH, animal_l.getOrientation(), "Animal should face NORTH after four left turns");
    }

    @Test
    void stringRepresentation() {
        Animal animal = new Animal();
        assertEquals("^", animal.toString(), "Animal facing NORTH should be represented by '^'");
        animal.move(MoveDirection.RIGHT, pos -> true);
        assertEquals(">", animal.toString(), "Animal facing EAST should be represented by '>'");
        animal.move(MoveDirection.RIGHT, pos -> true);
        assertEquals("v", animal.toString(), "Animal facing SOUTH should be represented by 'v'");
        animal.move(MoveDirection.RIGHT, pos -> true);
        assertEquals("<", animal.toString(), "Animal facing WEST should be represented by '<'");
    }

    @Test
    void moveBackwardFacingNorth() {
        Animal animal = new Animal(new Vector2d(2,2));
        animal.move(MoveDirection.BACKWARD, pos -> true);
        assertEquals(new Vector2d(2, 1), animal.getPosition(), "Animal should move backward to (2,1)");
    }

    @Test
    void moveBackwardFacingWest() {
        Animal animal = new Animal(new Vector2d(2,2));
        animal.move(MoveDirection.LEFT, pos -> true); // turn WEST
        animal.move(MoveDirection.BACKWARD, pos -> true);
        assertEquals(new Vector2d(3, 2), animal.getPosition(), "Animal should move backward to (3,2)");
    }

    @Test
    void positionAfterMultipleMoves() {
        Animal animal = new Animal( new Vector2d(2,2));
        animal.move(MoveDirection.FORWARD, pos -> true);
        animal.move(MoveDirection.RIGHT, pos -> true);
        animal.move(MoveDirection.FORWARD, pos -> true);
        animal.move(MoveDirection.LEFT, pos -> true);
        animal.move(MoveDirection.BACKWARD, pos -> true);
        assertEquals(new Vector2d(3, 2), animal.getPosition(), "Animal should end up at (3,2) after sequence of moves");
    }

    @Test
    void moveToBlockedPosition() {
        Animal animal = new Animal(new Vector2d(2,2));
        animal.move(MoveDirection.FORWARD, pos -> pos.getY() != 3); // Block position (2,3)
        assertEquals(new Vector2d(2, 2), animal.getPosition(), "Animal should not move to blocked position (2,3)");
    }


}