package agh.ics.oop.model.map;

import agh.ics.oop.model.util.Vector2d;

import java.util.Arrays;
import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    static final private Vector2d _NORTH = new Vector2d(0, 1);
    static final private Vector2d _NORTHEAST = new Vector2d(1, 1);
    static final private Vector2d _EAST = new Vector2d(1, 0);
    static final private Vector2d _SOUTHEAST = new Vector2d(1, -1);
    static final private Vector2d _SOUTH = new Vector2d(0, -1);
    static final private Vector2d _SOUTHWEST = new Vector2d(-1, -1);
    static final private Vector2d _WEST = new Vector2d(-1, 0);
    static final private Vector2d _NORTHWEST = new Vector2d(-1, 1);



    @Override
    public String toString() {
        return switch(this)
        {
            case NORTH -> "Północ";
            case NORTHEAST -> "Północny Wschód";
            case EAST -> "Wschód";
            case SOUTHEAST -> "Południowy Wschód";
            case SOUTH -> "Południe";
            case SOUTHWEST -> "Południowy Zachód";
            case WEST -> "Zachód";
            case NORTHWEST -> "Północny Zachód";
        };
    }

    public MapDirection rotate(int rotationNum) {
        var directions = Arrays.asList(values());
        return directions.get((directions.indexOf(this) + rotationNum) % directions.size());
    }

    public MapDirection opposite() {
        return rotate(4);
    }

    public static MapDirection getRandom() {
        var random = new Random();
        var directions = Arrays.asList(values());
        return directions.get(random.nextInt(directions.size()));
    }

    public Vector2d toUnitVector()
    {
        return switch (this)
        {
            case NORTH -> _NORTH;
            case NORTHEAST -> _NORTHEAST;
            case EAST -> _EAST;
            case SOUTHEAST -> _SOUTHEAST;
            case SOUTH -> _SOUTH;
            case SOUTHWEST -> _SOUTHWEST;
            case WEST -> _WEST;
            case NORTHWEST -> _NORTHWEST;
        };
    }
}
