package ch.fhnw.roundtable.etopia.views.grid;

import ch.fhnw.roundtable.etopia.views.Asset;

public enum GridAsset implements Asset {
    BACKGROUND("assets/grid/grid-mapScaled.png"),
    PIPE("assets/grid/grid-pipe.png"),
    PIPE_ACTIVE("assets/grid/grid-pipe-active.png"),
    PIPE_INACTIVE("assets/grid/grid-pipe-inactive.png"),
    PIPE90("assets/grid/grid-pipe90.png"),
    PIPE90_ACTIVE("assets/grid/grid-pipe90-active.png"),
    PIPE90_INACTIVE("assets/grid/grid-pipe90-inactive.png"),
    TPIPE_ACTIVE("assets/grid/grid-T_pipe-active.png"),
    TPIPE_INACTIVE("assets/grid/grid-T_pipe-inactive.png"),
    TPIPE("assets/grid/grid-T_pipe.png"),
    WIND_SOURCE("assets/grid/wind.png"),
    BIO_SOURCE("assets/map/biomass.png"),
    SOLAR_SOURCE("assets/grid/solar.png"),
    CITY("assets/grid/grid-city.png");
    private final String path;

    GridAsset(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
