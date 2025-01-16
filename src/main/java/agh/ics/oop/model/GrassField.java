package agh.ics.oop.model;

import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassFieldsNum) {
        super(UUID.randomUUID()); // na tyle mała szansa na powtórzenie się ID że nie sprawdzam czy został już użyty
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
    public Optional<WorldElement> objectAt(Vector2d position) {
        return super.objectAt(position)
                .or(() -> Optional.ofNullable(grasses.get(position)));
    }


    @Override
    public Boundary getCurrentBounds() {
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
        return new Boundary(lowerLeft, upperRight);
    }

    @Override
    public ArrayList<WorldElement> getElements() {
        return Stream.concat(
                        super.getElements().stream(),
                        grasses.values().stream()
                )
                .collect(Collectors.toCollection(ArrayList::new));
    }

    Map<Vector2d, Grass> getGrasses() { // package private do testów
        return grasses;
    }

}

