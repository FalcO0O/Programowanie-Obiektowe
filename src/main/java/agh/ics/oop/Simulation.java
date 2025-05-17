package agh.ics.oop;

import agh.ics.oop.model.AnimalFactory;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.model.map.exceptions.IncorrectPositionException;
import agh.ics.oop.model.util.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation implements Runnable, SimulationControlsListener {
    private final List<Animal> animalsList;
    private final WorldMap worldMap;
    private final AnimalFactory animalFactory;
    private final boolean saveStatistics;
    private final CSVHandler statisticsWriter;
    private boolean isStopped = false;
    private int tickRate = 500;
    private boolean isEnded = false;

    public Simulation(int initialAnimalsNumber, WorldMap worldMap, AnimalFactory animalFactory, boolean saveStatistics) {
        animalsList = new ArrayList<>();
        this.worldMap = worldMap;
        this.animalFactory = animalFactory;
        var rand = new Random();
        for (int i = 0; i < initialAnimalsNumber; i++) {
            var animal = animalFactory.createAnimal(
                new Vector2d(rand.nextInt(worldMap.getWidth()),  rand.nextInt(worldMap.getHeight())), 0);
            animalsList.add(animal);
        }
        placeAnimals(animalsList);
        this.saveStatistics = saveStatistics;
        statisticsWriter = new CSVHandler();
    }

    public void run()
    {
        int day = 1;
        while (true) {
            if (isEnded) {
                break;
            }
            if (!isStopped) {
                worldMap.setDay(day);
                removeDeadAnimals(day);
                moveAnimals();
                worldMap.handlePlantConsumption();
                var animalGroups = worldMap.getAnimalGroups();
                var newAnimals = animalFactory.handleReproduction(animalGroups, day);
                animalsList.addAll(newAnimals);
                placeAnimals(newAnimals);
                worldMap.createNewPlants();
                worldMap.updateStatistics();
                worldMap.mapUpdated("Liczba wyświetleń: " + day);
                worldMap.runSpecialEvents();
                if (saveStatistics) {
                    statisticsWriter.writeStatistics(worldMap);
                }
                day++;
                if (animalsList.isEmpty()) break;
            }
            try {
                Thread.sleep(tickRate);
            } catch (InterruptedException e) {
                System.out.println("Simulation interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    private void placeAnimals(List<Animal> animals) {
        for (var animal : animals) {
            try {
                worldMap.place(animal);
            }
            catch (IncorrectPositionException e) {
                System.out.printf("Failed to place animal. %s\n".formatted(e.getMessage()));
            }
        }
    }

    private void removeDeadAnimals(int day) {
        var iterator = animalsList.iterator();
        while (iterator.hasNext()) {
            var animal = iterator.next();
            if (animal.getEnergy() <= 0) {
                animal.die(day);
                iterator.remove();
                worldMap.remove(animal);
            }
        }
    }

    private void moveAnimals() {
        for (var animal : animalsList) {
            worldMap.move(animal);
        }
    }

    @Override
    public void stop() {
        isStopped = true;
    }

    @Override
    public void resume() {
        isStopped = false;
    }

    @Override
    public void setTickRate(int tickRate) {
        this.tickRate = tickRate;
    }

    public void end() {
        isEnded = true;
    }
}
