package agh.ics.oop.model.settings;

import agh.ics.oop.model.map.MapVariant;
import agh.ics.oop.model.settings.exceptions.InvalidSettingsException;

public class SettingsValidator {

    public void validate(Settings settings) throws InvalidSettingsException {
        if (isNonPositive(settings.mapWidth())) {
            throw new InvalidSettingsException("Map width must be positive");
        }
        if (isNonPositive(settings.mapHeight())) {
            throw new InvalidSettingsException("Map height must be positive");
        }
        if (isNegative(settings.initialPlantsNumber())) {
            throw new InvalidSettingsException("Initial plants number must be non-negative");
        }
        if (isNegative(settings.energyOnConsumption())) {
            throw new InvalidSettingsException("Energy on plant consumption must be non-negative");
        }
        if (isNonPositive(settings.dailyNewPlantsNumber())) {
            throw new InvalidSettingsException("Daily new plants number must be positive");
        }
        if (isNonPositive(settings.initialAnimalsNumber())) {
            throw new InvalidSettingsException("Initial animals number must be positive");
        }
        if (isNonPositive(settings.initialAnimalEnergy())) {
            throw new InvalidSettingsException("Initial animal energy must be positive");
        }
        if (isNonPositive(settings.energyRequiredForReproduction())) {
            throw new InvalidSettingsException("Energy required for reproduction must be non-negative");
        }
        if (isNonPositive(settings.energyLostOnReproduction())) {
            throw new InvalidSettingsException("Energy lost on reproduction must be non-negative");
        }
        if (isNegative(settings.minMutationsNumber())) {
            throw new InvalidSettingsException("Minimum mutations number must be positive");
        }
        if (isNegative(settings.maxMutationsNumber())) {
            throw new InvalidSettingsException("Maximum mutations number must be positive");
        }
        if (isNonPositive(settings.animalGenomeLength())) {
            throw new InvalidSettingsException("Animal genome length must be positive");
        }
        if (isNonPositive(settings.fireInterval()) && settings.mapVariant() == MapVariant.FIRES) {
            throw new InvalidSettingsException("Fire interval must be positive");
        }
        if (isNegative(settings.fireDuration()) && settings.mapVariant() == MapVariant.FIRES) {
            throw new InvalidSettingsException("Fire duration must be positive");
        }
        if (settings.mapHeight() * settings.mapHeight() < settings.initialPlantsNumber()) {
            throw new InvalidSettingsException("Initial plants number too big for given map dimensions");
        }
        if (settings.minMutationsNumber() > settings.maxMutationsNumber()) {
            throw new InvalidSettingsException("Minimum mutations number must be lower or equal to maximum mutations number");
        }
        if (settings.maxMutationsNumber() > settings.animalGenomeLength()) {
            throw new InvalidSettingsException("Maximum mutations number must be lower or equal to animal genome length");
        }
        if (settings.energyLostOnReproduction() >= settings.energyRequiredForReproduction()) {
            throw new InvalidSettingsException("Energy required for reproduction must be higher than energy lost on reproduction");
        }
    }

    private boolean isNonPositive(int num) {
        return num <= 0;
    }

    private boolean isNegative(int num) {
        return num < 0;
    }

}
