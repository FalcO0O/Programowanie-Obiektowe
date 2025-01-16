package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private final static int CELL_SIZE = 30;
    private WorldMap worldMap;
    private final List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4)); // zahardocowane pozycje zwierzaków;

    @FXML
    private Label moveInfoLabel;

    @FXML
    private TextField movesListTextField;

    @FXML
    private GridPane mapGrid;

    public void setWorldMap(WorldMap map) {
            this.worldMap = map;
    }

    private void drawMap() {
        clearGrid();

        Vector2d lowerLeft = worldMap.getCurrentBounds().lowerLeft();
        Vector2d upperRight = worldMap.getCurrentBounds().upperRight();

        int width = upperRight.getX() - lowerLeft.getX() + 1;
        int height = upperRight.getY() - lowerLeft.getY() + 1;

        addHeaders(width, height, lowerLeft);

        addElements(lowerLeft);

        setupGridConstraints(lowerLeft, upperRight);

        centerGrid();
    }

    private void setupGridConstraints(Vector2d lowerLeft, Vector2d upperRight) {
        // Kolumny
        for (int i = lowerLeft.getX(); i <= upperRight.getX() + 1; i++) {
            ColumnConstraints col = new ColumnConstraints(CELL_SIZE);
            mapGrid.getColumnConstraints().add(col);
        }

        // Wiersze
        for (int i = lowerLeft.getY(); i <= upperRight.getY() + 1; i++) {
            RowConstraints row = new RowConstraints(CELL_SIZE);
            mapGrid.getRowConstraints().add(row);
        }
    }

    private void addHeaders(int width, int height, Vector2d lowerLeft) {
        Label cornerLabel = new Label("X/Y");
        mapGrid.add(cornerLabel, 0, 0);
        GridPane.setHalignment(cornerLabel, HPos.CENTER);

        // Nagłówki kolumn (X)
        for (int i = 1; i <= width; i++) {
            Label xLabel = new Label(String.valueOf(lowerLeft.getX() + i - 1));
            mapGrid.add(xLabel, i, 0);
            GridPane.setHalignment(xLabel, HPos.CENTER);
        }

        // Nagłówki wierszy (Y)
        for (int i = 1; i <= height; i++) {
            Label yLabel = new Label(String.valueOf(lowerLeft.getY() + i - 1));
            mapGrid.add(yLabel, 0, height - i + 1); // Odwrócenie osi Y
            GridPane.setHalignment(yLabel, HPos.CENTER);
        }
    }

    private void addElements(Vector2d lowerLeft) {
        worldMap.getElements().forEach(element -> {
            Vector2d position = element.getPosition();
            WorldElementBox elementBox = new WorldElementBox(element);
            mapGrid.add(elementBox, position.getX() - lowerLeft.getX() + 1, position.getY() - lowerLeft.getY() + 1);
            GridPane.setHalignment(elementBox, HPos.CENTER);
        });
    }


    private void centerGrid() {
        mapGrid.setAlignment(Pos.CENTER);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            moveInfoLabel.setText(message);
        });
    }

    @FXML
    private void onSimulationStartClicked(ActionEvent actionEvent) {
        String[] stringOfMoves = movesListTextField.getText().split(" ");
        List<MoveDirection> listOfMoves = OptionsParser.parseMoveDirections(stringOfMoves);
        Simulation simulation = new Simulation(positions, listOfMoves, worldMap);
        SimulationEngine simulationEngine = new SimulationEngine(new ArrayList<>(List.of(simulation)));
        simulationEngine.runAsync();
    }
}
