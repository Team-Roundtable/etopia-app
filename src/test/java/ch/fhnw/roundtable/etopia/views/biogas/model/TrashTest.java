package ch.fhnw.roundtable.etopia.views.biogas.model;

import ch.fhnw.roundtable.etopia.configuration.Biogas;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasTrashState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrashTest {

    private Configuration configuration;
    private Biogas biogas;
    private TrashType type;

    @BeforeEach
    void setUp() {
        configuration = mock(Configuration.class);
        biogas = mock(Biogas.class);
        type = mock(TrashType.class);

        when(configuration.biogas()).thenReturn(biogas);
        when(biogas.itemSide()).thenReturn(10f);
        when(type.isBiodegradable()).thenReturn(true);
    }

    @Test
    void shiftDecrementsX() {
        Trash trash = new Trash(configuration, 5, 2, type);

        trash.shift();

        assertEquals(4, trash.x); // grid coordinate
    }

    @Test
    void isDroppedWhenXBelowZeroReturnsTrue() {
        Trash trash = new Trash(configuration, 0, 2, type);

        trash.shift(); // becomes -1

        assertTrue(trash.isDropped());
    }

    @Test
    void isDroppedWhenXZeroOrMoreReturnsFalse() {
        Trash trash = new Trash(configuration, 0, 2, type);

        assertFalse(trash.isDropped());
    }

    @Test
    void isBiodegradable() {
        Trash trash = new Trash(configuration, 1, 1, type);

        when(type.isBiodegradable()).thenReturn(true);
        assertTrue(trash.isBiodegradable());

        when(type.isBiodegradable()).thenReturn(false);
        assertFalse(trash.isBiodegradable());
    }

    @Test
    void stateContainsRenderCoordinates() {
        when(biogas.itemSide()).thenReturn(10f);
        when(biogas.conveyorOffsetX()).thenReturn(100f);
        when(biogas.conveyorOffsetY()).thenReturn(50f);

        Trash trash = new Trash(configuration, 2, 3, type);

        BiogasTrashState state = trash.state();

        assertEquals(120f, state.x()); // 100 + 2*10
        assertEquals(80f, state.y());  // 50 + 3*10
        assertEquals(type, state.type());
    }

    @Test
    void inheritsItemCoordinatesCorrectly() {
        Trash trash = new Trash(configuration, 7, 8, type);

        assertEquals(7, trash.x);
        assertEquals(8, trash.y);
    }
}
