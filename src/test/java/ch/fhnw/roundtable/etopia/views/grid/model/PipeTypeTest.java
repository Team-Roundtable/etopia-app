package ch.fhnw.roundtable.etopia.views.grid.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipeTypeTest {

    @Test
    void shouldRecognizeEditableTypes() {
        assertTrue(PipeType.STRAIGHT_EDITABLE.isEditable());
        assertTrue(PipeType.CORNER_EDITABLE.isEditable());
        assertTrue(PipeType.THREE_WAY_EDITABLE.isEditable());
    }

    @Test
    void shouldRecognizeImmutableTypes() {
        assertFalse(PipeType.PRODUCER.isEditable());
        assertFalse(PipeType.CONSUMER.isEditable());
        assertFalse(PipeType.STRAIGHT_IMMUTABLE.isEditable());
        assertFalse(PipeType.CROSS_IMMUTABLE.isEditable());
        assertFalse(PipeType.CORNER_IMMUTABLE.isEditable());
        assertFalse(PipeType.THREE_WAY_IMMUTABLE.isEditable());
    }
}
