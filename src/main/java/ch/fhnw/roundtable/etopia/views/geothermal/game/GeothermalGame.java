package ch.fhnw.roundtable.etopia.views.geothermal.game;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.helpers.Timer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.geothermal.GeothermalConfiguration;
import ch.fhnw.roundtable.etopia.views.health.game.HealthGame;

import java.util.Random;

public class GeothermalGame implements Game {

    private final GeothermalConfiguration geothermalConfiguration;
    private final Drill drill;
    private final Pipe downPipe;
    private final Pipe upPipe;
    private final Rocks rocks;
    private final HealthGame healthGame;
    private final Timer pipeTimer = new Timer(0.1f);
    private boolean down = true;
    private float collisionTimer = 2f;

    public GeothermalGame(Random random, GeothermalConfiguration geothermalConfiguration) {
        this.geothermalConfiguration = geothermalConfiguration;
        this.drill = new Drill(geothermalConfiguration);
        this.downPipe = new Pipe(geothermalConfiguration);
        this.upPipe = new Pipe(geothermalConfiguration);
        this.rocks = new Rocks(random, geothermalConfiguration);
        this.healthGame = new HealthGame(3);
    }

    @Override
    public void update(float delta, Input input) {

        drill.update(delta, input, down);

        if (pipeTimer.triggered(delta)) {
            if (down) {
                downPipe.add(drill.getX(), drill.getY());
            } else {
                upPipe.add(drill.getX(), drill.getY());
            }
        }

        var drillBounds = drill.getBounds();

        collisionTimer -= delta;
        if (collisionTimer <= 0) {
            if (rocks.intersects(drillBounds)) {
                healthGame.subtract();
                collisionTimer = 2f;
            }

            if (!down && downPipe.intersects(drillBounds)) {
                healthGame.subtract();
                collisionTimer = 2f;
            }
        }

        if (drill.getY() <= ETopia.WORLD_HEIGHT / 2f) {
            down = false;
            collisionTimer = 3f;
        }

    }

    @Override
    public ViewType next() {
        if (healthGame.dead()) {
            return ViewType.GEOTHERMAL_FAIL_HEALTH;
        }

        if (drill.getY() >= geothermalConfiguration.mapSize.height() - ETopia.WORLD_HEIGHT / 2f) {
            return ViewType.GEOTHERMAL_SUCCESS;
        }

        return null;
    }

    public Drill getDrill() {
        return drill;
    }

    public Pipe getDownPipe() {
        return downPipe;
    }

    public Pipe getUpPipe() {
        return upPipe;
    }

    public Rocks getRocks() {
        return rocks;
    }

    public HealthGame getHealthGame() {
        return healthGame;
    }
}
