package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void equalsSameVectors() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);

        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));
    }
    @Test
    void equalsDifferentVectors() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(-1,-2);

        assertFalse(v1.equals(v2));
        assertFalse(v2.equals(v1));
    }

    @Test
    void testToString() {
        Vector2d v1 = new Vector2d(2, 3);
        assertEquals("(2, 3)", v1.toString());
    }

    @Test
    void testPrecedes() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d v3 = new Vector2d(2, 2);

        assertTrue(v1.precedes(v2));
        assertFalse(v2.precedes(v1));
        assertTrue(v3.precedes(v1));
    }

    @Test
    void testFollows() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d v3 = new Vector2d(2, 2);

        assertTrue(v2.follows(v1));
        assertFalse(v1.follows(v2));
        assertFalse(v3.follows(v1));
    }

    @Test
    void testUpperRight() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 1);
        Vector2d expected = new Vector2d(4, 3);

        assertEquals(expected, v1.upperRight(v2));
    }

    @Test
    void testLowerLeft() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 1);
        Vector2d expected = new Vector2d(2, 1);

        assertEquals(expected, v1.lowRight(v2));
    }

    @Test
    void testAdd() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d expected = new Vector2d(6, 8);

        assertEquals(expected, v1.add(v2));
    }

    @Test
    void testSubtract() {
        Vector2d v1 = new Vector2d(4, 5);
        Vector2d v2 = new Vector2d(2, 3);
        Vector2d expected = new Vector2d(2, 2);

        assertEquals(expected, v1.subtract(v2));
    }

    @Test
    void testOpposite() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d expected = new Vector2d(-2, -3);

        assertEquals(expected, v1.opposite(v1), "Opposite of v1 should equal expected");
    }
}