package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer MapDrafter = new MapVisualizer(this);
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    public void move(Animal animal, MoveDirection direction) {
        switch (direction) {
            case LEFT, RIGHT -> animal.move(direction, this);
            case FORWARD, BACKWARD -> {
                Vector2d pos = animal.getPosition();
                animal.move(direction, this);
                if(!animal.getPosition().equals(pos)) {
                    animals.remove(pos);
                    animals.put(animal.getPosition(), animal);
                }
            }
            default -> {throw new RuntimeException("Wartość nie powinna istnieć!");}
        }
    }

    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal); // jedynie ten warunek jest wspólny dla obu klas dziedziczących
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    public ArrayList<WorldElement> getElements()
    {
        return new ArrayList<>(animals.values()); // należy stworzyć nową listę, aby oryginalna nie była nadpisana
    }
}
