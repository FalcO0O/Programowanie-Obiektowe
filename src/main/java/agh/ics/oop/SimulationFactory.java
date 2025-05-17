package agh.ics.oop;

import agh.ics.oop.model.AnimalFactory;
import agh.ics.oop.model.map.FiresMap;
import agh.ics.oop.model.map.GlobeMap;
import agh.ics.oop.model.settings.Settings;

public class SimulationFactory {

    public Simulation createSimulation(Settings settings) {
        var worldMap = switch (settings.mapVariant()) {
            case GLOBE -> new GlobeMap(settings.mapWidth(), settings.mapHeight(),
                settings.initialPlantsNumber(), settings.dailyNewPlantsNumber(), settings.energyOnConsumption());
            case FIRES -> new FiresMap(settings.mapWidth(), settings.mapHeight(),
                settings.initialPlantsNumber(), settings.dailyNewPlantsNumber(), settings.energyOnConsumption(),
                settings.fireInterval(), settings.fireDuration());
        };
        var animalFactory = new AnimalFactory(settings.initialAnimalEnergy(), settings.energyRequiredForReproduction(),
            settings.energyLostOnReproduction(), settings.minMutationsNumber(), settings.maxMutationsNumber(),
            settings.mutationVariant(), settings.animalGenomeLength());
        return new Simulation(settings.initialAnimalsNumber(), worldMap, animalFactory, settings.saveStatistics());
    }

}
