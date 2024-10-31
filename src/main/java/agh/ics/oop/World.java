package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

public class World {

    public static void main(String[] args) {
        //System.out.println("System wystartował");
        //System.out.print("argumenty -> ");
        //printSeq(args);

        System.out.println("Zwierzak wystartował");
        run(OptionsParser.parseMoveDirections(args));
        System.out.println("Zwierzak się zatrzymał");

        //System.out.println("System zakończył działanie");
    }

    static void printSeq(String[] args)
    {
        System.out.print(args[0]);
        for (int i = 1; i < args.length; i++) { // w poleceniu jest napisane że muszą występować conajmniej 2, więc zostawiam bez sprawdzania czy istnieje index w tablicy
            System.out.print("," + args[i]);
        }
        System.out.println();
    }


    private static void run(MoveDirection[] args)
    {
        for (MoveDirection dir : args)
        {
            switch (dir)
            {
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                default -> {}
            }
        }

    }

}
