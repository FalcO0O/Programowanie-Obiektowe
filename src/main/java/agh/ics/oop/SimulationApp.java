package agh.ics.oop;

import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalTime;

public class SimulationApp extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        ConsoleMapDisplay console = new ConsoleMapDisplay();
        GrassField grassMap = new GrassField(10);

        SimulationPresenter presenter = loader.getController();
        grassMap.addObserver(presenter);
        grassMap.addObserver(console);
        grassMap.addObserver((WorldMap worldMap, String message) -> System.out.println("%s %s".formatted(LocalTime.now(), message)));
        grassMap.addObserver(new FileMapDisplay());
        presenter.setWorldMap(grassMap);

        configureStage(primaryStage, viewRoot);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
