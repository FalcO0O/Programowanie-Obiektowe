package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;

    private final Vector2d UP = new Vector2d(0, 1);
    private final Vector2d DOWN = new Vector2d(0, -1);
    private final Vector2d RIGHT = new Vector2d(1, 0);
    private final Vector2d LEFT = new Vector2d(-1, 0);

    public Animal()
    {
        position = new Vector2d(2, 2);
        orientation = MapDirection.NORTH;
    }
    public Animal(int x, int y)
    {
        orientation = MapDirection.NORTH;
        position = new Vector2d(x, y);
    }

    @Override
    public String toString() {
        return "Animal:" + "orientation = " + orientation + ", position = " + position + '}';
    }

    public boolean isAt(Vector2d position)
    {
        return (position.equals(this.position));
    }

    private boolean withinMap(Vector2d position)
    {
        if (position.getX() >= 0 && position.getX() <= 4)
        {
            return position.getY() >= 0 && position.getY() <= 4;
        }
        return false;
    }

    public void move(MoveDirection direction)
    {
        switch (direction)
        {
            case LEFT -> {orientation = orientation.previous();}
            case RIGHT -> {orientation = orientation.next();}
            case FORWARD -> {
                Vector2d newPosition = this.position.add(orientation.toUnitVector());
                if (withinMap(newPosition)) this.position = newPosition;
            }
            case BACKWARD -> {
                Vector2d newPosition = this.position.add(orientation.toUnitVector().opposite());
                if (withinMap(newPosition)) this.position = newPosition;
            }
        }
    }

    public Vector2d getPosition()
    {
        return position;
    }

    public MapDirection getOrientation()
    {
        return orientation;
    }

}
