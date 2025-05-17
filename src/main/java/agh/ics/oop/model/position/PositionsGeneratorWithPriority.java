package agh.ics.oop.model.position;

import agh.ics.oop.model.util.Vector2d;

import java.util.List;

public interface PositionsGeneratorWithPriority {

    List<Vector2d> getPriorityPositions();

    List<Vector2d> getRegularPositions();

}
