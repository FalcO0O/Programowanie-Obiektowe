package agh.ics.oop.model.element;

import agh.ics.oop.model.map.MapDirection;
import agh.ics.oop.model.util.Vector2d;
import javafx.application.Platform;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnimalComparator {

    @BeforeAll
    static void initJfxRuntime() {
        try {
            Platform.startup(() -> {});
        } catch (Exception e) {
            System.out.println("Platforma już chodzi");
        }
    }

    @Test
    public void testSorting() {
        var position = new Vector2d(0, 0);
        var orientation = MapDirection.NORTH;
        var genes = List.of(0);
        var animal1 = new Animal(position, 50, 100, genes, 0, orientation, 5, new ArrayList<>());
        var animal2 = new Animal(position, 30, 100, genes, 0, orientation, 10, new ArrayList<>());
        var animal3 = new Animal(position, 50, 100, genes, 0, orientation, 10, new ArrayList<>());
        var animal4 = new Animal(position, 50, 100, genes, 0, orientation, 10, new ArrayList<>());
        // animal1 and animal4 have child
        new Animal(position, 30, 100, genes, 0, orientation, 10, List.of(animal2, animal4));
        var animals = new ArrayList<>(List.of(animal1, animal2, animal3, animal4));
        animals.sort(new AnimalComparator());
        var sortedAnimals = animals.reversed();
        assertEquals(sortedAnimals.get(0), animal1);
        assertEquals(sortedAnimals.get(1), animal4);
        assertEquals(sortedAnimals.get(2), animal3);
        assertEquals(sortedAnimals.get(3), animal2);
    }



}
