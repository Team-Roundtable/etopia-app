package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Plumber implements View {

    private static final Logger LOGGER = LoggerFactory.getLogger(Plumber.class);

    private final Pipe pipesDown;
    private final Pipe pipesUp;

    private Vector2 lastPosition;

    private ActivePipe direction;

    public Plumber(Texture pipeTexture) {
        pipesDown = new Pipe(pipeTexture);
        pipesUp = new Pipe(pipeTexture);

        direction = ActivePipe.NONE;
    }

    public void startPipingDown(Vector2 position) {
        lastPosition = position;
        direction = ActivePipe.DOWN;
    }

    public void startPipingUp(Vector2 position) {
        lastPosition = position;
        direction = ActivePipe.UP;
    }

    public void updatePosition(Vector2 newPosition) {
        if (ActivePipe.NONE == direction) {
            return;
        }

        float dst = lastPosition.dst(newPosition);
        if (dst > PipeSegment.PIPE_LENGTH) {
            switch (direction) {
                case NONE -> {
                }
                case UP -> pipesUp.layPipe(lastPosition, newPosition);
                case DOWN -> pipesDown.layPipe(lastPosition, newPosition);
                default -> LOGGER.warn("Unexpected value: {}", direction);
            }
            lastPosition = newPosition;
        }
    }

    @Override
    public void update(float delta, Input input) {
        pipesDown.update(delta, input);
        pipesUp.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        pipesDown.render(renderer);
        pipesUp.render(renderer);
    }

    @Override
    public void dispose() {
        pipesDown.dispose();
        pipesUp.dispose();
    }

    public boolean collides(Circle collider) {
        switch (direction) {
            case NONE, DOWN -> {
                return false;
            }
            case UP -> {
                return pipesDown.collides(collider);
            }
            default -> {
                LOGGER.warn("Unexpected direction: {}", direction);
                return false;
            }
        }
    }

    private enum ActivePipe {
        NONE, UP, DOWN
    }
}
