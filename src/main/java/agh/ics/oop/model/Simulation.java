package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animalsList;
    private final List<MoveDirection> moveDirectionsList;

    public Simulation(List<Vector2d> vectorsList, List<MoveDirection> moveDirectionsList) {
        this.animalsList = new ArrayList<>(); // lepiej użyć ArrayList, ponieważ zwierzaki ustawiamy tylko raz, a potem wielokrotnie je wybieramy z listy
        this.moveDirectionsList = moveDirectionsList;

        for (Vector2d vector : vectorsList)
        {
            animalsList.add(new Animal(vector.getX(), vector.getY()));
        }
    }

    public void run()
    {
        for(int i = 0; i < moveDirectionsList.size(); i++)
        {
            int animalIndex = i % animalsList.size();
            animalsList.get(animalIndex).move(moveDirectionsList.get(i));
            System.out.println("Zwierzę " + animalIndex + " : " + animalsList.get(animalIndex).toString());
        }
    }

}
