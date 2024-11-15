package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void runWithMultipleMoves() {
        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> vectorsList = List.of(new Vector2d(2, 2), new Vector2d(3, 3));
        List<MoveDirection> moveDirectionsList = List.of(
                MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.BACKWARD);
        Simulation simulation = new Simulation(vectorsList, moveDirectionsList, map);
        simulation.run();

        assertNotNull(map.objectAt(new Vector2d(2, 3)), "Second animal should have moved to (2,3)");
        assertNotNull(map.objectAt(new Vector2d(2, 4)), "First animal should have moved to (4,3)");
    }

    @Test
    void blockedMovement() {
        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> vectorsList = List.of(new Vector2d(0, 0));
        List<MoveDirection> moveDirectionsList = List.of(MoveDirection.FORWARD, MoveDirection.FORWARD);
        Simulation simulation = new Simulation(vectorsList, moveDirectionsList, map);
        simulation.run();

        assertNotNull(map.objectAt(new Vector2d(0, 2)), "Animal should be blocked at (0,2)");
    }

    @Test
    void emptyDirections() {
        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> vectorsList = List.of(new Vector2d(2, 2), new Vector2d(3, 3));
        List<MoveDirection> moveDirectionsList = List.of();
        Simulation simulation = new Simulation(vectorsList, moveDirectionsList, map);
        simulation.run();

        assertTrue(map.isOccupied(new Vector2d(2, 2)), "Animal should remain at (2,2)");
        assertTrue(map.isOccupied(new Vector2d(3, 3)), "Second animal should remain at (3,3)");
    }

    @Test
    void edgePosition() {
        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> vectorsList = List.of(new Vector2d(4, 4), new Vector2d(0, 0));
        List<MoveDirection> moveDirectionsList = List.of(
                MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.FORWARD
        );
        Simulation simulation = new Simulation(vectorsList, moveDirectionsList, map);
        simulation.run();

        assertTrue(map.isOccupied(new Vector2d(4, 4)), "Animal should move to (0,1)");
        assertTrue(map.isOccupied(new Vector2d(1, 0)), "Animal at edge should remain within bounds");
    }

    @Test
    void blockedAnimal() {
        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> vectorsList = List.of(new Vector2d(1, 1), new Vector2d(1, 2));
        List<MoveDirection> moveDirectionsList = List.of(
                MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.LEFT
        );
        Simulation simulation = new Simulation(vectorsList, moveDirectionsList, map);
        simulation.run();

        assertTrue(map.isOccupied(new Vector2d(1, 1)), "Blocked animal should remain in place");
    }

    @Test
    void allDirections() {
        RectangularMap map = new RectangularMap(5, 5);
        List<Vector2d> vectorsList = List.of(new Vector2d(2, 2));
        List<MoveDirection> moveDirectionsList = List.of(
                MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.BACKWARD, MoveDirection.LEFT
        );
        Simulation simulation = new Simulation(vectorsList, moveDirectionsList, map);
        simulation.run();

        assertTrue(map.isOccupied(new Vector2d(1, 3)), "Animal should be at (1,3)");
    }

    @Test
    public void numberOfAnimalsOnMapMatchesList() {
        RectangularMap map = new RectangularMap(5,5);
        List<MoveDirection> directions = null; // nie sprawdzamy tego
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4), new Vector2d(1, 1), new Vector2d(2,2));
        Simulation simulation = new Simulation(positions, directions, map);
        assertEquals(simulation.getNumOfAnimalsOnMap(), simulation.sizeOfAnimalList());
    }
}