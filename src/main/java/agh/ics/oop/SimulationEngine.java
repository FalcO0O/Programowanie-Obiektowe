package agh.ics.oop;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final ArrayList<Simulation> simulationsList = new ArrayList<>();
    private final ArrayList<Thread> threadsList = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public SimulationEngine(ArrayList<Simulation> simulations) {
        this.simulationsList.addAll(simulations);
    }

    public void runSync()
    {
        for (Simulation simulation : simulationsList) {
            simulation.run();
        }
    }

    public void runAsync()
    {
        for(Simulation simulation : simulationsList)
        {
            Thread thread = new Thread(simulation);
            thread.start();
            threadsList.add(thread);
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException
    {
        for(Thread thread : threadsList)
        {
            thread.join();
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            executor.shutdownNow();
        }

    }

    public void runAsyncInThreadPool() {
        for (Simulation simulation : simulationsList) {
            executor.submit(simulation);
        }
    }


}
