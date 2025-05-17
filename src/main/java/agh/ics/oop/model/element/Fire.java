package agh.ics.oop.model.element;

import agh.ics.oop.model.util.Vector2d;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Fire implements WorldElement {

    private final Vector2d position;
    private static final Image IMAGE = new Image("images/fire.png");
    private final ImageView imageView = new ImageView(IMAGE);


    public Fire(Vector2d position)
    {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public StackPane getResource() {
        return new StackPane(imageView);
    }

}
