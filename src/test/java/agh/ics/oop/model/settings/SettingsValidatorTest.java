package agh.ics.oop.model.settings;

import agh.ics.oop.model.genotype.MutationVariant;
import agh.ics.oop.model.map.MapVariant;
import agh.ics.oop.model.settings.exceptions.InvalidSettingsException;
import javafx.application.Platform;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SettingsValidatorTest {

    @BeforeAll
    static void initJfxRuntime() {
        try {
            Platform.startup(() -> {});
        } catch (Exception e) {
            System.out.println("Platforma już chodzi");
        }
    }

    @Test
    public void testValidSettings() {
        var validator = new SettingsValidator();
        var settings = new Settings(
                50, 50,
                MapVariant.GLOBE,
                0,
                0,
                100,
                5,
                10,
                10,
                100,
                50,
                20,
                1,
                3,
                MutationVariant.SMALL_CORRECTION,
                8,
                true
        );
        assertDoesNotThrow(() -> validator.validate(settings));
    }

    @Test
    public void testInvalidNegativeValue() {
        var validator = new SettingsValidator();
        var settings = new Settings(
                50,
                50,
                MapVariant.GLOBE,
                0,
                0,
                100,
                5,
                -3,
                10,
                100,
                50,
                20,
                1,
                3,
                MutationVariant.SMALL_CORRECTION,
                8,
                true
        );
        assertThrows(InvalidSettingsException.class, () -> validator.validate(settings));
    }

    @Test
    public void testInvalidNonPositiveValue() {
        var validator = new SettingsValidator();
        var settings = new Settings(
                50,
                50,
                MapVariant.GLOBE,
                0,
                0,
                100,
                5,
                1,
                10,
                100,
                50,
                0,
                1,
                3,
                MutationVariant.SMALL_CORRECTION,
                8,
                true
        );
        assertThrows(InvalidSettingsException.class, () -> validator.validate(settings));
    }

    @Test
    public void testTooManyPlants() {
        var validator = new SettingsValidator();
        var settings = new Settings(
                10,
                10,
                MapVariant.GLOBE,
                0,
                0,
                150,
                5,
                1,
                10,
                100,
                50,
                0,
                1,
                3,
                MutationVariant.SMALL_CORRECTION,
                8,
                true
        );
        assertThrows(InvalidSettingsException.class, () -> validator.validate(settings));
    }

    @Test
    public void testMinMutationsGreaterThanMaxMutations() {
        var validator = new SettingsValidator();
        var settings = new Settings(
                10,
                10,
                MapVariant.GLOBE,
                0,
                0,
                150,
                5,
                1,
                10,
                100,
                50,
                5,
                5,
                3,
                MutationVariant.SWAP,
                8,
                true
        );
        assertThrows(InvalidSettingsException.class, () -> validator.validate(settings));
    }

    @Test
    public void testInvalidFireSettings() {
        var validator = new SettingsValidator();
        var settings = new Settings(
                10,
                10,
                MapVariant.FIRES,
                0,
                4,
                150,
                5,
                1,
                10,
                100,
                50,
                5,
                5,
                3,
                MutationVariant.SMALL_CORRECTION,
                8,
                true
        );
        assertThrows(InvalidSettingsException.class, () -> validator.validate(settings));
    }

}
