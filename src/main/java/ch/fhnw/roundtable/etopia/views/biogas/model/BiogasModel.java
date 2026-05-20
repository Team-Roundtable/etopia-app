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

    public BiogasModel(Configuration configuration, Random random, StatusModel status) {
        this.configuration = configuration;
        this.random = random;
        this.cursor = new Cursor(configuration);
        this.trashes = new ArrayList<>();
        this.shiftTimer = new Timer(configuration.biogas().shiftTimer());
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

    @Override
    public BiogasState state() {
        return new BiogasState(
                trashes.stream().map(Trash::state).toList(),
                cursor.state()
        );
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

                iterator.remove();
            }
        }
    }

    private void removeTrash() {
        trashes.removeIf(trash -> trash.intersects(cursor));
    }
}
