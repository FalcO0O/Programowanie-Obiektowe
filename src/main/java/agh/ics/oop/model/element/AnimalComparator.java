package agh.ics.oop.model.element;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal animal1, Animal animal2) {
        if (animal1.getEnergy() != animal2.getEnergy()) {
            return animal1.getEnergy() - animal2.getEnergy();
        }
        if (animal1.getBirthDay() != animal2.getBirthDay()) {
            return animal2.getBirthDay() - animal1.getBirthDay();
        }
        if (animal1.getNumberOfChildren() != animal2.getNumberOfChildren()) {
            return animal1.getNumberOfChildren() - animal2.getNumberOfChildren();
        }
        // hashCode jest "losowy" ale nie łamie kontraktu Comparatora
        return animal1.hashCode() - animal2.hashCode();
    }

}
