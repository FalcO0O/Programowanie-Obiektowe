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
        Vector2d v4 = new Vector2d(2, 3);
        Vector2d v5 = new Vector2d(1, 3);
        Vector2d v6 = new Vector2d(2, 4);
        Vector2d v7 = new Vector2d(5, 1);

        assertTrue(v1.precedes(v2));  // x i y mniejsze w v1 niż w v2
        assertTrue(v3.precedes(v1));  // x równy, y mniejszy
        assertTrue(v5.precedes(v1));  // x mniejszy, y równy
        assertTrue(v4.precedes(v1));  // wektory równe

        assertFalse(v2.precedes(v1));  // x i y większe w v2 niż w v1
        assertFalse(v1.precedes(v3));  // x równy, y większy
        assertFalse(v1.precedes(v5));  // x większy, y równy
        assertTrue(v1.precedes(v6));  // x równy, y mniejsze w v1 niż w v6
        assertFalse(v1.precedes(v7));  // y większe, ale x mniejsze

        assertFalse(v1.precedes(null));  // null argument, powinno zwrócić false
    }


    @Test
    void testFollows() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 5);
        Vector2d v3 = new Vector2d(2, 2);
        Vector2d v4 = new Vector2d(2, 3);
        Vector2d v5 = new Vector2d(1, 3);
        Vector2d v6 = new Vector2d(2, 4);
        Vector2d v7 = new Vector2d(5, 1);

        // Testy pozytywne (przypadki, gdzie follows powinno zwrócić true)
        assertTrue(v2.follows(v1));  // x i y większe w v2 niż w v1
        assertTrue(v1.follows(v3));  // x równy, y większy
        assertTrue(v1.follows(v5));  // x większy, y równy
        assertTrue(v1.follows(v4));  // wektory równe

        // Testy negatywne (przypadki, gdzie follows powinno zwrócić false)
        assertFalse(v1.follows(v2));  // x i y mniejsze w v1 niż w v2
        assertFalse(v3.follows(v1));  // x równy, y mniejsze
        assertFalse(v5.follows(v1));  // x mniejsze, y równy
        assertTrue(v6.follows(v1));  // x równy, y większe
        assertFalse(v7.follows(v1));  // x większe, ale y mniejsze

        assertFalse(v1.follows(null));  // null argument, powinno zwrócić false
    }


    @Test
    void testUpperRight() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 1);
        Vector2d expected = new Vector2d(4, 3);

        Vector2d v3 = new Vector2d(-5, 5);
        Vector2d v4 = new Vector2d(5, -5);
        Vector2d expected_2 = new Vector2d(5, 5);

        assertEquals(expected, v1.upperRight(v2));
        assertEquals(expected_2, v3.upperRight(v4));
    }

    @Test
    void testLowerLeft() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(4, 1);
        Vector2d expected = new Vector2d(2, 1);

        assertEquals(expected, v1.lowerRight(v2));
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

        assertEquals(expected, v1.opposite(), "Opposite of v1 should equal expected");
    }
}