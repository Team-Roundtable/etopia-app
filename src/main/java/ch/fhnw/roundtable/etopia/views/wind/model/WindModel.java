package ch.fhnw.roundtable.etopia.views.wind.model;

import ch.fhnw.roundtable.etopia.Model;
import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.time.Timer;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Intersections;
import ch.fhnw.roundtable.etopia.shapes.Rectangle;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import ch.fhnw.roundtable.etopia.views.wind.state.WindState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WindModel implements Model<WindState> {

    private final Random random;
    private final Configuration configuration;
    private final Turbine turbine;
    private final List<Gust> gusts = new ArrayList<>();
    private final Timer gustNormalTimer;
    private final Timer gustHarmfulTimer;
    private final Rectangle outsideBounds;
    private final StatusModel status;

    public WindModel(Configuration configuration, Random random, StatusModel status) {
        this(configuration,
                random,
                status,
                new Turbine(configuration),
                new Timer(configuration.wind().gustTimer()),
                new Timer(configuration.wind().tornadoTimer()));
    }

    public WindModel(Configuration configuration,
                     Random random,
                     StatusModel status,
                     Turbine turbine,
                     Timer gustNormalTimer,
                     Timer gustHarmfulTimer) {
        this.random = random;
        this.configuration = configuration;
        this.turbine = turbine;

        this.gustNormalTimer = gustNormalTimer;
        this.gustHarmfulTimer = gustHarmfulTimer;

        this.outsideBounds = new Rectangle(-200, 0, 100, configuration.worldHeight());
        this.status = status;
    }

    @Override
    public void update(float delta, Controls controls) {
        controls.setButtonLightVertical();

        status.update(delta, controls);
        turbine.update(delta, controls);

        spawnGusts(delta);

        for (var gust : gusts) {
            gust.update(delta, controls);
        }

        checkCollision();
    }

    @Override
    public WindState state() {
        return new WindState(
                turbine.state(),
                gusts.stream().map(Gust::state).toList()
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

    void spawnGusts(float delta) {
        if (gustNormalTimer.triggered(delta)) {
            gusts.add(new Gust(configuration, random.nextFloat(configuration.worldHeight()), false));
        }

        if (gustHarmfulTimer.triggered(delta)) {
            gusts.add(new Gust(configuration, random.nextFloat(configuration.worldHeight()), true));
        }
    }

    void checkCollision() {
        var turbineBounds = turbine.getBounds();
        var gustIterator = gusts.iterator();

        while (gustIterator.hasNext()) {
            var gust = gustIterator.next();
            var gustBounds = gust.getBounds();

            if (Intersections.intersects(turbineBounds, gustBounds)) {
                gustIterator.remove();

                if (!gust.isHarmful()) {
                    status.addEnergy(configuration.wind().energyAdded());
                } else if (!turbine.isFrozen()) {
                    status.removeHealth();
                    turbine.freeze();
                }
            }

            if (Intersections.intersects(outsideBounds, gustBounds)) {
                gustIterator.remove();
            }
        }
    }

    List<Gust> getGusts() {
        return gusts;
    }
}
