package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animalsList;
    private final List<MoveDirection> moveDirectionsList;
    private final WorldMap world;

    public Simulation(List<Vector2d> vectorsList, List<MoveDirection> moveDirectionsList, WorldMap world) {
        this.animalsList = new ArrayList<>(); // lepiej użyć ArrayList, ponieważ zwierzaki ustawiamy tylko raz, a potem wielokrotnie je wybieramy z listy
        this.moveDirectionsList = moveDirectionsList;
        this.world = world;

        for (Vector2d vector : vectorsList)
        {
            world.place(new Animal(vector.getX(), vector.getY()));
            animalsList.add(new Animal(vector.getX(), vector.getY()));
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

}
