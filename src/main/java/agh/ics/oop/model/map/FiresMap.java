package agh.ics.oop.model.map;

import agh.ics.oop.model.element.Fire;
import agh.ics.oop.model.element.WorldElement;
import agh.ics.oop.model.util.Vector2d;

import java.util.*;

public class FiresMap extends AbstractWorldMap {

    protected final int fireInterval;
    protected final int fireDuration;
    protected final Map<Vector2d, Integer> plantsOnFire = new HashMap<>();

    public FiresMap(int width, int height, int initialPlantsNumber,
                    int dailyNewPlantsNumber, int energyOnConsumption, int fireInterval, int direDuration) {
        super(width, height, initialPlantsNumber, dailyNewPlantsNumber, energyOnConsumption);
        this.fireInterval = fireInterval;
        this.fireDuration = direDuration;
    }

    @Override
    public void runSpecialEvents() {
        var plantList = plants.values().stream().toList();
        if (this.getDay() % fireInterval == 0 && !plantList.isEmpty()) {
            var pickedPlant = plantList.get(rand.nextInt(plantList.size()));
            if (!plantsOnFire.containsKey(pickedPlant.getPosition())) {
                plantsOnFire.put(pickedPlant.getPosition(), 0);
            }
        }
        var plantsOnFireSet = new HashSet<Vector2d>();
        var positions = new LinkedHashSet<>(plantsOnFire.keySet());
        positions.forEach(position -> {
            int daysOnFire = plantsOnFire.get(position);
            plantsOnFireSet.addAll(getAdjacentPlants(position));
            if(daysOnFire >= fireDuration) {
                plants.remove(position);
                plantsOnFire.remove(position);
            } else {
                plantsOnFire.put(position, daysOnFire+1);
            }
        });
        plantsOnFireSet.forEach(position -> {
            if (!plantsOnFire.containsKey(position)) {
                plantsOnFire.put(position, 0);
            }
        });

    }

    @Override
    public void handlePlantConsumption() {
        for (var group : getAnimalGroups()) {
            var position = group.getPosition();
            if(plantsOnFire.containsKey(position))
            {
                // odejmujemy całą energię przez co zwierzak zostanie usunięty w następnej turze, tak aby było widać ze wszedł w pożar
                group.getAnimals().forEach(animal -> {
                    animal.subtractEnergy(animal.getEnergy());
                });
            }
            else {
                var firstAnimal = group.getAnimals().getFirst();
                if (plants.containsKey(position)) {
                    firstAnimal.consumePlant(plants.get(position));
                    plants.remove(position);
                }
            }

        }
    }

    @Override
    public WorldElement primaryObjectAt(Vector2d position) {
        if (plantsOnFire.containsKey(position)) {
            return new Fire(position);
        }
        return super.primaryObjectAt(position);
    }

    protected HashSet<Vector2d> getAdjacentPlants(Vector2d plantPosition)
    {
        var nearbyPositions = new ArrayList<>(List.of(
            plantPosition.add(MapDirection.NORTH.toUnitVector()),
            plantPosition.add(MapDirection.EAST.toUnitVector()),
            plantPosition.add(MapDirection.WEST.toUnitVector()),
            plantPosition.add(MapDirection.SOUTH.toUnitVector())
        ));
        var plantsSet = new HashSet<Vector2d>();
        nearbyPositions.forEach(position -> {
            if(plants.containsKey(position)) {
                plantsSet.add(position);
            }
            if(position.getX() == 0) { // pozar moze sie przeniesc na drugą stronę mapy (tak jak zwierzeta)
                var otherSidePosition = new Vector2d(getWidth() - 1, position.getY());
                if(plants.containsKey(otherSidePosition)) {
                    plantsSet.add(otherSidePosition);
                }
            } else if (position.getX() == getWidth() - 1) { // pozar moze sie przeniesc na drugą stronę mapy (tak jak zwierzeta)
                var otherSidePosition = new Vector2d(0, position.getY());
                if(plants.containsKey(otherSidePosition)) {
                    plantsSet.add(otherSidePosition);
                }
            }
        });
        return plantsSet;
    }

}
