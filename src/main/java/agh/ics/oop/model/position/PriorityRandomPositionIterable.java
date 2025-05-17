package agh.ics.oop.model.position;

import agh.ics.oop.model.util.Vector2d;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PriorityRandomPositionIterable implements Iterable<Vector2d> {

    private final static double CHANGE_TO_GET_PRIORITY_POSITION = 0.8;
    private final int positionsToGenerate;
    private final List<Vector2d> priorityPositions;
    private final List<Vector2d> regularPositions;
    private final PriorityRandomPositionIterator iteratorObject = new PriorityRandomPositionIterator();
    private final Random random = new Random();

    public PriorityRandomPositionIterable(List<Vector2d> priorityPositions,
                                          List<Vector2d> regularPositions,
                                          int positionsToGenerate) {
        Collections.shuffle(priorityPositions);
        Collections.shuffle(regularPositions);
        this.priorityPositions = priorityPositions;
        this.regularPositions = regularPositions;
        this.positionsToGenerate = positionsToGenerate;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return iteratorObject;
    }

    private class PriorityRandomPositionIterator implements Iterator<Vector2d> {

        private int generatedPositions = 0;

        @Override
        public boolean hasNext() {
            return generatedPositions < positionsToGenerate && (!priorityPositions.isEmpty() || !regularPositions.isEmpty());
        }

        @Override
        public Vector2d next() {
            double priorityPercentage = (double) priorityPositions.size() / (priorityPositions.size() + regularPositions.size());
            double priorityChance = CHANGE_TO_GET_PRIORITY_POSITION * priorityPercentage;
            double regularChance = (1 - CHANGE_TO_GET_PRIORITY_POSITION) * (1 - priorityChance);
            double normalizedPriorityChance = priorityChance / (priorityChance + regularChance);
            List<Vector2d> positions;
            if (regularPositions.isEmpty() ||
                (random.nextDouble() < normalizedPriorityChance && !priorityPositions.isEmpty())) {
                positions = priorityPositions;
            } else {
                positions = regularPositions;
            }
            generatedPositions++;
            return positions.removeLast();
        }

    }
}