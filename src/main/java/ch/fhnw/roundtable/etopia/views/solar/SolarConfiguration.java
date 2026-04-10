package ch.fhnw.roundtable.etopia.views.solar;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.helpers.Size;
import ch.fhnw.roundtable.etopia.shapes.Vector;

public class SolarConfiguration {

    public final Vector worldCenter = new Vector(
            ETopia.WORLD_WIDTH / 2f,
            0);

    public final Vector panelCenter = new Vector(
            ETopia.WORLD_WIDTH / 2f,
            ETopia.WORLD_HEIGHT / 4f
    );

    public final float sunOrbitRadius = ETopia.WORLD_HEIGHT;
    public final float sunSpeed = 0.5f;

    public final Size sunSize = new Size(64, 64, 4);
    public final Size panelSize = new Size(64, 64, 4);

    public final float panelSpeed = 75f;
    public final float panelMaxRotation = 70f;
}
