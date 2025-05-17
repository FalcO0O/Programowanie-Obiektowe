package agh.ics.oop.presenter;

import agh.ics.oop.model.genotype.MutationVariant;
import agh.ics.oop.model.map.MapVariant;
import agh.ics.oop.model.settings.Settings;
import agh.ics.oop.model.settings.SettingsValidator;
import agh.ics.oop.model.settings.exceptions.InvalidSettingsException;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Optional;

public class SettingsParser {
    // optionale tutaj są wymagane poniewaz użytkownik mógł wpisać literę a nie liczbę,w takim wypadku musimy opakować nulla
    private Optional<Integer> mapWidth;
    private Optional<Integer> mapHeight;
    private MapVariant mapVariant;
    private Optional<Integer> fireInterval;
    private Optional<Integer> fireDuration;
    private Optional<Integer> initialPlantsNumber;
    private Optional<Integer> energyOnConsumption;
    private Optional<Integer> dailyNewPlantsNumber;
    private Optional<Integer> initialAnimalsNumber;
    private Optional<Integer> initialAnimalEnergy;
    private Optional<Integer> energyRequiredForReproduction;
    private Optional<Integer> energyLostOnReproduction;
    private Optional<Integer> minMutationsNumber;
    private Optional<Integer> maxMutationsNumber;
    private MutationVariant mutationVariant;
    private Optional<Integer> animalGenomeLength;
    private boolean saveStatistics;
    private final SettingsValidator validator = new SettingsValidator();

    public SettingsParser() {
        mapWidth = Optional.of(0);
        mapHeight = Optional.of(0);
        mapVariant = MapVariant.GLOBE;
        fireInterval = Optional.of(0);
        initialPlantsNumber = Optional.of(0);
        energyOnConsumption = Optional.of(0);
        dailyNewPlantsNumber = Optional.of(0);
        initialAnimalsNumber = Optional.of(0);
        initialAnimalEnergy = Optional.of(0);
        energyRequiredForReproduction = Optional.of(0);
        energyLostOnReproduction = Optional.of(0);
        minMutationsNumber = Optional.of(0);
        maxMutationsNumber = Optional.of(0);
        mutationVariant = MutationVariant.SWAP;
        animalGenomeLength = Optional.of(0);
        saveStatistics = false;
    }

    public SettingsParser(List<String> settings) {
        mapWidth = Optional.of(Integer.parseInt(settings.get(0)));
        mapHeight = Optional.of(Integer.parseInt(settings.get(1)));
        mapVariant = MapVariant.valueOf(settings.get(2));
        fireInterval = Optional.of(Integer.parseInt(settings.get(3)));
        fireDuration = Optional.of(Integer.parseInt(settings.get(4)));
        initialPlantsNumber = Optional.of(Integer.parseInt(settings.get(5)));
        energyOnConsumption = Optional.of(Integer.parseInt(settings.get(6)));
        dailyNewPlantsNumber = Optional.of(Integer.parseInt(settings.get(7)));
        initialAnimalsNumber = Optional.of(Integer.parseInt(settings.get(8)));
        initialAnimalEnergy = Optional.of(Integer.parseInt(settings.get(9)));
        energyRequiredForReproduction = Optional.of(Integer.parseInt(settings.get(10)));
        energyLostOnReproduction = Optional.of(Integer.parseInt(settings.get(11)));
        minMutationsNumber = Optional.of(Integer.parseInt(settings.get(12)));
        maxMutationsNumber = Optional.of(Integer.parseInt(settings.get(13)));
        mutationVariant = MutationVariant.valueOf(settings.get(14));
        animalGenomeLength = Optional.of(Integer.parseInt(settings.get(15)));
        saveStatistics = settings.get(16).equals("true");
    }

    public Settings buildAndValidateSettings() throws InvalidSettingsException {
        checkOptionalProperties();
        var settings = new Settings(mapWidth.get(), mapHeight.get(), mapVariant, fireInterval.orElse(0),
            fireDuration.orElse(0), initialPlantsNumber.get(), energyOnConsumption.get(), dailyNewPlantsNumber.get(), initialAnimalsNumber.get(),
            initialAnimalEnergy.get(), energyRequiredForReproduction.get(), energyLostOnReproduction.get(),
            minMutationsNumber.get(), maxMutationsNumber.get(), mutationVariant, animalGenomeLength.get(),
            saveStatistics);
        validator.validate(settings);
        return settings;
    }

    public SettingsParser parseMapWidth(TextField textField) {
        mapWidth = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseMapHeight(TextField textField) {
        mapHeight = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseMapVariant(ComboBox<String> comboBox) {
        mapVariant = MapVariant.fromDisplayName(comboBox.getValue());
        return this;
    }

    public SettingsParser parseFireInterval(TextField textField) {
        fireInterval = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseFireDuration(TextField textField) {
        fireDuration = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseInitialPlantsNumber(TextField textField) {
        initialPlantsNumber = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseEnergyOnConsumption(TextField textField) {
        energyOnConsumption = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseDailyNewPlantsNumber(TextField textField) {
        dailyNewPlantsNumber = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseInitialAnimalsNumber(TextField textField) {
        initialAnimalsNumber = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseInitialAnimalEnergy(TextField textField) {
        initialAnimalEnergy = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseEnergyRequiredForReproduction(TextField textField) {
        energyRequiredForReproduction = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseEnergyLostOnReproduction(TextField textField) {
        energyLostOnReproduction = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseMinMutationsNumber(TextField textField) {
        minMutationsNumber = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseMaxMutationsNumber(TextField textField) {
        maxMutationsNumber = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseMutationVariant(ComboBox<String> comboBox) {
        mutationVariant = MutationVariant.fromDisplayName(comboBox.getValue());
        return this;
    }

    public SettingsParser parseAnimalGenomeLength(TextField textField) {
        animalGenomeLength = parseIntFromTextField(textField);
        return this;
    }

    public SettingsParser parseSaveStatistics(CheckBox checkBox) {
        saveStatistics = checkBox.isSelected();
        return this;
    }

    private Optional<Integer> parseIntFromTextField(TextField textField) {
        try {
            return Optional.of(Integer.parseInt(textField.getText()));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    private void checkOptionalProperties() throws InvalidSettingsException {
        if (mapWidth.isEmpty()) {
            throw new InvalidSettingsException("Map width must be an integer");
        }
        if (mapHeight.isEmpty()) {
            throw new InvalidSettingsException("Map height must be an integer");
        }
        if (initialPlantsNumber.isEmpty()) {
            throw new InvalidSettingsException("Initial plants number must be an integer");
        }
        if (energyOnConsumption.isEmpty()) {
            throw new InvalidSettingsException("Energy on consumption must be an integer");
        }
        if (dailyNewPlantsNumber.isEmpty()) {
            throw new InvalidSettingsException("Daily new plants number must be an integer");
        }
        if (initialAnimalsNumber.isEmpty()) {
            throw new InvalidSettingsException("Initial animals number must be an integer");
        }
        if (energyRequiredForReproduction.isEmpty()) {
            throw new InvalidSettingsException("Energy required for reproduction must be an integer");
        }
        if (energyLostOnReproduction.isEmpty()) {
            throw new InvalidSettingsException("Energy lost on reproduction must be an integer");
        }
        if (minMutationsNumber.isEmpty()) {
            throw new InvalidSettingsException("Minimum mutations number must be an integer");
        }
        if (maxMutationsNumber.isEmpty()) {
            throw new InvalidSettingsException("Maximum mutations number must be an integer");
        }
        if (animalGenomeLength.isEmpty()) {
            throw new InvalidSettingsException("Animal genome length must be an integer");
        }
        if (fireInterval.isEmpty() && mapVariant == MapVariant.FIRES) {
            throw new InvalidSettingsException("Fire interval must be an integer");
        }
        if (fireDuration.isEmpty() && mapVariant == MapVariant.FIRES) {
            throw new InvalidSettingsException("Fire duration must be an integer");
        }
    }


}
