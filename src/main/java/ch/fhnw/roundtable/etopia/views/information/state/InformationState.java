package ch.fhnw.roundtable.etopia.views.information.state;

import ch.fhnw.roundtable.etopia.views.information.model.InformationType;

public record InformationState(
        InformationTextState title,
        InformationTextState description,
        InformationTextState note,
        InformationVideoState video,
        boolean disabled,
        InformationType type
) {
}
