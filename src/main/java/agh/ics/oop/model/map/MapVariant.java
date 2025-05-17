package agh.ics.oop.model.map;

import java.util.Arrays;
import java.util.List;

public enum MapVariant {
    GLOBE("Globe"),
    FIRES("Fires");

    private final String displayName;

    MapVariant(String displayName) {
        this.displayName = displayName;
    }

    public static List<String> getAllDisplayNames() {
        return Arrays.stream(values()).map(variant -> variant.displayName).toList();
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MapVariant fromDisplayName(String displayName) {
        var variant = Arrays.stream(values()).filter(var -> var.displayName.equals(displayName)).findFirst();
        if (variant.isPresent()) {
            return variant.get();
        } else {
            throw new IllegalArgumentException("MapVariant with displayName %s doesn't exist".formatted(displayName));
        }
    }

}
