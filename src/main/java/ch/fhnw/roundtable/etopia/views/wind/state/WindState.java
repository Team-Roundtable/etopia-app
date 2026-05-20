package ch.fhnw.roundtable.etopia.views.wind.state;

import java.util.List;

public record WindState(
        WindTurbineState turbine,
        List<WindGustState> gusts
) {
}
