package agh.ics.oop.model.element;

import agh.ics.oop.model.util.Vector2d;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;

public class SortedAnimalGroup implements WorldElement {

    private final List<Animal> animals;
    private final Vector2d position;
    private final static Image IMAGE = new Image("/images/many-animals.png");

    public SortedAnimalGroup(List<Animal> animals) {
        AnimalComparator animalComparator = new AnimalComparator();
        this.animals = animals.stream().sorted(animalComparator).toList().reversed();
        this.position = animals.getFirst().getPosition();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public StackPane getResource() {
        ImageView imageView = new ImageView(IMAGE);
        return new StackPane(imageView);
    }

}
