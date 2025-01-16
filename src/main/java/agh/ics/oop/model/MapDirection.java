package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    static final private Vector2d _NORTH = new Vector2d(0, -1);
    static final private Vector2d _EAST = new Vector2d(1, 0);
    static final private Vector2d _SOUTH = new Vector2d(0, 1);
    static final private Vector2d _WEST = new Vector2d(-1, 0);

    @Override
    public String toString() {
        return switch(this)
        {
            case NORTH -> "Północ";
            case EAST -> "Wschód";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
        };
    }

    public MapDirection next()
    {
        return switch(this)
        {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public MapDirection previous()
    {
        return switch(this)
        {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    public Vector2d toUnitVector()
    {
        return switch (this)
        {
            case NORTH -> _NORTH;
            case EAST -> _EAST;
            case SOUTH -> _SOUTH;
            case WEST -> _WEST;
        };
    }

    public MapDirection Next()
    {
        return MapDirection.values()[(this.ordinal() + 1) % 4];
    }
}
