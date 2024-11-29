package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap {
    private final Boundary MapBoundary;

    public RectangularMap(int width, int height) {
        MapBoundary = new Boundary(new Vector2d(0,0), new Vector2d(width-1, height-1));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && (position.precedes(MapBoundary.upperRight()) && position.follows(MapBoundary.lowerLeft()));
    }

    @Override
    public Boundary getCurrentBounds() {
        return MapBoundary;
    }
}
