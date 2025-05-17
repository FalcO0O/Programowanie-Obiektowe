package agh.ics.oop.presenter;

import agh.ics.oop.SimulationControlsListener;
import agh.ics.oop.model.WorldMapStatisticsProvider;
import agh.ics.oop.model.element.WorldElement;
import agh.ics.oop.model.map.MapChangeListener;
import agh.ics.oop.model.map.ReadOnlyWorldMap;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.model.util.Vector2d;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

import java.util.HashSet;
import java.util.stream.Collectors;

public class SimulationPresenter implements MapChangeListener {
    private final static int SIMULATION_HEIGHT = 600;
    private final static int SIMULATION_WIDTH = 600;
    private int cellSize, width, height;
    private final static String PLACEHOLDER_TEXT = "--";
    private ReadOnlyWorldMap worldMap;
    private static final Image GOOD_IMAGE = new Image("/images/good-terrain.png");
    private static final Image BAD_IMAGE = new Image("/images/bad-terrain.png");
    private static final Image HIGHLIGHT_IMAGE = new Image("/images/highlight.png");
    private static final Image GENOTYPE_HIGHLIGHT_IMAGE = new Image("/images/highlight-genotype.png");
    private boolean isSimulationStopped = false;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Label animalsNumber;
    @FXML
    private Label plantsNumber;
    @FXML
    private Label freePositionsNumber;
    @FXML
    private Label genotypeRanking;
    @FXML
    private Label averageEnergyLevel;
    @FXML
    private Label averageDeadLifespan;
    @FXML
    private Label averageChildrenNumber;

    @FXML
    private Label animalGenes;
    @FXML
    private Label animalCurrentGene;
    @FXML
    private Label animalEnergy;
    @FXML
    private Label animalConsumedPlantsNumber;
    @FXML
    private Label animalChildrenNumber;
    @FXML
    private Label animalDescendantsNumber;
    @FXML
    private Label animalLifespanOrDeathDayDescription;
    @FXML
    private Label animalLifespanOrDeathDay;
    @FXML
    private Button stopButton;
    @FXML
    private Slider tickRateSlider;
    @FXML
    private Button showPriorityPlantsButton;
    @FXML
    private Button showDominantGenotypeButton;
    private boolean showPriorityPlants = false;
    private boolean showDominantGenotype = false;
    private SimulationControlsListener controlsListener;
    private WorldElement selectedElement;

    public void setWorldMap(WorldMap map) {
        worldMap = map;

        width = map.getWidth();
        height = map.getHeight();

        cellSize = Math.max(30, Math.min(SIMULATION_HEIGHT / (height + 1), SIMULATION_WIDTH / (width + 1)));
        showPriorityPlantsButton.setDisable(true);
        showDominantGenotypeButton.setDisable(true);
        tickRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            controlsListener.setTickRate(newValue.intValue());
        });
    }

    public void setSimulationControlsListener(SimulationControlsListener listener) {
        controlsListener = listener;
    }

    private void drawMap() {
        clearGrid();

        // Set background after setting size constraints
        setGridBackground();

        addHeaders(width, height);
        addWorldElements();

        // total columns and rows including headers
        int columnsCount = width + 1;  // 1 for X headers
        int rowsCount = height + 1;    // 1 for Y headers

        // define column constraints
        for (int i = 0; i < columnsCount; i++) {
            ColumnConstraints col = new ColumnConstraints(cellSize);
            mapGrid.getColumnConstraints().add(col);
        }

        // define row constraints
        for (int i = 0; i < rowsCount; i++) {
            RowConstraints row = new RowConstraints(cellSize);
            mapGrid.getRowConstraints().add(row);
        }

        // set fixed size to prevent scaling
        mapGrid.setPrefSize(columnsCount * cellSize, rowsCount * cellSize);
        mapGrid.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        mapGrid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        centerGrid();
    }

    @FXML
    private void onShowPriorityPlantsClicked() {
        showPriorityPlants = !showPriorityPlants;
        drawMap();
    }

    @FXML
    private void onShowDominantGenotypeClicked() {
        showDominantGenotype = !showDominantGenotype;
        drawMap();
    }

    @FXML
    private void onStopButtonClicked() {
        isSimulationStopped = !isSimulationStopped;
        if (isSimulationStopped) {
            showPriorityPlantsButton.setDisable(false);
            showDominantGenotypeButton.setDisable(false);
            stopButton.setText("Resume simulation");
            controlsListener.stop();
        } else {
            showPriorityPlantsButton.setDisable(true);
            showDominantGenotypeButton.setDisable(true);
            showPriorityPlants = false;
            showDominantGenotype = false;
            stopButton.setText("Stop simulation");
            controlsListener.resume();
        }
    }

    private void addHeaders(int width, int height) {
        Label cornerLabel = new Label("X/Y");
        mapGrid.add(cornerLabel, 0, 0);
        GridPane.setHalignment(cornerLabel, HPos.CENTER);

        // X-axis headers
        for (int i = 1; i <= width; i++) {
            Label xLabel = new Label(String.valueOf(i - 1));
            mapGrid.add(xLabel, i, 0);
            GridPane.setHalignment(xLabel, HPos.CENTER);
        }

        // Y-axis headers (reversed)
        for (int i = 1; i <= height; i++) {
            Label yLabel = new Label(String.valueOf(i - 1));
            mapGrid.add(yLabel, 0, height - i + 1);
            GridPane.setHalignment(yLabel, HPos.CENTER);
        }
    }

    private void addWorldElements() {
        var dominantGenotype = new HashSet<WorldElement>(worldMap.getAnimalsWithDominantGenotype());
        for (int i = 1; i <= width; i++) {
            for(int j = 1; j <= height; j++) {
                var position = new Vector2d(i - 1, j - 1);
                if (worldMap.isOccupied(position)) {
                    var element = worldMap.primaryObjectAt(position);
                    var resource = element.getResource();
                    if (worldMap.positionContainsElement(selectedElement, position))
                    {
                        resource.getChildren().add(new ImageView(HIGHLIGHT_IMAGE));
                    }
                    if (showDominantGenotype && dominantGenotype.contains(element)) {
                        resource.getChildren().add(new ImageView(GENOTYPE_HIGHLIGHT_IMAGE));
                    }
                    var box = new WorldElementBox(resource, cellSize);
                    mapGrid.add(box, i, worldMap.getHeight() - j + 1);
                    GridPane.setHalignment(box, HPos.CENTER);
                    if (element.isSelectable()) {
                        box.setOnMouseClicked((mouseEvent -> {
                            selectedElement = element;
                            drawElementStatistics();
                            drawMap();
                        }));
                    }
                }
            }
        }
    }

    private void centerGrid() {
        mapGrid.setAlignment(Pos.CENTER);
    }

    private void clearGrid() {
        mapGrid.getChildren().clear();
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void setGridBackground() {
        var priorityPositions = worldMap.getPriorityPlantPositions();
        for(int i = 1; i <= height; i++) {
            for(int j = 1; j <= width; j++) {
                var position = new Vector2d(i - 1, j - 1);
                WorldElementBox box = priorityPositions.contains(position) && showPriorityPlants ?
                        new WorldElementBox(GOOD_IMAGE, cellSize) :
                        new WorldElementBox(BAD_IMAGE, cellSize);
                mapGrid.add(box, i, worldMap.getHeight() - j + 1);
            }
        }
    }

    private void drawMapStatistics(WorldMapStatisticsProvider statistics) {
        animalsNumber.setText(Integer.toString(statistics.getAnimalsNumber()));
        plantsNumber.setText(Integer.toString(statistics.getPlantsNumber()));
        freePositionsNumber.setText(Integer.toString(statistics.getFreePositionsNumber()));
        genotypeRanking.setText(statistics
            .getGenotypeRanking()
            .stream()
            .limit(3)
            .map(entry -> "%s (%d)\n".formatted(entry.genotype(), entry.frequency()))
            .collect(Collectors.joining()));
        var energyLevel = statistics.getAverageEnergyLevel();
        averageEnergyLevel.setText(energyLevel.isPresent() ? String.format("%.2f", energyLevel.get()) : PLACEHOLDER_TEXT);
        var deadLifespan = statistics.getAverageDeadLifespan();
        averageDeadLifespan.setText(deadLifespan.isPresent() ? String.format("%.2f", deadLifespan.get()) : PLACEHOLDER_TEXT);
        var childrenNumber = statistics.getAverageChildrenNumber();
        averageChildrenNumber.setText(childrenNumber.isPresent() ? String.format("%.2f", childrenNumber.get()) : PLACEHOLDER_TEXT);
    }

    private void drawElementStatistics() {
        var stats = selectedElement.getStatistics();
        animalGenes.setText(stats
            .genome()
            .stream()
            .map(Object::toString)
            .collect(Collectors.joining()));
        animalCurrentGene.setText(Integer.toString(stats.currentGene()));
        animalEnergy.setText(Integer.toString(stats.energy()));
        animalConsumedPlantsNumber.setText(Integer.toString(stats.consumedPlantsNumber()));
        animalChildrenNumber.setText(Integer.toString(stats.childrenNumber()));
        animalDescendantsNumber.setText(Integer.toString(stats.descendantsNumber()));
        animalLifespanOrDeathDayDescription.setText(stats.isDead() ? "Death day:" : "Lifespan:");
        animalLifespanOrDeathDay.setText(Integer.toString(
            stats.isDead() ? stats.deathDay() : (worldMap.getDay() - stats.birthDay())));

    }

    private void drawPlaceholderElementStatistics() {
        animalGenes.setText(PLACEHOLDER_TEXT);
        animalCurrentGene.setText(PLACEHOLDER_TEXT);
        animalEnergy.setText(PLACEHOLDER_TEXT);
        animalConsumedPlantsNumber.setText(PLACEHOLDER_TEXT);
        animalChildrenNumber.setText(PLACEHOLDER_TEXT);
        animalDescendantsNumber.setText(PLACEHOLDER_TEXT);
        animalLifespanOrDeathDayDescription.setText("Lifespan:");
        animalLifespanOrDeathDay.setText(PLACEHOLDER_TEXT);
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            drawMapStatistics(worldMap.getStatistics());
            if (selectedElement != null) {
                drawElementStatistics();
            } else {
                drawPlaceholderElementStatistics();
            }
        });
    }
}
