package agh.ics.oop;


import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.model.settings.Settings;
import agh.ics.oop.model.settings.exceptions.InvalidSettingsException;
import agh.ics.oop.presenter.SettingsParser;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CSVHandler {
    private final File configFolder = Paths.get("configs").toFile();
    private final static String STATS_DIRECTORY = "stats_saves";
    private boolean firstWrite = true;

    public void exportSettings(Stage stage, Settings settings) throws IOException {
        Path path = chooseDirectory(stage);
        writeSettings(path, settings);
    }

    public Settings importSettings(Stage stage) throws IOException, InvalidSettingsException {
        Path path = chooseFile(stage);
        return settingsReader(path);
    }

    private Path chooseFile(Stage stage) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz skąd wczytać konfigurację");

        // Ustawienie filtra rozszerzeń
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Pliki CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Ustawienie domyślnego katalogu aby go potem nie szukac
        fileChooser.setInitialDirectory(configFolder);

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            return selectedFile.toPath();
        } else {
            throw new IOException(); // użytkownik nie wybrał folderu
        }
    }

    private Path chooseDirectory(Stage stage) throws IOException {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Wybierz gdzie zapisać konfigurację");

        // Ustawienie domyślnego katalogu aby go potem nie szukac
        dirChooser.setInitialDirectory(configFolder);

        File selectedDirectory = dirChooser.showDialog(stage);
        if (selectedDirectory != null) {
            return selectedDirectory.toPath();
        } else {
            throw new IOException(); // użytkownik nie wybrał folderu
        }
    }

    public Settings settingsReader(Path path) throws IOException, InvalidSettingsException {
        List<String> data;
        try(BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            var stringData = br.readLine().split(",");
            data = new ArrayList<>(Arrays.asList(stringData));
        }
        return new SettingsParser(data).buildAndValidateSettings();
    }

    public void writeSettings(Path path, Settings settings) throws IOException {
        var outputData = "%d,%d,%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s,%d,%b".formatted(
                settings.mapWidth(),
                settings.mapHeight(),
                settings.mapVariant().toString(),
                settings.fireInterval(),
                settings.fireDuration(),
                settings.initialPlantsNumber(),
                settings.energyOnConsumption(),
                settings.dailyNewPlantsNumber(),
                settings.initialAnimalsNumber(),
                settings.initialAnimalEnergy(),
                settings.energyRequiredForReproduction(),
                settings.energyLostOnReproduction(),
                settings.minMutationsNumber(),
                settings.maxMutationsNumber(),
                settings.mutationVariant().toString(),
                settings.animalGenomeLength(),
                settings.saveStatistics()
        );
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path.toString() + "/settings-%s.csv".formatted(UUID.randomUUID())))) {
            bw.write(outputData);
        }
    }

    public void writeStatistics(WorldMap worldMap) {
        var path = STATS_DIRECTORY + "/stats-%s.csv".formatted(worldMap.getID());
        var stats = worldMap.getStatistics();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8, StandardOpenOption.APPEND,StandardOpenOption.CREATE)) {
            var outputData = "%d,%d,%d,%d,%s,%f,%f,%f\n".formatted(
                    stats.getDay(),
                    stats.getAnimalsNumber(),
                    stats.getPlantsNumber(),
                    stats.getFreePositionsNumber(),
                    stats.getGenotypeRanking().stream().limit(3).toList(),
                    stats.getAverageEnergyLevel().orElse(0.0),
                    stats.getAverageDeadLifespan().orElse(0.0),
                    stats.getAverageChildrenNumber().orElse(0.0)
            );
            if(firstWrite)
            {
                writer.write("Day,Animal number,Plants number,Free positions number,Genotype ranking,Average energy level,Average dead lifespan,Average children number\n");
                firstWrite = false;
            }
            writer.append(outputData);
        } catch (IOException exception) {
            System.out.printf("Failed to write statistics. %s\n", exception.getMessage()); // ten błąd ma bardzo małą szansę wystąpienia więc piszemy tylko log na konsoli
        }
    }
}