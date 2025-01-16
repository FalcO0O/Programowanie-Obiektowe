package agh.ics.oop;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class FileMapDisplay implements MapChangeListener {
    private final String filePath = "map_%s.log".formatted(UUID.randomUUID());

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        try(FileWriter fileWriter = new FileWriter(filePath))
        {
            fileWriter.append("%s\n%s\n".formatted(message, worldMap));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
