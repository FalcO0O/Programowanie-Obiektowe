package agh.ics.oop.model.position;

import agh.ics.oop.model.util.Vector2d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquatorPositionsGenerator implements PositionsGeneratorWithPriority {

    private final static double priorityPositionsPercentage = 0.2;
    private final List<Vector2d> priorityPositions;
    private final List<Vector2d> regularPositions;

    public EquatorPositionsGenerator(int mapWidth, int mapHeight) {
        this(mapWidth, mapHeight, new HashSet<>());
    }

    public EquatorPositionsGenerator(int mapWidth, int mapHeight, Set<Vector2d> occupiedPositions) {
        int priorityRows;
        int ceiled = (int) Math.ceil(mapHeight * priorityPositionsPercentage);
        if (ceiled % 2 == mapHeight % 2) {
            priorityRows = ceiled;
        } else {
            priorityRows = ceiled + 1;
        }
        int middle = mapHeight / 2;
        int parity = mapHeight % 2;
        priorityPositions = new ArrayList<>();
        regularPositions = new ArrayList<>();
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                var position = new Vector2d(i, j);
                if (occupiedPositions.contains(position)) {
                    continue;
                }
                if (middle - priorityRows / 2 <= j && j < middle + parity + priorityRows / 2) {
                    priorityPositions.add(position);
                } else {
                    regularPositions.add(position);
                }
            }
        }
    }

    public List<Vector2d> getPriorityPositions() {
        return priorityPositions;
    }

    public List<Vector2d> getRegularPositions() {
        return regularPositions;
    }

}
