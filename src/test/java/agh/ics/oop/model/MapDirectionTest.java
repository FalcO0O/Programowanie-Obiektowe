package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {
    @Test
    void nextAllDirections() {
        //given
        MapDirection N = MapDirection.NORTH;
        MapDirection E = MapDirection.EAST;
        MapDirection S = MapDirection.SOUTH;
        MapDirection W = MapDirection.WEST;

        // when
        N = N.next();
        E = E.next();
        S = S.next();
        W = W.next();

        // then
        assertEquals(MapDirection.EAST,     N);
        assertEquals(MapDirection.SOUTH,    E);
        assertEquals(MapDirection.WEST,     S);
        assertEquals(MapDirection.NORTH,    W);
    }
    @Test
    void previousAllDirections() {
        MapDirection N = MapDirection.NORTH;
        MapDirection E = MapDirection.EAST;
        MapDirection S = MapDirection.SOUTH;
        MapDirection W = MapDirection.WEST;

        N = N.previous();
        E = E.previous();
        S = S.previous();
        W = W.previous();

        assertEquals(MapDirection.EAST,     S);
        assertEquals(MapDirection.SOUTH,    W);
        assertEquals(MapDirection.WEST,     N);
        assertEquals(MapDirection.NORTH,    E);
    }
}