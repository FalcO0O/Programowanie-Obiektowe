package agh.ics.oop.model.genotype;

import java.util.Arrays;
import java.util.List;

public enum MutationVariant {
    SMALL_CORRECTION("Small correction"),
    SWAP("Swap");

    private final String displayName;

    MutationVariant(String displayName) {
        this.displayName = displayName;
    }

    public static List<String> getAllDisplayNames() {
        return Arrays.stream(values()).map(variant -> variant.displayName).toList();
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MutationVariant fromDisplayName(String displayName) {
        var variant = Arrays.stream(values()).filter(var -> var.displayName.equals(displayName)).findFirst();
        if (variant.isPresent()) {
            return variant.get();
        } else {
            throw new IllegalArgumentException("MutationVariant with displayName %s doesn't exist".formatted(displayName));
        }
    }

}
