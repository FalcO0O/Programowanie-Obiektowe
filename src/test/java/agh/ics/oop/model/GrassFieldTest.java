package agh.ics.oop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    private GrassField map;

    @BeforeEach
    void setUp() {
        map = new GrassField(10);
    }

    @Test
    void placeAnimalOnEmptyPosition() {
        Vector2d vector2d = new Vector2d(1,1);
        Animal animal = new Animal(vector2d);
        assertDoesNotThrow(() -> map.place(animal), "Animal should be placed at (1,1)");
        assert map.objectAt(vector2d).isPresent();
        assertEquals(animal, map.objectAt(vector2d).get(), "Animal should be accessible at (1,1)");
    }

    @Test
    void canPlaceAnimalOnGrass() {
        Map<Vector2d, Grass> grasses = map.getGrasses();
        Vector2d grassPosition = grasses.keySet().iterator().next();
        Animal animal = new Animal(grassPosition);

        assertDoesNotThrow(() -> map.place(animal), "Animal should be placed on a position occupied by grass");
        assert map.objectAt(grassPosition).isPresent();
        assertEquals(animal, map.objectAt(grassPosition).get(), "Animal should be placed on top of grass");
    }

    @Test
    void cannotPlaceAnimalOnAnotherAnimal() {
        Vector2d vector = new Vector2d(2, 2);
        Animal animal1 = new Animal(vector);
        Animal animal2 = new Animal(vector);

        assertDoesNotThrow(() -> map.place(animal1), "Animal1 should be placed at (2,2)");
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2), "Animal2 should not be placed at the same position as Animal1");
        assert map.objectAt(vector).isPresent();
        assertEquals(animal1, map.objectAt(vector).get(), "Only Animal1 should remain at (2,2)");
    }

    @Test
    void animalCanMoveOverGrass() {
        Map<Vector2d, Grass> grasses = map.getGrasses();
        Vector2d grassPosition = grasses.keySet().iterator().next();
        Animal animal = new Animal(grassPosition.add(new Vector2d(0, 1)));
        assertDoesNotThrow(() -> map.place(animal), "Animal should not be placed on the map at (0,1)");
        map.move(animal, MoveDirection.BACKWARD);
        assertEquals(grassPosition, animal.getPosition(), "Animal should move onto the position of the grass");
        assert map.objectAt(grassPosition).isPresent();
        assertEquals(animal, map.objectAt(grassPosition).get(), "Animal should occupy the position of the grass");
    }

    @Test
    void animalCannotMoveOntoAnotherAnimal() {
        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 3));

        assertDoesNotThrow(() -> map.place(animal1), "Animal should be placed on the map at (2,2)");
        assertDoesNotThrow(() -> map.place(animal2), "Animal should be placed on the map at (2,3)");

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
        assertDoesNotThrow(() -> map.place(animal), "Animal should be placed on the map at (4,4)");
        assertTrue(map.isOccupied(grassPosition), "Position (4,4) should be occupied");
        assert map.objectAt(grassPosition).isPresent();
        assertEquals(animal, map.objectAt(grassPosition).get(), "Animal should take priority over grass");
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

    @Test
    void isOrderedCollectionSorted()
    {
        Animal animal1 = new Animal(new Vector2d(2, 6));
        Animal animal2 = new Animal(new Vector2d(3, 9));
        Animal animal3 = new Animal(new Vector2d(9, 5));
        Animal animal4 = new Animal(new Vector2d(2, 4));
        ArrayList<Animal> animalsToPlace = new ArrayList<>(List.of(animal1, animal2, animal3, animal4));
        animalsToPlace.forEach(
                animal -> assertDoesNotThrow(
                        () -> map.place(animal), "Error while placing animals"
                ));

        assertEquals(map.getOrderedAnimals(), List.of(animal4, animal1, animal2, animal3));
    }
}