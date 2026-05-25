package ch.fhnw.roundtable.etopia.views.biogas.model;

import ch.fhnw.roundtable.etopia.configuration.Biogas;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemTest {

    private Configuration configuration;
    private Biogas biogas;

    private static class TestItem extends Item {
        TestItem(Configuration configuration, int x, int y) {
            super(configuration, x, y);
        }
    }

    @BeforeEach
    void setUp() {
        configuration = mock(Configuration.class);
        biogas = mock(Biogas.class);

        when(configuration.biogas()).thenReturn(biogas);
        when(biogas.itemSide()).thenReturn(10f);
        when(biogas.conveyorOffsetX()).thenReturn(100f);
        when(biogas.conveyorOffsetY()).thenReturn(50f);
    }

    @Test
    void intersectsWhenSameGridPositionReturnsTrue() {
        Item a = new TestItem(configuration, 2, 3);
        Item b = new TestItem(configuration, 2, 3);

        assertTrue(a.intersects(b));
    }

    @Test
    void intersectsWhenDifferentXReturnsFalse() {
        Item a = new TestItem(configuration, 1, 3);
        Item b = new TestItem(configuration, 2, 3);

        assertFalse(a.intersects(b));
    }

    @Test
    void intersectsWhenDifferentYReturnsFalse() {
        Item a = new TestItem(configuration, 2, 1);
        Item b = new TestItem(configuration, 2, 2);

        assertFalse(a.intersects(b));
    }

    @Test
    void localCoordinatesReturnRawGridValues() {
        Item item = new TestItem(configuration, 7, 9);

        assertEquals(7, item.localX());
        assertEquals(9, item.localY());
    }

    @Test
    void getXAppliesOffsetAndScale() {
        Item item = new TestItem(configuration, 2, 3);

        // conveyorOffsetX + x * side = 100 + 2 * 10 = 120
        assertEquals(120f, item.getX());
    }

    @Test
    void getYAppliesOffsetAndScale() {
        Item item = new TestItem(configuration, 2, 3);

        // conveyorOffsetY + y * side = 50 + 3 * 10 = 80
        assertEquals(80f, item.getY());
    }
}
