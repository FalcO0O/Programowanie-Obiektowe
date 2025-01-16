package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();

    private final MapVisualizer MapDrafter = new MapVisualizer(this);
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final UUID uuid;

    public AbstractWorldMap(UUID uuid) {
        this.uuid = uuid;
    }

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
        else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    public void move(Animal animal, MoveDirection direction) {
        switch (direction) {
            case LEFT, RIGHT -> {
                animal.move(direction, this);
                mapChanged("Zwierze obrócilo sie na " + animal.getOrientation().toString());
            }
            case FORWARD, BACKWARD -> {
                Vector2d pos = animal.getPosition();
                animal.move(direction, this);
                if(!animal.getPosition().equals(pos)) {
                    animals.remove(pos);
                    animals.put(animal.getPosition(), animal);
                }
                mapChanged("Zwierze ruszylo sie z pozycji %s na pozycje %s".formatted(pos.toString(), animal.getPosition().toString()));
            }
            default -> throw new RuntimeException("Wartość nie powinna istnieć!");
        }
    }

    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    public boolean canMoveTo(Vector2d position) {
        if(objectAt(position).isPresent()) return !(objectAt(position).get() instanceof Animal);
        return true;
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        return Optional.ofNullable(animals.get(position));
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

    public Collection<Animal> getOrderedAnimals()
    {
        return animals.values().stream()
                .sorted(
                        Comparator.comparing( (Animal animal) -> animal.getPosition().getX())
                                .thenComparing((Animal animal) -> animal.getPosition().getY())
                )
                .collect(Collectors.toList());
    }

    public UUID getID()
    {
        return uuid;
    }

}
