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
        assertThrows(IllegalArgumentException.class, () -> parseMoveDirections(input), "Invalid directions should throw an exception");
    }

    @Test
    void parseAllInvalidDirections() {
        String[] input = {"x", "y", "abcb", "esaw"};
        assertThrows(IllegalArgumentException.class, () -> parseMoveDirections(input), "All invalid directions should throw an exception");
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
        assertThrows(IllegalArgumentException.class, () -> parseMoveDirections(input), "Mixed case invalid directions should throw an exception");
    }
}