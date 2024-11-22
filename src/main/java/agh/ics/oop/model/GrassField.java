package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassFieldsNum) {
        int maxWidthAndHeight = (int) Math.ceil(Math.sqrt(10*grassFieldsNum));

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidthAndHeight, maxWidthAndHeight, grassFieldsNum);
        for(Vector2d grassPosition : randomPositionGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grasses.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return (super.objectAt(position) == null) ? grasses.get(position) : super.objectAt(position);
    }

    @Override
    public String toString() {
        Vector2d upperRight = new Vector2d(-Integer.MAX_VALUE, -Integer.MAX_VALUE);
        Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (Vector2d vector : super.animals.keySet()) {
            lowerLeft = lowerLeft.lowerLeft(vector);
            upperRight = upperRight.upperRight(vector);
        }

        for (Vector2d vector : grasses.keySet()) {
            lowerLeft = lowerLeft.lowerLeft(vector);
            upperRight = upperRight.upperRight(vector);
        }

        return MapDrafter.draw(lowerLeft, upperRight);
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

