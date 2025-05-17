package agh.ics.oop.model;

import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.SortedAnimalGroup;
import agh.ics.oop.model.genotype.GenotypeGenerator;
import agh.ics.oop.model.genotype.MutationVariant;
import agh.ics.oop.model.genotype.SmallCorrectionGenotypeGenerator;
import agh.ics.oop.model.genotype.SwapGenotypeGenerator;
import agh.ics.oop.model.map.MapDirection;
import agh.ics.oop.model.util.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalFactory {

    private final int initialAnimalEnergy;
    private final int energyRequiredForReproduction;
    private final int energyLostOnReproduction;
    private final int animalGenomeLength;
    private final int minMutationsNumber;
    private final int maxMutationsNumber;
    private final Random rand = new Random();
    private final MutationVariant mutationVariant;


    public AnimalFactory(int initialAnimalEnergy, int energyRequiredForReproduction,
                         int energyLostOnReproduction, int minMutationsNumber, int maxMutationsNumber,
                         MutationVariant mutationVariant, int animalGenomeLength) {
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.energyRequiredForReproduction = energyRequiredForReproduction;
        this.energyLostOnReproduction = energyLostOnReproduction;
        this.minMutationsNumber = minMutationsNumber;
        this.maxMutationsNumber = maxMutationsNumber;
        this.animalGenomeLength = animalGenomeLength;
        this.mutationVariant = mutationVariant;
    }

    public List<Animal> handleReproduction(List<SortedAnimalGroup> animalGroups, int day) {
        List<Animal> newAnimals = new ArrayList<>();
        for (var animalGroup : animalGroups) {
            int i = 0;
            var animals = animalGroup.getAnimals();
            while (i + 1 < animals.size() && canReproduce(animals.get(i + 1))) {
                var newAnimal = reproduce(animals.get(i), animals.get(i + 1), day);
                newAnimals.add(newAnimal);
                i += 2;
            }
        }
        return newAnimals;
    }

    private boolean canReproduce(Animal animal) {
        return animal.getEnergy() >= energyRequiredForReproduction;
    }

    private Animal reproduce(Animal animal1, Animal animal2, int day) {
        animal1.subtractEnergy(energyLostOnReproduction);
        animal2.subtractEnergy(energyLostOnReproduction);

        GenotypeGenerator genotypeGenerator = switch (mutationVariant) {
            case SMALL_CORRECTION -> new SmallCorrectionGenotypeGenerator(
                animal1.getGenome(), animal1.getEnergy(),
                animal2.getGenome(), animal2.getEnergy(),
                minMutationsNumber, maxMutationsNumber
            );
            case SWAP -> new SwapGenotypeGenerator(
                animal1.getGenome(), animal1.getEnergy(),
                animal2.getGenome(), animal2.getEnergy(),
                minMutationsNumber, maxMutationsNumber
            );
        };
        var genotype = genotypeGenerator.generate();

        var orientation = MapDirection.getRandom();
        return new Animal(animal1.getPosition(), 2 * energyLostOnReproduction, initialAnimalEnergy, genotype,
            rand.nextInt(animalGenomeLength), orientation, day, new ArrayList<>(List.of(animal1, animal2)));
    }

    public Animal createAnimal(Vector2d position, int day) {
        List<Integer> genesList = new ArrayList<>();
        var orientation = MapDirection.getRandom();
        for (int i = 0; i < animalGenomeLength; i++) genesList.add(rand.nextInt(8));
        return new Animal(position, initialAnimalEnergy, initialAnimalEnergy, genesList,
            rand.nextInt(animalGenomeLength), orientation, day, new ArrayList<>());
    }

}
