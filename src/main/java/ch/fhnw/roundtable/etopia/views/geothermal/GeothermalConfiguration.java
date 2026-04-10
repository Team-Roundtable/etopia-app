package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.helpers.Size;

public class GeothermalConfiguration {

    public final Size mapSize = new Size(960, 4320, 2);

    public final Size drillSize = new Size(64, 64, 4);
    public final Size pipeSegmentSize = new Size(16, 16, 4);
    public final Size rockSize = new Size(64, 64, 4);

    public final float drillSpeed = 400;
    public final int numberOfRocks = 40;
}
