package ch.fhnw.roundtable.etopia.views.commons.panel;

import java.util.StringJoiner;

public record PanelDetails(
        String title,
        String description,
        String select
) {

    @Override
    public String toString() {
        return new StringJoiner("\n").add(title).add(description).add(select).toString();
    }
}
