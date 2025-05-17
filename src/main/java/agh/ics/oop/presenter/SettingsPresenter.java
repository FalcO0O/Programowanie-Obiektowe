package agh.ics.oop.presenter;

import agh.ics.oop.CSVHandler;
import agh.ics.oop.SimulationFactory;
import agh.ics.oop.model.genotype.MutationVariant;
import agh.ics.oop.model.map.MapVariant;
import agh.ics.oop.model.settings.Settings;
import agh.ics.oop.model.settings.exceptions.InvalidSettingsException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsPresenter {

    private final ExecutorService simulationExecutor = Executors.newFixedThreadPool(4);
    private int numOfSimulations = 0;
    @FXML
    private TextField mapWidth;
    @FXML
    private TextField mapHeight;
    @FXML
    private ComboBox<String> mapVariant;
    @FXML
    private TextField fireInterval;
    @FXML
    private TextField fireDuration;
    @FXML
    private TextField initialPlantsNumber;
    @FXML
    private TextField energyOnConsumption;
    @FXML
    private TextField dailyNewPlantsNumber;
    @FXML
    private TextField initialAnimalsNumber;
    @FXML
    private TextField initialAnimalEnergy;
    @FXML
    private TextField energyRequiredForReproduction;
    @FXML
    private TextField energyLostOnReproduction;
    @FXML
    private TextField minMutationsNumber;
    @FXML
    private TextField maxMutationsNumber;
    @FXML
    private ComboBox<String> mutationVariant;
    @FXML
    private TextField animalGenomeLength;
    @FXML
    private CheckBox saveStatistics;
    @FXML
    private GridPane grid;
    @FXML
    private Label errorLabel;
    CSVHandler csvHandler = new CSVHandler();
    private final Stage filePickerStage = new Stage();;

    public SettingsPresenter() {
        Platform.runLater(() -> {
            var mapVariants = MapVariant.getAllDisplayNames();
            mapVariant.setItems(FXCollections.observableArrayList(mapVariants));
            mapVariant.setValue(mapVariants.getFirst());
            var mutationVariants = MutationVariant.getAllDisplayNames();
            mutationVariant.setItems(FXCollections.observableArrayList(mutationVariants));
            mutationVariant.setValue(mutationVariants.getFirst());
            int rows = 18;
            var elements = grid.getChildren();
            for (int i = 0; i < rows * 2; i++) {
                GridPane.setRowIndex(elements.get(i), i % rows);
                GridPane.setColumnIndex(elements.get(i), i / rows);
            }
            var button = grid.getChildren().getLast();
            GridPane.setRowIndex(button, rows);
            GridPane.setColumnIndex(button, 0);
            GridPane.setColumnSpan(grid.getChildren().getLast(), 2);
            GridPane.setHalignment(button, HPos.CENTER);
            fireInterval.setDisable(MapVariant.fromDisplayName(mapVariant.getValue()) != MapVariant.FIRES);
            fireDuration.setDisable(MapVariant.fromDisplayName(mapVariant.getValue()) != MapVariant.FIRES);
            mapVariant.setOnAction(actionEvent -> {
                fireInterval.setDisable(MapVariant.fromDisplayName(mapVariant.getValue()) != MapVariant.FIRES);
                fireDuration.setDisable(MapVariant.fromDisplayName(mapVariant.getValue()) != MapVariant.FIRES);
            });
        });
    }

    @FXML
    private void onSimulationStartClicked() {
        Platform.runLater(() -> {
            try {
                startNewSimulationWindow();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @FXML
    private void onLoadConfigurationClicked() {
        try {
            Settings settings = csvHandler.importSettings(filePickerStage);
            setInputValues(settings);
            Platform.runLater(() -> {
                errorLabel.setText(""); // kasowanie błędu w przypadku gdyby wczesniej wystąpił
            });
        } catch (InvalidSettingsException exception) {
            Platform.runLater(() -> {
                errorLabel.setText(exception.getMessage());
            });
        } catch (Exception exception) {
            Platform.runLater(() -> {
                errorLabel.setText("Failed to read file");
            });
        }
    }

    @FXML
    private void onExportConfigurationClicked() {
        try {
            Settings settings = getInputValues();
            csvHandler.exportSettings(filePickerStage, settings);
            Platform.runLater(() -> {
                errorLabel.setText(""); // kasowanie błędu w przypadku gdyby wczesniej wystąpił
            });
        } catch (InvalidSettingsException exception) {
            Platform.runLater(() -> {
                errorLabel.setText(exception.getMessage());
            });
        } catch (Exception exception) {
            Platform.runLater(() -> {
                errorLabel.setText("Failed to write file");
            });
        }
    }


    private void startNewSimulationWindow() throws IOException {
        Settings settings;
        try {
            settings = getInputValues();
        } catch (InvalidSettingsException exception) {
            Platform.runLater(() -> {
                errorLabel.setText(exception.getMessage());
            });
            return;
        }
        errorLabel.setText("");
        Path originalFxmlPath = new File(Objects.requireNonNull(getClass() // taki hack, aby ścieżka była poprawnie odnaleziona (miało problem z ':' w C:/...)
                .getResource("/simulation.fxml"))
            .getFile()).toPath();
        Path tempFxmlPath = Files.createTempFile("simulation-", ".fxml");
        tempFxmlPath.toFile().deleteOnExit();
        Files.copy(originalFxmlPath, tempFxmlPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(tempFxmlPath.toUri().toURL());
        BorderPane newViewRoot = loader.load();

        SimulationPresenter newPresenter = loader.getController();

        Stage newStage = new Stage();
        configureNewStage(newStage, newViewRoot);
        newStage.show();

        simulationExecutor.submit(() -> {
            var simulationFactory = new SimulationFactory();
            var simulation = simulationFactory.createSimulation(settings);
            newStage.setOnCloseRequest(event -> {
                simulation.end();
            });
            newPresenter.setWorldMap(simulation.getWorldMap());
            newPresenter.setSimulationControlsListener(simulation);
            simulation.getWorldMap().addObserver(newPresenter);
            Thread thread = new Thread(simulation);
            thread.start();
        });
    }

    private void configureNewStage(Stage stage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setX(20); // dzięki temu nowe okno nie pokrywa się z pierwotnym
        stage.setY(20);
        stage.setTitle("Simulation nr: " + ++numOfSimulations);
    }

    private Settings getInputValues() throws InvalidSettingsException {
        return new SettingsParser()
            .parseMapWidth(mapWidth)
            .parseMapHeight(mapHeight)
            .parseMapVariant(mapVariant)
            .parseInitialPlantsNumber(initialPlantsNumber)
            .parseEnergyOnConsumption(energyOnConsumption)
            .parseDailyNewPlantsNumber(dailyNewPlantsNumber)
            .parseInitialAnimalsNumber(initialAnimalsNumber)
            .parseInitialAnimalEnergy(initialAnimalEnergy)
            .parseEnergyRequiredForReproduction(energyRequiredForReproduction)
            .parseEnergyLostOnReproduction(energyLostOnReproduction)
            .parseMinMutationsNumber(minMutationsNumber)
            .parseMaxMutationsNumber(maxMutationsNumber)
            .parseMutationVariant(mutationVariant)
            .parseAnimalGenomeLength(animalGenomeLength)
            .parseFireInterval(fireInterval)
            .parseFireDuration(fireDuration)
            .parseSaveStatistics(saveStatistics)
            .buildAndValidateSettings();
    }

    private void setInputValues(Settings settings) {
        mapWidth.setText(String.valueOf(settings.mapWidth()));
        mapHeight.setText(String.valueOf(settings.mapHeight()));
        mapVariant.setValue(settings.mapVariant().getDisplayName());
        initialPlantsNumber.setText(String.valueOf(settings.initialPlantsNumber()));
        energyOnConsumption.setText(String.valueOf(settings.energyOnConsumption()));
        dailyNewPlantsNumber.setText(String.valueOf(settings.dailyNewPlantsNumber()));
        initialAnimalsNumber.setText(String.valueOf(settings.initialAnimalsNumber()));
        initialAnimalEnergy.setText(String.valueOf(settings.initialAnimalEnergy()));
        energyRequiredForReproduction.setText(String.valueOf(settings.energyRequiredForReproduction()));
        energyLostOnReproduction.setText(String.valueOf(settings.energyLostOnReproduction()));
        minMutationsNumber.setText(String.valueOf(settings.minMutationsNumber()));
        maxMutationsNumber.setText(String.valueOf(settings.maxMutationsNumber()));
        mutationVariant.setValue(settings.mutationVariant().getDisplayName());
        animalGenomeLength.setText(String.valueOf(settings.animalGenomeLength()));
        fireInterval.setText(String.valueOf(settings.fireInterval()));
        fireDuration.setText(String.valueOf(settings.fireDuration()));
        saveStatistics.setSelected(settings.saveStatistics());
    }

}
