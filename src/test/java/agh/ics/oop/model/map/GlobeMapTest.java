package agh.ics.oop.model.map;

import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.SortedAnimalGroup;
import agh.ics.oop.model.map.exceptions.IncorrectPositionException;
import agh.ics.oop.model.util.Vector2d;
import javafx.application.Platform;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GlobeMapTest {

    @BeforeAll
    static void initJfxRuntime() {
        try {
            Platform.startup(() -> {});
        } catch (Exception e) {
            System.out.println("Platforma już chodzi");
        }
    }

    private GlobeMap getMap() {
        return new GlobeMap(10, 10, 10, 10, 10);
    }

    private Animal getAnimal(Vector2d position) {
        int energy = 100;
        int initialEnergy = 100;
        List<Integer> genes = List.of(2, 1);
        int currentGene = 0;
        MapDirection orientation = MapDirection.NORTH;
        int birthDay = 0;
        List<Animal> parents = new ArrayList<>();
        return new Animal(position, energy, initialEnergy, genes, currentGene, orientation, birthDay, parents);
    }

    @Test
    void testPlace() {
        var map = getMap();
        var position = new Vector2d(5, 5);
        var animal1 = getAnimal(position);
        var animal2 = getAnimal(position);
        assertDoesNotThrow(() -> map.place(animal1));
        assertEquals(animal1, map.primaryObjectAt(position));
        assertDoesNotThrow(() -> map.place(animal2));
        assertInstanceOf(SortedAnimalGroup.class, map.primaryObjectAt(position));
        var animal3 = getAnimal(new Vector2d(100, 1000));
        assertThrows(IncorrectPositionException.class, () -> map.place(animal3));
    }

    @Test
    void testRemove() {
        var map = getMap();
        var position = new Vector2d(5, 5);
        var animal = getAnimal(position);
        assertDoesNotThrow(() -> map.place(animal));
        map.remove(animal);
        assertNotEquals(animal, map.primaryObjectAt(position));
    }

    @Test
    void testMove() {
        var map = getMap();
        var position = new Vector2d(5, 5);
        var animal = getAnimal(position);
        assertDoesNotThrow(() -> map.place(animal));
        map.move(animal);
        assertEquals(animal, map.primaryObjectAt(new Vector2d(6, 5)));
    }

}
