package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    private final Vector2d lowerLeft = new Vector2d(0, 0);

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
            case FORWARD -> {
                Vector2d pos = animal.getPosition();
                pos = pos.add(animal.getOrientation().toUnitVector());
                if (canMoveTo(pos)) {
                    animals.remove(animal.getPosition());
                    animal.move(direction, this);
                    animals.put(animal.getPosition(), animal);
                }
            }
            case BACKWARD -> {
                Vector2d pos = animal.getPosition().add(animal.getOrientation().toUnitVector().opposite());
                if (canMoveTo(pos)) {
                    animals.remove(animal.getPosition());
                    animal.move(direction, this);
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
        return position.follows(lowerLeft); // jedynie ten warunek jest wspólny dla obu klas dziedziczących
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
