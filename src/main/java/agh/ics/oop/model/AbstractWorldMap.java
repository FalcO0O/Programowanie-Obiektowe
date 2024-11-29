package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();

    private final MapVisualizer MapDrafter = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    public void place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            mapChanged("place");

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
        mapChanged("move");
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

    public abstract Boundary getCurrentBounds();

    public final String toString() { // final bc no other class should override it
        Boundary bounds = getCurrentBounds();
        return MapDrafter.draw(bounds.lowerLeft(), bounds.upperRight());
    }

    public ArrayList<WorldElement> getElements()
    {
        return new ArrayList<>(animals.values()); // należy stworzyć nową listę, aby oryginalna nie była nadpisana
    }
}
