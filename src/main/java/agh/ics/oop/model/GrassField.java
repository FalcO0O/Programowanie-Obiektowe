package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final MapVisualizer mapDrafter = new MapVisualizer(this);

    public GrassField(int grassFieldsNum) {
        int maxWidthAndHeight = (int) Math.ceil(Math.sqrt(10*grassFieldsNum));

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidthAndHeight, maxWidthAndHeight, grassFieldsNum);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return grasses.containsKey(position) || super.isOccupied(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) &&
                (!isOccupied(position) || (isOccupied(position) && objectAt(position) instanceof Grass));
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return (super.objectAt(position) == null) ? grasses.get(position) : super.objectAt(position);
    }

    @Override
    public String toString() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Vector2d vector : super.animals.keySet()) {
            minX = Math.min(minX, vector.getX());
            minY = Math.min(minY, vector.getY());
            maxX = Math.max(maxX, vector.getX());
            maxY = Math.max(maxY, vector.getY());
        }

        for (Vector2d vector : grasses.keySet()) {
            minX = Math.min(minX, vector.getX());
            minY = Math.min(minY, vector.getY());
            maxX = Math.max(maxX, vector.getX());
            maxY = Math.max(maxY, vector.getY());
        }

        Vector2d lowerLeft = new Vector2d(minX, minY);
        Vector2d upperRight = new Vector2d(maxX, maxY);

        return mapDrafter.draw(lowerLeft, upperRight);
    }

    @Override
    public ArrayList<WorldElement> getElements() {
        ArrayList<WorldElement> elements = new ArrayList<>(super.getElements());
        elements.addAll(grasses.values());
        return elements;
    }

    Map<Vector2d, Grass> getGrasses() { // package private do testów
        return grasses;
    }
}

