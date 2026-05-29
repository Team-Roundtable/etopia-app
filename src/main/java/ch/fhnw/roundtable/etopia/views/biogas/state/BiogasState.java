package ch.fhnw.roundtable.etopia.views.biogas.state;

import java.util.List;

public record BiogasState(
        List<BiogasTrashState> trashes,
        BiogasCursorState cursor,
        List<BiogasTrashState> deliveredTrashes,
        List<BiogasTrashState> grabbedTrashes
) {
}
