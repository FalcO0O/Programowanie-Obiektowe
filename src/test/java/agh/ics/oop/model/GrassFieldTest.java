package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    private GrassField map;

    @BeforeEach
    void setUp() {
        map = new GrassField(10);
    }

    @Test
    void placeAnimalOnEmptyPosition() {
        Animal animal = new Animal(new Vector2d(1, 1));
        assertTrue(map.place(animal), "Animal should be at (1,1)");
        assertEquals(animal, map.objectAt(new Vector2d(1, 1)), "Animal should be accessible at (1,1)");
    }

    @Test
    void canPlaceAnimalOnGrass() {
        Map<Vector2d, Grass> grasses = map.getGrasses();
        Vector2d grassPosition = grasses.keySet().iterator().next();
        Animal animal = new Animal(grassPosition);

        assertTrue(map.place(animal), "Animal should be placed on a position occupied by grass");
        assertEquals(animal, map.objectAt(grassPosition), "Animal should be placed on top of grass");
    }

    @Test
    void cannotPlaceAnimalOnAnotherAnimal() {
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 2));

        assertTrue(map.place(animal1), "Animal1 should be placed at (2,2)");
        assertFalse(map.place(animal2), "Animal2 should not be placed at the same position as Animal1");
        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)), "Only Animal1 should remain at (2,2)");
    }

    @Test
    void animalCanMoveOverGrass() {
        Map<Vector2d, Grass> grasses = map.getGrasses();
        Vector2d grassPosition = grasses.keySet().iterator().next();
        Animal animal = new Animal(grassPosition.add(new Vector2d(0, 1)));
        map.place(animal);
        map.move(animal, MoveDirection.BACKWARD);
        assertEquals(grassPosition, animal.getPosition(), "Animal should move onto the position of the grass");
        assertEquals(animal, map.objectAt(grassPosition), "Animal should occupy the position of the grass");
    }

    @Test
    void animalCannotMoveOntoAnotherAnimal() {
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 3));

        map.place(animal1);
        map.place(animal2);

        map.move(animal1, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 2), animal1.getPosition(), "Animal1 should not move onto Animal2's position");
        assertEquals(new Vector2d(2, 3), animal2.getPosition(), "Animal2 should remain at (2,3)");
    }

    @Test
    void grassAndAnimalOccupySameSpace() {
        Map<Vector2d, Grass> grasses = map.getGrasses(); // Pobranie mapy trawy
        Vector2d grassPosition = new Vector2d(4, 4);

        grasses.put(grassPosition, new Grass(grassPosition));
        Animal animal = new Animal(grassPosition);
        map.place(animal);
        assertTrue(map.isOccupied(grassPosition), "Position (4,4) should be occupied");
        assertEquals(animal, map.objectAt(grassPosition), "Animal should take priority over grass");
    }

    @Test
    void randomGrassPlacementConsistency() {
        ArrayList<WorldElement> elements = map.getElements();
        int grassCount = 0;
        for(WorldElement element : elements) {
            if(element instanceof Grass) {
                grassCount++;
            }
        }
        assertEquals(10, grassCount, "GrassField should contain exactly 10 grass elements");
    }
}