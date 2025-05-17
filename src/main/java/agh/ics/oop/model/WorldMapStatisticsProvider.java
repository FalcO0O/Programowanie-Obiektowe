package agh.ics.oop.model;

import agh.ics.oop.model.genotype.GenotypeRankingEntry;

import java.util.List;
import java.util.Optional;

public interface WorldMapStatisticsProvider {

    int getDay();

    int getAnimalsNumber();

    int getFreePositionsNumber();

    int getPlantsNumber();

    List<GenotypeRankingEntry> getGenotypeRanking();

    Optional<Double> getAverageDeadLifespan();

    Optional<Double> getAverageChildrenNumber();

    Optional<Double> getAverageEnergyLevel();

}
