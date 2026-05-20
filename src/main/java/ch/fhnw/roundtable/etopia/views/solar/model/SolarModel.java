package ch.fhnw.roundtable.etopia.views.solar.model;

import ch.fhnw.roundtable.etopia.Model;
import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.time.Timer;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Angles;
import ch.fhnw.roundtable.etopia.views.solar.state.SolarState;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;

public class SolarModel implements Model<SolarState> {

    private final StatusModel status;
    private final Sun sun;
    private final Panel panel;
    private final Timer energyInterval;

    private float efficiency;

    public SolarModel(Configuration configuration, StatusModel status) {
        this.status = status;
        this.sun = new Sun(configuration);
        this.panel = new Panel(configuration);
        this.energyInterval = new Timer(configuration.solar().energyInterval());
    }

    @Override
    public void update(float delta, Controls controls) {
        controls.setButtonLightHorizontal();

        status.update(delta, controls);
        sun.update(delta, controls);
        panel.update(delta, controls);

        if (energyInterval.triggered(delta)) {
            updateEfficiency();
            status.addEnergy(0.05f * efficiency);
        }
    }

    @Override
    public SolarState state() {
        return new SolarState(
                sun.state(),
                panel.state(),
                efficiency * 100
        );
    }

    @Override
    public Result result() {
        if (status.isFinished()) {
            return Result.FAIL_TIME;
        }

        if (status.isPowered()) {
            return Result.SUCCESS;
        }

        return Result.RUNNING;
    }

    private void updateEfficiency() {
        float target = Angles.angle(panel.getCenter(), sun.getCenter()) - 90f;
        float difference = Angles.difference(panel.getRotation(), target);

        float normalized = 1f - (difference / 180f);
        normalized = Math.max(0, normalized);

        efficiency = (float) Math.pow(normalized, 5);
    }
}
