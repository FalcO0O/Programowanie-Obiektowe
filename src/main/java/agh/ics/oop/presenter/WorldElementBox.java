package agh.ics.oop.presenter;

import agh.ics.oop.model.WorldElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class WorldElementBox extends VBox {

    private static final int IMAGE_SIZE = 20;

    public WorldElementBox(WorldElement element) {
        ImageView imageView = new ImageView(element.getResource());
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);

        Label positionLabel = new Label(element.getPosition().toString());
        this.getChildren().addAll(imageView, positionLabel);
        this.setAlignment(Pos.CENTER);
    }
}
