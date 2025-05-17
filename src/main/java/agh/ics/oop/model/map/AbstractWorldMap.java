package agh.ics.oop.model.map;

import agh.ics.oop.model.WorldMapStatistics;
import agh.ics.oop.model.WorldMapStatisticsProvider;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.Plant;
import agh.ics.oop.model.element.SortedAnimalGroup;
import agh.ics.oop.model.element.WorldElement;
import agh.ics.oop.model.map.exceptions.IncorrectPositionException;
import agh.ics.oop.model.position.EquatorPositionsGenerator;
import agh.ics.oop.model.position.PositionsGeneratorWithPriority;
import agh.ics.oop.model.position.PriorityRandomPositionIterable;
import agh.ics.oop.model.util.Vector2d;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {

    protected final int dailyNewPlantsNumber;
    protected final WorldMapStatistics statistics;
    protected int day;
    protected final Random rand = new Random();
    protected final int energyOnConsumption;
    protected final Map<Vector2d, Plant> plants = new HashMap<>();

    protected final Map<Vector2d, List<Animal>> animals = new ConcurrentHashMap<>();
    private final List<MapChangeListener> observers = new ArrayList<>();
    private final UUID uuid;
    private final Set<Vector2d> animalOccupiedPositions = new LinkedHashSet<>();
    private final Vector2d upperRight, lowerLeft;

    public AbstractWorldMap(int width, int height, int initialPlantsNumber, int dailyNewPlantsNumber, int energyOnConsumption) {
        super();
        this.uuid = UUID.randomUUID();
        upperRight = new Vector2d(width-1, height-1);
        lowerLeft = new Vector2d(0,0);
        this.energyOnConsumption = energyOnConsumption;
        this.dailyNewPlantsNumber = dailyNewPlantsNumber;
        PositionsGeneratorWithPriority positionGenerator = new EquatorPositionsGenerator(width, height);
        var positionsIterable = new PriorityRandomPositionIterable(
            positionGenerator.getPriorityPositions(),
            positionGenerator.getRegularPositions(),
            initialPlantsNumber);
        for (var position : positionsIterable) {
            plants.put(position, new Plant(position, energyOnConsumption));
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                animals.put(new Vector2d(i, j), new ArrayList<>());
            }
        }
        statistics = new WorldMapStatistics(width, height);
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if (!isOutOfBounds(animal.getPosition())) {
            var list = animals.get(animal.getPosition());
            list.add(animal);
            animalOccupiedPositions.add(animal.getPosition());
            statistics.registerAnimalBirth(animal);
        }
        else {
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    @Override
    public void remove(Animal animal) {
        var list = animals.get(animal.getPosition());
        list.remove(animal);
        if (list.isEmpty()) {
            animalOccupiedPositions.remove(animal.getPosition());
        }
        statistics.registerAnimalDeath(animal, day);
    }

    @Override
    public void move(Animal animal) {
        Vector2d prevPosition = animal.getPosition();
        animal.move(this);
        if(animal.getPosition().equals(prevPosition)) {
            return;
        }
        var prevList = animals.get(prevPosition);
        prevList.remove(animal);
        if (prevList.isEmpty()) {
            animalOccupiedPositions.remove(prevPosition);
        }
        var currentList = animals.get(animal.getPosition());
        currentList.add(animal);
        animalOccupiedPositions.add(animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return primaryObjectAt(position) != null;
    }

    @Override
    public WorldElement primaryObjectAt(Vector2d position) {
        var animalList = animals.get(position);
        if (animalList.size() > 1) {
            return new SortedAnimalGroup(animalList);
        }
        if (animalList.size() == 1) {
            return animalList.getFirst();
        }
        if (plants.containsKey(position)) {
            return plants.get(position);
        }
        return null;
    }

    @Override
    public UUID getID()
    {
        return uuid;
    }

    @Override
    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    protected boolean isOutOfBounds(Vector2d position) {
        return isOutOfHorizontalBounds(position) || isOutOfVerticalBounds(position);
    }

    @Override
    public boolean isOutOfHorizontalBounds(Vector2d position) {
        return position.getX() < lowerLeft.getX() || position.getX() > upperRight.getX();
    }

    @Override
    public boolean isOutOfVerticalBounds(Vector2d position) {
        return position.getY() < lowerLeft.getY() || position.getY() > upperRight.getY();
    }

    @Override
    public Vector2d getCorrectedHorizontalPosition(Vector2d position) {
        if (position.getX() == lowerLeft.getX() - 1) {
            return new Vector2d(upperRight.getX(), position.getY());
        }
        if (position.getX() == upperRight.getX() + 1) {
            return new Vector2d(lowerLeft.getX(), position.getY());
        }
        return position;
    }

    @Override
    public List<SortedAnimalGroup> getAnimalGroups() {
        List<SortedAnimalGroup> groups = new ArrayList<>();
        for (var position : animalOccupiedPositions) {
            groups.add(new SortedAnimalGroup(animals.get(position)));
        }
        return groups;
    }



    @Override
    public void createNewPlants() {
        PositionsGeneratorWithPriority positionsGenerator = new EquatorPositionsGenerator(getWidth(), getHeight(), plants.keySet());
        var positionsIterable = new PriorityRandomPositionIterable(
            positionsGenerator.getPriorityPositions(),
            positionsGenerator.getRegularPositions(),
            dailyNewPlantsNumber);
        for (var position : positionsIterable) {
            plants.put(position, new Plant(position, energyOnConsumption));
        }
    }

    @Override
    public List<Vector2d> getPriorityPlantPositions() {
        PositionsGeneratorWithPriority positioGenerator = new EquatorPositionsGenerator(getWidth(), getHeight());
        return positioGenerator.getPriorityPositions();
    }

    @Override
    public int getWidth() {
        return upperRight.getX() + 1;
    }

    @Override
    public int getHeight() {
        return upperRight.getY() + 1;
    }

    @Override
    public void updateStatistics() {
        statistics.updateMapStatistics(day, animals, plants, animalOccupiedPositions);
    }

    @Override
    public WorldMapStatisticsProvider getStatistics() {
        return statistics;
    }

    @Override
    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public void mapUpdated(String message) {
        mapChanged(message);
    }

    @Override
    public void runSpecialEvents() {

    }

    @Override
    public void handlePlantConsumption() {
        for (var group : getAnimalGroups()) {
            var position = group.getPosition();
            var firstAnimal = group.getAnimals().getFirst();
            if (plants.containsKey(position)) {
                firstAnimal.consumePlant(plants.get(position));
                plants.remove(position);
            }
        }
    }

    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }

    @Override
    public boolean positionContainsElement(WorldElement element, Vector2d position) {
        return (animals.containsKey(position) && animals.get(position).contains(element)) ||
                (plants.containsKey(position) && plants.get(position).equals(element));
    }

    @Override
    public List<Animal> getAnimalsWithDominantGenotype() {
        if (statistics.getGenotypeRanking().isEmpty()) {
            return new ArrayList<>();
        }
        var genotype = statistics.getGenotypeRanking().getFirst().genotype();

        return animals.values().stream().flatMap(Collection::stream).filter(animal ->
            animal.getGenome()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining())
                .equals(genotype)).toList();
    }

}
