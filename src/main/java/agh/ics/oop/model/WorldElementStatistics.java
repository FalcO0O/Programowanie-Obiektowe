package agh.ics.oop.model;

import java.util.List;

public record WorldElementStatistics(
    List<Integer> genome,
    int currentGene,
    int energy,
    int consumedPlantsNumber,
    int childrenNumber,
    int descendantsNumber,
    int birthDay,
    boolean isDead,
    int deathDay
) {
}
