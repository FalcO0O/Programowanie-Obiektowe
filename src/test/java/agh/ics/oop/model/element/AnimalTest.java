package agh.ics.oop.model.element;

import agh.ics.oop.model.map.GlobeMap;
import agh.ics.oop.model.map.MapDirection;
import agh.ics.oop.model.map.MoveValidator;
import agh.ics.oop.model.util.Vector2d;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalTest {

    private MoveValidator validator = new GlobeMap(10, 10, 0, 0, 0);

    @BeforeAll
    static void initJfxRuntime() {
        try {
            Platform.startup(() -> {});
        } catch (Exception e) {
            System.out.println("Platforma już chodzi");
        }
    }

    private Animal getAnimal() {
        Vector2d position = new Vector2d(5, 5);
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
    public void testMove() {
        var animal = getAnimal();
        assertEquals(new Vector2d(5, 5), animal.getPosition());
        animal.move(validator);
        assertEquals(new Vector2d(6, 5), animal.getPosition());
        animal.move(validator);
        assertEquals(new Vector2d(7, 4), animal.getPosition());
        assertEquals(98, animal.getEnergy()); // mapa nie ma roślin
    }

    @Test
    public void testConsumePlant() {
        var animal = getAnimal();
        Plant plant = new Plant(new Vector2d(5, 5), 50);
        animal.consumePlant(plant);

        assertEquals(150, animal.getEnergy());
        assertEquals(1, animal.getStatistics().consumedPlantsNumber());
    }

    @Test
    public void testDie() {
        var animal = getAnimal();
        animal.die(10);
        assertTrue(animal.isDead());
        assertEquals(10, animal.getStatistics().deathDay());
    }

}
