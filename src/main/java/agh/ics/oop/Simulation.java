package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animalsList;
    private final List<MoveDirection> moveDirectionsList;
    private final WorldMap world;
    private int numOfAnimalsOnMap;

    public Simulation(List<Vector2d> vectorsList, List<MoveDirection> moveDirectionsList, WorldMap world) {
        this.animalsList = new ArrayList<>(); // lepiej użyć ArrayList, ponieważ zwierzaki ustawiamy tylko raz, a potem wielokrotnie je wybieramy z listy
        this.moveDirectionsList = moveDirectionsList;
        this.world = world;

        for (Vector2d vector : vectorsList)
        {
            Animal newAnimal = new Animal(vector);
            if(world.place(newAnimal))
            {
                animalsList.add(newAnimal);
                numOfAnimalsOnMap++;
            }
        }
    }

    public void run()
    {
        for(int i = 0; i < moveDirectionsList.size(); i++)
        {
            int animalIndex = i % animalsList.size();
            world.move(animalsList.get(animalIndex), moveDirectionsList.get(i));
            System.out.println(world);
        }
    }

    int sizeOfAnimalList() // package private dla testów (numberOfAnimalsOnMapMatchesList)
    {
        return animalsList.size();
    }

    int getNumOfAnimalsOnMap() // package private dla testów (numberOfAnimalsOnMapMatchesList)
    {
        return numOfAnimalsOnMap;
    }

}
