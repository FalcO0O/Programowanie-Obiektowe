package agh.ics.oop;

import agh.ics.oop.model.*;
import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
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
            try
            {
                world.place(newAnimal);
                animalsList.add(newAnimal);
                numOfAnimalsOnMap++;
            }
            catch (IncorrectPositionException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void run()
    {
        for(int i = 0; i < moveDirectionsList.size(); i++)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Simulation interrupted");
                Thread.currentThread().interrupt();
            }
            int animalIndex = i % animalsList.size();
            world.move(animalsList.get(animalIndex), moveDirectionsList.get(i));
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
