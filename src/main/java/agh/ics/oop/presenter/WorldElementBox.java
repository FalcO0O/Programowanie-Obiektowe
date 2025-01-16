package agh.ics.oop.presenter;

import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class WorldElementBox extends VBox {

    private static final int IMAGE_SIZE = 20;

    public WorldElementBox(WorldElement element) {
        String resourcePath = "/" + element.getResourceName(); // nie zdaje sobie Pan sprawy ile mi zeszło aby odnaleźć to "/" :(
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(resourcePath)));

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);

        Label positionLabel = new Label(element.getPosition().toString());
        this.getChildren().addAll(imageView, positionLabel);
        this.setAlignment(Pos.CENTER);
    }
}
