package ch.fhnw.roundtable.etopia.views.information.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InformationTypeTest {

    @Test
    void shouldReturnLanguagePrefix() {
        assertEquals("information", InformationType.INFORMATION.getLanguagePrefix());
    }

    @Test
    void shouldReturnEscapableTrueForStartTypes() {
        assertTrue(InformationType.INFORMATION.isEscapable());
        assertTrue(InformationType.WIND_START.isEscapable());
        assertTrue(InformationType.BIOGAS_START.isEscapable());
    }

    @Test
    void shouldReturnEscapableFalseForResultTypes() {
        assertFalse(InformationType.BIOGAS_SUCCESS.isEscapable());
        assertFalse(InformationType.GEOTHERMAL_FAIL_TIME.isEscapable());
    }
}
