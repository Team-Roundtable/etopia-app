package ch.fhnw.roundtable.etopia.views.biogas.model;

import ch.fhnw.roundtable.etopia.Model;
import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.time.Timer;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasState;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BiogasModel implements Model<BiogasState> {

    private final Configuration configuration;
    private final Random random;
    private final Cursor cursor;
    private final List<Trash> trashes;
    private final Timer shiftTimer;
    private final StatusModel status;

    private final List<Trash> deliveredTrashes = new ArrayList<>();
    private final List<Trash> grabbedTrashes = new ArrayList<>();

    public BiogasModel(Configuration configuration, Random random, StatusModel status) {
        this(configuration, random, status, new Cursor(configuration), new Timer(configuration.biogas().shiftTimer()));
    }

    BiogasModel(Configuration configuration, Random random, StatusModel status, Cursor cursor, Timer timer) {
        this.configuration = configuration;
        this.random = random;
        this.cursor = cursor;
        this.trashes = new ArrayList<>();
        this.shiftTimer = timer;
        this.status = status;
    }

    @Override
    public void update(float delta, Controls controls) {
        controls.setButtonLightPlayable();
        status.update(delta, controls);

        if (shiftTimer.triggered(delta)) {
            shiftTrash();
            addTrash();
        }

        droppedTrash();

        cursor.update(delta, controls);

        if (controls.isButtonJustPressed(ButtonType.SELECT)) {
            removeTrash();
        }
    }

    /**
     * Is not idempotent. Returns delivered and grabbed trashes only once.
     * @return The current state of BiogasModel
     */
    @Override
    public BiogasState state() {
        var state = new BiogasState(
                trashes.stream().map(Trash::state).toList(),
                cursor.state(),
                deliveredTrashes.stream().map(Trash::state).toList(),
                grabbedTrashes.stream().map(Trash::state).toList()
        );
        deliveredTrashes.clear();
        grabbedTrashes.clear();
        return state;
    }

    @Override
    public Result result() {
        if (status.isDead()) {
            return Result.FAIL_HEALTH;
        }

        if (status.isFinished()) {
            return Result.FAIL_TIME;
        }

        if (status.isPowered()) {
            return Result.SUCCESS;
        }

        return Result.RUNNING;
    }

    private void shiftTrash() {
        for (var trash : trashes) {
            trash.shift();
        }
    }

    private void addTrash() {
        var x = configuration.biogas().mapWidth();
        var y = random.nextInt(configuration.biogas().mapHeight());
        var biodegradable = TrashType.createRandom(random);
        var trash = new Trash(configuration, x, y, biodegradable);

        trashes.add(trash);
    }

    private void droppedTrash() {
        var iterator = trashes.iterator();

        while (iterator.hasNext()) {
            var trash = iterator.next();

            if (trash.isDropped()) {
                if (trash.isBiodegradable()) {
                    status.addEnergy(0.05f);
                } else {
                    status.removeHealth();
                }

                deliveredTrashes.add(trash);
                iterator.remove();
            }
        }
    }

    private void removeTrash() {
        var grabbed = trashes.stream().filter(trash -> trash.intersects(cursor)).toList();

        trashes.removeAll(grabbed);

        grabbedTrashes.addAll(grabbed);
    }

    List<Trash> getTrashes() {
        return trashes;
    }
}
