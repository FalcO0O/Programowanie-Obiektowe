package agh.ics.oop.model.map;

import agh.ics.oop.model.element.Fire;
import agh.ics.oop.model.util.Vector2d;
import javafx.application.Platform;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FiresMapTest {

    @BeforeAll
    static void initJfxRuntime() {
        try {
            Platform.startup(() -> {});
        } catch (Exception e) {
            System.out.println("Platforma już chodzi");
        }
    }

    private FiresMap getOneTileMap() {
        return new FiresMap(1, 1, 1, 0, 0, 1, 10);
    }

    @Test
    public void testFires() {
        var map = getOneTileMap();
        map.setDay(1);

        // Uruchomienie zdarzeń specjalnych w wątku JavaFX
        Platform.runLater(() -> {
            map.runSpecialEvents();
            assertInstanceOf(Fire.class, map.primaryObjectAt(new Vector2d(0, 0)));
        });
    }
}
