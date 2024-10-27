package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static agh.ics.oop.OptionsParser.parseMoveDirections;
import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {
    @Test
    void parseValidDirections() {
        String[] input = {"f", "b", "l", "r"};
        MoveDirection[] expected = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT
        };

        assertArrayEquals(expected, parseMoveDirections(input));
    }

    @Test
    void parseWithInvalidDirections() {
        String[] input = {"f", "x", "b", "abc", "l"};
        MoveDirection[] expected = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT
        };

        assertArrayEquals(expected, parseMoveDirections(input));
    }

    @Test
    void parseAllInvalidDirections() {
        String[] input = {"x", "y", "abcb", "esaw"};
        MoveDirection[] expected = {};

        assertArrayEquals(expected, parseMoveDirections(input));
    }

    @Test
    void parseEmptyArray() {
        String[] input = {};
        MoveDirection[] expected = {};

        assertArrayEquals(expected, parseMoveDirections(input));
    }

    @Test
    void parseWithMixedCase() {
        String[] input = {"F", "b", "L", "R"};
        MoveDirection[] expected = {
                MoveDirection.BACKWARD
        };
        // program miał interpretować tylko lowercasy
        assertArrayEquals(expected, parseMoveDirections(input));
    }
}