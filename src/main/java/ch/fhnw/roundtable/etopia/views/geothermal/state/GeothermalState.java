package ch.fhnw.roundtable.etopia.views.geothermal.state;

import java.util.List;

public record GeothermalState(
        float cameraPositionX,
        float cameraPositionY,
        float mapWidth,
        float mapHeight,
        List<GeothermalRockState> rocks,
        List<GeothermalPipeState> pipes,
        GeothermalDrillState drill,
        boolean justGotHurt
) {
}
