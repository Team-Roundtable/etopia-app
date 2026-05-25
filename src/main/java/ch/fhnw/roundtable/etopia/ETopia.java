package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.input.ControlsFactory;
import ch.fhnw.roundtable.etopia.rendering.Renderer;
import ch.fhnw.roundtable.etopia.views.map.Map;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class ETopia implements ApplicationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ETopia.class);
    private static final float HEARTBEAT_RATE = 10f;
    private final Configuration configuration;

    private Renderer renderer;
    private Controls controls;
    private View view;
    private float heartbeat = 0;

    public ETopia(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void create() {
        controls = ControlsFactory.create(configuration);
        renderer = new Renderer();

        view = new Map(configuration);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    @SuppressWarnings({"checkstyle:WhitespaceAround", "PMD.AvoidCatchingThrowable"})
    public void render() {
        try {
            float delta = Gdx.graphics.getDeltaTime();

            logHeartbeat(delta);

            ScreenUtils.clear(Color.BLACK);

            controls.update();

            view.update(delta, controls);
            view.render(renderer);

            var transition = view.transition();

            if (transition instanceof Transition.Change(Supplier<View> next)) {
                view.dispose();

                var nextView = next.get();
                logTransition(nextView);

                view = nextView;
            }
        } catch (Exception e) {
            LOGGER.error("FATAL: exception in main loop, trying to recover", e);
            if (view != null) {
                view.dispose();
            }
            view = new Map(configuration);
        } catch (Throwable t) {
            LOGGER.error("FATAL: error in main loop", t);
            throw t;
        }
    }

    private void logHeartbeat(float delta) {
        heartbeat += delta;

        if (LOGGER.isTraceEnabled() && heartbeat > HEARTBEAT_RATE) {
            LOGGER.trace("Heartbeat: {}", view.getClass().getSimpleName());
            heartbeat = 0;
        }
    }

    private void logTransition(View nextView) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Transition: {} -> {}",
                    view.getClass().getSimpleName(),
                    nextView.getClass().getSimpleName());
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        controls.setButtonLightNone();
        view.dispose();
        renderer.dispose();
    }
}
