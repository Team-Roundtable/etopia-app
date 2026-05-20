package ch.fhnw.roundtable.etopia.views.solar.state;

public record SolarState(
        SolarSunState sun,
        SolarPanelState panel,
        float efficiency
) {
}
