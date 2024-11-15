package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;


public class RectangularMap implements WorldMap {
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final Vector2d lowerLeft, upperRight;
    private final MapVisualizer obj = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width-1, height-1);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
    }

    @Override
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
                if (canMoveTo(pos))
                    animals.remove(animal.getPosition());
                    animal.move(direction, this);
                    animals.put(animal.getPosition(), animal);
            }
            default -> {throw new RuntimeException("Wartość nie powinna istnieć!");}
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight) && !isOccupied(position);
    }

    @Override
    public String toString() {
        return obj.draw(lowerLeft, upperRight);
    }
}
