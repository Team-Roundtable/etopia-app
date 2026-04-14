package ch.fhnw.roundtable.etopia.views.biogas.game;

import ch.fhnw.roundtable.etopia.helpers.Timer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Game;
import ch.fhnw.roundtable.etopia.views.ViewType;
import ch.fhnw.roundtable.etopia.views.biogas.BiogasConfiguration;
import ch.fhnw.roundtable.etopia.views.energy.game.EnergyGame;
import ch.fhnw.roundtable.etopia.views.health.game.HealthGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BiogasGame implements Game {

    private final Random random;
    private final BiogasConfiguration biogasConfiguration;
    private final Cursor cursor;
    private final Trash[][] conveyor;
    private final Timer trashTimer = new Timer(0.5f);
    private final HealthGame healthGame;
    private final EnergyGame energyGame;

    public BiogasGame(Random random, BiogasConfiguration biogasConfiguration) {
        this.random = random;
        this.biogasConfiguration = biogasConfiguration;
        this.cursor = new Cursor(biogasConfiguration);
        this.conveyor = new Trash[biogasConfiguration.conveyorWidth][biogasConfiguration.conveyorHeight];
        this.healthGame = new HealthGame(3);
        this.energyGame = new EnergyGame();
    }

    @SuppressWarnings("PMD.NullAssignment")
    @Override
    public void update(float delta, Input input) {
        if (trashTimer.triggered(delta)) {
            for (Trash trash : shift()) {
                if (trash.isBiodegradable()) {
                    energyGame.update(0.05f);
                } else {
                    healthGame.subtract();
                }
            }

            var x = biogasConfiguration.conveyorWidth - 1;
            var y = random.nextInt(biogasConfiguration.conveyorHeight);
            conveyor[x][y] = new Trash(biogasConfiguration.trashSize, random.nextBoolean());
        }

        cursor.update(input);

        if (input.isSelectJustPressed()) {
            var trash = conveyor[cursor.getX()][cursor.getY()];

            if (trash != null) {
                if (trash.isBiodegradable()) {
                    healthGame.subtract();
                }

                conveyor[cursor.getX()][cursor.getY()] = null;
            }
        }
    }

    @Override
    public ViewType next() {
        if (healthGame.dead()) {
            return ViewType.BIOGAS_FAIL_HEALTH;
        }

        if (energyGame.full()) {
            return ViewType.BIOGAS_SUCCESS;
        }

        return null;
    }

    public Cursor getCursor() {
        return cursor;
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public Trash[][] getConveyor() {
        return conveyor;
    }

    public HealthGame getHealthGame() {
        return healthGame;
    }

    public EnergyGame getEnergyGame() {
        return energyGame;
    }

    @SuppressWarnings("PMD.NullAssignment")
    private List<Trash> shift() {
        var dropped = new ArrayList<Trash>();

        for (int y = 0; y < conveyor[0].length; y++) {
            var first = conveyor[0][y];

            if (first != null) {
                dropped.add(first);
                conveyor[0][y] = null;
            }

            for (int x = 1; x < conveyor.length; x++) {
                conveyor[x - 1][y] = conveyor[x][y];
                conveyor[x][y] = null;
            }
        }
        return dropped;
    }
}
