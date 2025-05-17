package agh.ics.oop.model.element;

import agh.ics.oop.model.util.Vector2d;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Plant implements WorldElement {
    private final Vector2d position;
    private static final Image PLANT = new Image("images/grass.png");
    private final ImageView plantImage = new ImageView(PLANT);
    private final int energyOnConsumption;


    public Plant(Vector2d position, int energyOnConsumption)
    {
        this.position = position;
        this.energyOnConsumption = energyOnConsumption;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public StackPane getResource() {
        return new StackPane(plantImage);
    }

    public int getEnergyOnConsumption() {
        return energyOnConsumption;
    }

}
