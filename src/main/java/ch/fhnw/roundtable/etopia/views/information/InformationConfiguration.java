package ch.fhnw.roundtable.etopia.views.information;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.shapes.Vector;

public class InformationConfiguration {

    public final float maxWidth = ETopia.WORLD_WIDTH - 512;

    public final Vector titleOffset = new Vector(256, ETopia.WORLD_HEIGHT - 256);
    public final Vector descriptionOffset = new Vector(256, ETopia.WORLD_HEIGHT - 384);
    public final Vector noteOffset = new Vector(256, 256);
}
