package ch.fhnw.roundtable.etopia.views.biogas.state;

import ch.fhnw.roundtable.etopia.views.biogas.model.TrashType;

import java.util.UUID;

public record BiogasTrashState(
        float x,
        float y,
        float width,
        float height,
        TrashType type,
        UUID id) {
}
