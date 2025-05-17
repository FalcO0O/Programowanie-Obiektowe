package agh.ics.oop.model;

import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.Plant;
import agh.ics.oop.model.genotype.GenotypeRankingEntry;
import agh.ics.oop.model.util.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public class WorldMapStatistics implements WorldMapStatisticsProvider {

    private int animalsNumber = 0;
    private int plantsNumber = 0;
    private int freePositionsNumber;
    private final Map<String, Integer> genotypeFrequencies = new HashMap<>();
    private int totalEnergyLevel = 0;
    private int totalDeadLifespan = 0;
    private int deadAnimalsNumber = 0;
    private int totalChildrenNumber = 0;
    private int day;
    private final int mapPositionsNumber;

    public WorldMapStatistics(int mapWidth, int mapHeight) {
        mapPositionsNumber = mapWidth * mapHeight;
        freePositionsNumber = mapPositionsNumber;
    }

    public void registerAnimalBirth(Animal animal) {
        String mappedGenes = mapGenesToString(animal.getGenome());
        genotypeFrequencies.merge(mappedGenes, 1, Integer::sum);
        if (animal.getBirthDay() != 0) {
            totalChildrenNumber += 2; // 2 bo jest 2 rodziców
        }
    }

    public void registerAnimalDeath(Animal animal, int day) {
        String mappedGenes = mapGenesToString(animal.getGenome());
        int sameGenes = genotypeFrequencies.get(mappedGenes);
        if (sameGenes == 1) {
            genotypeFrequencies.remove(mappedGenes);
        } else {
            genotypeFrequencies.put(mappedGenes, sameGenes - 1);
        }
        totalDeadLifespan += day - animal.getBirthDay();
        totalChildrenNumber -= animal.getNumberOfChildren();
        deadAnimalsNumber++;
    }

    public void updateMapStatistics(int day, Map<Vector2d, List<Animal>> animals, Map<Vector2d, Plant> plants,
                                    Set<Vector2d> animalOccupiedPositions) {
        this.day = day;
        animalsNumber = animals.values().stream().mapToInt(Collection::size).sum();
        plantsNumber = plants.size();
        int occupiedPositions = animalOccupiedPositions.size();
        for (var position : plants.keySet()) {
            if (!animals.containsKey(position)) {
                occupiedPositions++;
            }
        }
        freePositionsNumber = mapPositionsNumber - occupiedPositions;
        totalEnergyLevel = animals.values()
            .stream()
            .flatMap(Collection::stream)
            .mapToInt(Animal::getEnergy)
            .sum();
    }

    @Override
    public int getAnimalsNumber() {
        return animalsNumber;
    }

    @Override
    public int getFreePositionsNumber() {
        return freePositionsNumber;
    }

    @Override
    public int getPlantsNumber() {
        return plantsNumber;
    }

    @Override
    public List<GenotypeRankingEntry> getGenotypeRanking() {
        return genotypeFrequencies.entrySet()
            .stream()
            .map(entry -> new GenotypeRankingEntry(entry.getKey(), entry.getValue()))
            .sorted()
            .toList()
            .reversed();
    }

    @Override
    public Optional<Double> getAverageDeadLifespan() {
        if (deadAnimalsNumber == 0) {
            return Optional.empty();
        }
        return Optional.of((double) totalDeadLifespan / deadAnimalsNumber);
    }

    @Override
    public Optional<Double> getAverageChildrenNumber() {
        if (animalsNumber == 0) {
            return Optional.empty();
        }
        return Optional.of((double) totalChildrenNumber / animalsNumber);
    }

    @Override
    public Optional<Double> getAverageEnergyLevel() {
        if (animalsNumber == 0) {
            return Optional.empty();
        }
        return Optional.of((double) totalEnergyLevel / animalsNumber);
    }

    private String mapGenesToString(List<Integer> genes) {
        return genes.stream().map(Object::toString).collect(Collectors.joining());
    }

    @Override
    public int getDay() {
        return day;
    }
}
