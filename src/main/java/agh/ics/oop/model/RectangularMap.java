package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d upperRight, lowerLeft;
    private final Boundary MapBoundary;

    public RectangularMap(int width, int height) {
        MapBoundary = new Boundary(new Vector2d(0,0), new Vector2d(width-1, height-1));
        upperRight = new Vector2d(width-1, height-1);
        lowerLeft = new Vector2d(0,0);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && (position.precedes(upperRight) && position.follows(lowerLeft));
    }

    @Override
    public Boundary getCurrentBounds() {
        return MapBoundary;
    }
}
