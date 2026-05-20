package ch.fhnw.roundtable.etopia.views.biogas.state;

import ch.fhnw.roundtable.etopia.views.biogas.model.TrashType;

public record BiogasTrashState(
        float x,
        float y,
        float width,
        float height,
        TrashType type
) {
}
