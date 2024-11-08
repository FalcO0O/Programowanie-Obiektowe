package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static agh.ics.oop.OptionsParser.parseMoveDirections;
import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {
    @Test
    void parseValidDirections() {
        String[] input = {"f", "b", "l", "r"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT);
        assertEquals(expected, parseMoveDirections(input));
    }

    @Test
    void parseWithInvalidDirections() {
        String[] input = {"f", "x", "b", "abc", "l"};
        List<MoveDirection> expected = List.of(MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.LEFT);
        assertEquals(expected, parseMoveDirections(input));
    }

    @Test
    void parseAllInvalidDirections() {
        String[] input = {"x", "y", "abcb", "esaw"};
        List<MoveDirection> expected = new ArrayList<>();

        assertEquals(expected, parseMoveDirections(input));
    }

    @Test
    void parseEmptyArray() {
        String[] input = {};
        List<MoveDirection> expected = new ArrayList<>();

        assertEquals(expected, parseMoveDirections(input));
    }

    @Test
    void parseWithMixedCase() {
        String[] input = {"F", "b", "L", "R"};
        List<MoveDirection> expected = List.of(MoveDirection.BACKWARD);

        assertEquals(expected, parseMoveDirections(input));
    }
}