package ch.fhnw.roundtable.etopia.views.biogas.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasTrashState;

import java.util.UUID;

public class Trash extends Item implements Renderable<BiogasTrashState> {

    private final TrashType type;
    private final UUID id;

    public Trash(Configuration configuration, int x, int y, TrashType type) {
        super(configuration, x, y);
        this.type = type;
        this.id = UUID.randomUUID();
    }

    @Override
    public BiogasTrashState state() {
        return new BiogasTrashState(
                getX(),
                getY(),
                side,
                side,
                type,
                id
        );
    }

    public void shift() {
        x--;
    }

    public boolean isBiodegradable() {
        return type.isBiodegradable();
    }

    public boolean isDropped() {
        return x < 0;
    }
}
