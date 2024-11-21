package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d lowerLeft, upperRight;
    private final MapVisualizer mapDrafter = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width-1, height-1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && position.precedes(upperRight) && super.canMoveTo(position);
    }

    @Override
    public String toString() {
        return mapDrafter.draw(lowerLeft, upperRight);
    }
}
