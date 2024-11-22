package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d lowerLeft, upperRight;

    public RectangularMap(int width, int height) {
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width-1, height-1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && (position.precedes(upperRight) && position.follows(lowerLeft));
    }

    @Override
    public String toString() {
        return MapDrafter.draw(lowerLeft, upperRight);
    }
}
