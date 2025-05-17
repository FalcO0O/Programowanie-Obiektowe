package agh.ics.oop.presenter;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class WorldElementBox extends VBox {

    public WorldElementBox(StackPane stackPane, int imageSize) {
        resizeStackPane(stackPane, imageSize);

        this.getChildren().addAll(stackPane);
        this.setAlignment(Pos.CENTER);
    }

    public WorldElementBox(Image image, int imageSize) {
        ImageView imageView = new ImageView(image);
        resizeImageView(imageView, imageSize);

        this.getChildren().addAll(imageView);
        this.setAlignment(Pos.CENTER);
    }

    private void resizeStackPane(StackPane stackPane, int imageSize) {
        for (var node : stackPane.getChildren()) {
            if (node instanceof ImageView imageView) {
                resizeImageView(imageView, imageSize);
            }
        }
    }
    private void resizeImageView(ImageView imageView, int imageSize) {
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);
        imageView.setPreserveRatio(true); // Maintain aspect ratio
        imageView.setSmooth(true);        // Enable smooth scaling
        imageView.setCache(true);         // Cache for performance
    }
}
