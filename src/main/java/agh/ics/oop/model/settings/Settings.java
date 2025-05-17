package agh.ics.oop.model.settings;

import agh.ics.oop.model.genotype.MutationVariant;
import agh.ics.oop.model.map.MapVariant;

public record Settings(
    int mapWidth,
    int mapHeight,
    MapVariant mapVariant,
    int fireInterval,
    int fireDuration,
    int initialPlantsNumber,
    int energyOnConsumption,
    int dailyNewPlantsNumber,
    int initialAnimalsNumber,
    int initialAnimalEnergy,
    int energyRequiredForReproduction,
    int energyLostOnReproduction,
    int minMutationsNumber,
    int maxMutationsNumber,
    MutationVariant mutationVariant,
    int animalGenomeLength,
    boolean saveStatistics
) {}
