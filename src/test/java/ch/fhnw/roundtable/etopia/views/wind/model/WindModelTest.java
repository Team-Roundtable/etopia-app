package ch.fhnw.roundtable.etopia.views.wind.model;

import ch.fhnw.roundtable.etopia.Result;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.configuration.Wind;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.shapes.Rectangle;
import ch.fhnw.roundtable.etopia.time.Timer;
import ch.fhnw.roundtable.etopia.views.status.model.StatusModel;
import ch.fhnw.roundtable.etopia.views.wind.state.WindGustState;
import ch.fhnw.roundtable.etopia.views.wind.state.WindState;
import ch.fhnw.roundtable.etopia.views.wind.state.WindTurbineState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WindModelTest {

    private static final float DELTA_TIME = 1f;
    private static final float WORLD_HEIGHT = 800f;
    private static final float ENERGY_ADDED = 0.25f;

    private Random random;
    private StatusModel status;
    private Turbine turbine;
    private Timer normalTimer;
    private Timer harmfulTimer;
    private Controls controls;

    private WindModel model;

    @BeforeEach
    void setUp() {
        Configuration configuration = mock(Configuration.class);
        Wind windConfiguration = mock(Wind.class);
        random = mock(Random.class);
        status = mock(StatusModel.class);
        turbine = mock(Turbine.class);
        normalTimer = mock(Timer.class);
        harmfulTimer = mock(Timer.class);
        controls = mock(Controls.class);

        when(configuration.worldHeight())
                .thenReturn(WORLD_HEIGHT);

        when(configuration.wind())
                .thenReturn(windConfiguration);

        when(windConfiguration.energyAdded())
                .thenReturn(ENERGY_ADDED);

        model = new WindModel(
                configuration,
                random,
                status,
                turbine,
                normalTimer,
                harmfulTimer
        );
    }

    @Test
    void shouldSetVerticalLightOnUpdate() {
        model.update(DELTA_TIME, controls);

        verify(controls).setButtonLightVertical();
    }

    @Test
    void shouldUpdateStatusAndTurbine() {
        model.update(DELTA_TIME, controls);

        verify(status).update(DELTA_TIME, controls);
        verify(turbine).update(DELTA_TIME, controls);
    }

    @Test
    void shouldSpawnNormalGust() {
        when(normalTimer.triggered(DELTA_TIME))
                .thenReturn(true);

        when(random.nextFloat(WORLD_HEIGHT))
                .thenReturn(100f);

        model.spawnGusts(DELTA_TIME);

        assertEquals(1, model.getGusts().size());
        assertFalse(model.getGusts().getFirst().isHarmful());
    }

    @Test
    void shouldSpawnHarmfulGust() {
        when(harmfulTimer.triggered(DELTA_TIME))
                .thenReturn(true);

        when(random.nextFloat(WORLD_HEIGHT))
                .thenReturn(100f);

        model.spawnGusts(DELTA_TIME);

        assertEquals(1, model.getGusts().size());
        assertTrue(model.getGusts().getFirst().isHarmful());
    }

    @Test
    void shouldAddEnergyOnHarmlessCollision() {
        Gust gust = mock(Gust.class);

        model.getGusts().add(gust);

        Rectangle sameBounds =
                new Rectangle(0, 0, 10, 10);

        when(turbine.getBounds())
                .thenReturn(sameBounds);

        when(gust.getBounds())
                .thenReturn(sameBounds);

        when(gust.isHarmful())
                .thenReturn(false);

        model.checkCollision();

        verify(status).addEnergy(ENERGY_ADDED);
        assertEquals(0, model.getGusts().size());
    }

    @Test
    void shouldRemoveHealthAndFreezeOnHarmfulCollision() {
        Gust gust = mock(Gust.class);

        model.getGusts().add(gust);

        Rectangle sameBounds =
                new Rectangle(0, 0, 10, 10);

        when(turbine.getBounds())
                .thenReturn(sameBounds);

        when(gust.getBounds())
                .thenReturn(sameBounds);

        when(gust.isHarmful())
                .thenReturn(true);

        when(turbine.isFrozen())
                .thenReturn(false);

        model.checkCollision();

        verify(status).removeHealth();
        verify(turbine).freeze();
    }

    @Test
    void shouldNotDamageFrozenTurbine() {
        Gust gust = mock(Gust.class);

        model.getGusts().add(gust);

        Rectangle sameBounds =
                new Rectangle(0, 0, 10, 10);

        when(turbine.getBounds())
                .thenReturn(sameBounds);

        when(gust.getBounds())
                .thenReturn(sameBounds);

        when(gust.isHarmful())
                .thenReturn(true);

        when(turbine.isFrozen())
                .thenReturn(true);

        model.checkCollision();

        verify(status, never()).removeHealth();
        verify(turbine, never()).freeze();
    }

    @Test
    void shouldReturnFailHealthWhenDead() {
        when(status.isDead()).thenReturn(true);

        assertEquals(Result.FAIL_HEALTH, model.result());
    }

    @Test
    void shouldReturnFailTimeWhenFinished() {
        when(status.isDead()).thenReturn(false);
        when(status.isFinished()).thenReturn(true);

        assertEquals(Result.FAIL_TIME, model.result());
    }

    @Test
    void shouldReturnSuccessWhenPowered() {
        when(status.isPowered()).thenReturn(true);

        assertEquals(Result.SUCCESS, model.result());
    }

    @Test
    void shouldReturnRunningOtherwise() {
        assertEquals(Result.RUNNING, model.result());
    }

    @Test
    void shouldPrioritizeFailHealthOverEverythingElse() {
        when(status.isDead()).thenReturn(true);
        when(status.isFinished()).thenReturn(true);
        when(status.isPowered()).thenReturn(true);

        assertEquals(Result.FAIL_HEALTH, model.result());
    }

    @Test
    void shouldUpdateAllGusts() {
        Gust gust1 = mock(Gust.class);
        Gust gust2 = mock(Gust.class);

        Rectangle nonCollidingBounds =
                new Rectangle(1000, 1000, 10, 10);

        when(gust1.getBounds())
                .thenReturn(nonCollidingBounds);

        when(gust2.getBounds())
                .thenReturn(nonCollidingBounds);

        when(turbine.getBounds())
            .thenReturn(
                new Rectangle(0, 0, 10, 10)
            );

        model.getGusts().add(gust1);
        model.getGusts().add(gust2);

        model.update(DELTA_TIME, controls);

        verify(gust1).update(DELTA_TIME, controls);
        verify(gust2).update(DELTA_TIME, controls);
    }

    @Test
    void shouldReturnCorrectState() {
        Gust gust = mock(Gust.class);

        WindGustState gustState =
                mock(WindGustState.class);

        WindTurbineState turbineState =
                mock(WindTurbineState.class);

        model.getGusts().add(gust);

        when(turbine.state())
                .thenReturn(turbineState);

        when(gust.state())
                .thenReturn(gustState);

        WindState state = model.state();

        assertEquals(turbineState, state.turbine());
        assertEquals(1, state.gusts().size());
        assertEquals(gustState, state.gusts().getFirst());
    }

    @Test
    void shouldRemoveGustWhenOutsideBounds() {
        Gust gust = mock(Gust.class);

        model.getGusts().add(gust);

        Rectangle outsideRectangle =
                new Rectangle(-150, 0, 10, 10);

        when(gust.getBounds())
                .thenReturn(outsideRectangle);

        when(turbine.getBounds())
            .thenReturn(
                new Rectangle(
                        500,
                        500,
                        10,
                        10
                )
            );

        model.checkCollision();

        assertEquals(0, model.getGusts().size());
    }
}
