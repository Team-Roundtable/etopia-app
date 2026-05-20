package ch.fhnw.roundtable.etopia.views.grid.model;

import ch.fhnw.roundtable.etopia.Model;
import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.time.Countdown;
import ch.fhnw.roundtable.etopia.input.ButtonType;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.grid.state.GridState;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;

import java.util.List;

public class GridModel implements Model<GridState> {

    private final Cursor cursor;
    private final List<Pipe> producers;
    private final StatusModel status;
    private final Countdown finishCountdown;

    public GridModel(Configuration configuration, StatusModel status) {
        this.cursor = new Cursor(configuration);
        this.producers = GridMap.getProducers(configuration);
        this.status = status;
        this.finishCountdown = new Countdown(configuration.grid().finishCountdown());
    }

    @Override
    public void update(float delta, Controls controls) {
        finishCountdown.update(delta);

        if (!finishCountdown.isStarted()) {
            controls.setButtonLightPlayable();
        } else {
            controls.setButtonLightNone();
            return;
        }

        status.update(delta, controls);
        cursor.update(delta, controls);

        if (controls.isButtonJustPressed(ButtonType.SELECT)) {
            producers.forEach(producer -> PipeInteractionService.rotate(producer, cursor));
        }

        for (Pipe producer : producers) {
            PowerPropagationService.shutdown(producer);
            PowerPropagationService.power(producer);
        }

        updateFinishCountdown();
    }

    @Override
    public GridState state() {
        return new GridState(
                producers.stream().flatMap(producer -> producer.state().stream()).toList(),
                cursor.state()
        );
    }

    @Override
    public Result result() {
        if (status.isFinished()) {
            return Result.FAIL_TIME;
        }

        if (finishCountdown.isFinished()) {
            return Result.SUCCESS;
        }

        return Result.RUNNING;
    }

    private void updateFinishCountdown() {
        var poweredProducers = producers.stream()
                .filter(PowerPropagationService::isConsumerPowered)
                .count();

        status.setEnergy((float) poweredProducers / producers.size());

        if (poweredProducers == producers.size()) {
            finishCountdown.start();
        }
    }
}
