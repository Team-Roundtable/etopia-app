package ch.fhnw.roundtable.etopia.views.biogas.model;

import java.util.Random;

public enum TrashType {
    APPLE(true),
    BANANA(true),
    GRAPES(true),
    BOTTLE(false),
    CAN(false),
    COLA(false),
    CUP(false),
    GLASS(false);

    private final boolean biodegradable;

    TrashType(boolean biodegradable) {
        this.biodegradable = biodegradable;
    }

    public static TrashType createRandom(Random random) {
        var types = values();

        return types[random.nextInt(types.length)];
    }

    public boolean isBiodegradable() {
        return biodegradable;
    }
}
