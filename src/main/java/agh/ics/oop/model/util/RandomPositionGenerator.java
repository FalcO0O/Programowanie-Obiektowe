package agh.ics.oop.model.util;


import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final ArrayList<Vector2d> vectors = new ArrayList<>();

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
        if (grassCount > maxWidth * maxHeight) {
            throw new IllegalArgumentException("No enough space for every grass");
        }

        ArrayList<Integer> positions = new ArrayList<>();
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                positions.add(toNumber(x, y, maxHeight));
            }
        }

        Collections.shuffle(positions, new Random());

        for (int i = 0; i < grassCount; i++) {
            int number = positions.get(i);
            vectors.add(toVector(number, maxHeight));       // randomizing is O(n)
        }
    }

    private int toNumber(int x, int y, int numOfRows) {
        return x * numOfRows + y;
    }

    private Vector2d toVector(int number, int numOfRows) {
        int x = number / numOfRows;
        int y = number % numOfRows;
        return new Vector2d(x, y);
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return vectors.iterator();
    }
}
