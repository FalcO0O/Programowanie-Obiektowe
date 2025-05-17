package agh.ics.oop.model.position;

import agh.ics.oop.model.util.Vector2d;
import javafx.application.Platform;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EquatorPositionsGeneratorTest {

    @BeforeAll
    static void initJfxRuntime() {
        try {
            Platform.startup(() -> {});
        } catch (Exception e) {
            System.out.println("Platforma już chodzi");
        }
    }

    @Test
    public void testGenerator() {
        Set<Vector2d> bannedPositions = new HashSet<>();
        bannedPositions.add(new Vector2d(5, 5));
        bannedPositions.add(new Vector2d(5, 4));
        bannedPositions.add(new Vector2d(0, 0));
        var generator = new EquatorPositionsGenerator(10, 10, bannedPositions);
        var priorityPositions = generator.getPriorityPositions();
        var regularPositions = generator.getRegularPositions();
        assertEquals(18, priorityPositions.size());
        assertEquals(79, regularPositions.size());
        assertFalse(new HashSet<>(priorityPositions).contains(new Vector2d(5, 5)));
    }

}
