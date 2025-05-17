package agh.ics.oop.model.element;

import agh.ics.oop.model.WorldElementStatistics;
import agh.ics.oop.model.map.MapDirection;
import agh.ics.oop.model.map.MoveValidator;
import agh.ics.oop.model.util.Vector2d;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private final int initialEnergy;
    private int energy;
    private int currentGene;
    private final ImageView animalRepresentation;
    private final List<Integer> genes;
    private final List<Animal> closestAncestors;
    private final List<Animal> closestDescendants = new ArrayList<>();
    private final int birthDay;
    private int numberOfChildren = 0;
    private int consumedPlantsNumber = 0;
    private int numberOfDescendants = 0;
    private int deathDay = -1;

    private final static List<Image> ANIMAL_IMAGES = List.of(
            new Image("images/chicken.png"),
            new Image("images/cow.png"),
            new Image("images/dog.png"),
            new Image("images/horse.png"),
            new Image("images/rabbit.png")
    );

    private final static List<Image> ENERGY_LEVELS = List.of(
            new Image("images/energy-0.png"),
            new Image("images/energy-1.png"),
            new Image("images/energy-2.png"),
            new Image("images/energy-3.png"),
            new Image("images/energy-4.png"),
            new Image("images/energy-5.png")
    );

       
    public Animal(Vector2d position, int energy, int initialEnergy, List<Integer> genes,
                  int currentGene, MapDirection orientation, int birthDay, List<Animal> parents)
    {
        this.orientation = orientation;
        this.position = position;
        this.energy = energy;
        this.initialEnergy = initialEnergy;
        Random rand = new Random();
        this.animalRepresentation = new ImageView(ANIMAL_IMAGES.get(rand.nextInt(ANIMAL_IMAGES.size())));
        this.genes = genes;
        this.birthDay = birthDay;
        this.closestAncestors = parents;
        this.currentGene = currentGene;
        for (var parent : parents) {
            parent.registerChildBirth(this);
        }
    }

    public void move(MoveValidator validator) {
        energy--;
        orientation = orientation.rotate(genes.get(currentGene));
        currentGene = (currentGene + 1) % genes.size();
        Vector2d newPosition = position.add(orientation.toUnitVector());
        if (validator.isOutOfVerticalBounds(newPosition)) {
            orientation = orientation.opposite();
        } else if (validator.isOutOfHorizontalBounds(newPosition)) {
            position = validator.getCorrectedHorizontalPosition(newPosition);
        } else {
            position = newPosition;
        }
    }

    @Override
    public Vector2d getPosition()
    {
        return position;
    }

    @Override
    public StackPane getResource() {
        int energyLevel;
        if (getEnergy() > initialEnergy * 3) energyLevel = 5;
        else if (getEnergy() > initialEnergy * 1.5) energyLevel = 4;
        else if (getEnergy() > initialEnergy * 0.7) energyLevel = 3;
        else if (getEnergy() > initialEnergy * 0.4) energyLevel = 2;
        else if (getEnergy() > initialEnergy * 0.2) energyLevel = 1;
        else energyLevel = 0;

        ImageView energyImg = new ImageView(ENERGY_LEVELS.get(energyLevel));

        return new StackPane(
                animalRepresentation,
                energyImg
        );
    }

    public MapDirection getOrientation()
    {
        return orientation;
    }

    public void subtractEnergy(int amountToSubtract)
    {
        energy -= amountToSubtract;
    }
  
    public void consumePlant(Plant plant) {
        energy = energy + plant.getEnergyOnConsumption();
        consumedPlantsNumber++;
    }

    public void die(int day) {
        deathDay = day;
        for (var ancestor : closestAncestors) {
            ancestor.registerClosestDescendantsDeath(this, closestDescendants);
        }
        for (var descendant : closestDescendants) {
            descendant.registerClosestAncestorsDeath(this, closestAncestors);
        }
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isDead() {
        return deathDay != -1;
    }

    public List<Integer> getGenome()
    {
        return genes;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    @Override
    public boolean isSelectable() {
        return true;
    }

    @Override
    public WorldElementStatistics getStatistics() {
        return new WorldElementStatistics(
            genes, currentGene, energy, consumedPlantsNumber, numberOfChildren,
            numberOfDescendants, birthDay, isDead(), deathDay
        );
    }

    private void registerChildBirth(Animal child) {
        numberOfChildren++;
        numberOfDescendants++;
        closestDescendants.add(child);
        for (var ancestor : closestAncestors) {
            ancestor.registerDescendantBirth();
        }
    }

    private void registerDescendantBirth() {
        numberOfDescendants++;
        for (var ancestor : closestAncestors) {
            ancestor.registerDescendantBirth();
        }
    }

    private void registerClosestAncestorsDeath(Animal ancestor, List<Animal> ancestorsClosestAncestors) {
        closestAncestors.remove(ancestor);
        closestAncestors.addAll(ancestorsClosestAncestors);
    }

    private void registerClosestDescendantsDeath(Animal descendant, List<Animal> descendantsClosestDescendants) {
        closestDescendants.remove(descendant);
        closestDescendants.addAll(descendantsClosestDescendants);
    }

}
