package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Drill implements View {

    private static final Logger LOGGER = LoggerFactory.getLogger(Drill.class);

    private static final float COLLIDER_RADIES = 48;
    private final Sprite drill;
    private static final float DRILL_SPEED = 400;
    private static final float MOVE_SPEED = 600;
    private GameState gameState;
    private final Plumber plumber;

    public Drill(Texture drillTexture, Texture pipeTexture, float drillStartY) {
        drill = new Sprite(drillTexture);
        drill.setPosition(ETopia.WORLD_WIDTH / 2f, drillStartY);
        drill.scale(4);

        this.plumber = new Plumber(pipeTexture);
    }

    @Override
    public void update(float delta, Input input) {
        switch (gameState) {
            case PREPARATION -> {
                plumber.startPipingDown(new Vector2(drill.getX(), drill.getY()));
            }
            case DRILL_DOWN -> {
                drill.translateY(-DRILL_SPEED * delta);
                drill.translateX(input.getHorizontalInput() * MOVE_SPEED * delta);
                plumber.updatePosition(new Vector2(drill.getX(), drill.getY()));
            }
            case AT_BOTTOM -> {
                plumber.startPipingUp(new Vector2(drill.getX(), drill.getY()));
            }
            case DRILL_UP -> {
                drill.translateY(DRILL_SPEED * delta);
                drill.translateX(input.getHorizontalInput() * MOVE_SPEED * delta);
                plumber.updatePosition(new Vector2(drill.getX(), drill.getY()));
            }
            case FINISHED_DRILLING, GAME_OVER -> {
            }
            default -> LOGGER.warn("Unexpected value: {}", gameState);
        }

        plumber.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            drill.draw(batch);
        });

        plumber.render(renderer);
    }

    @Override
    public void dispose() {

    }

    public float getY() {
        return drill.getY();
    }

    void updateGameState(GameState newState) {
        this.gameState = newState;

        if (Objects.requireNonNull(newState) == GameState.AT_BOTTOM) {
            drill.setRotation(180);
        }
    }

    public Circle getCollider() {
        return new Circle(drill.getX() + drill.getWidth() / 2f, drill.getY() + drill.getHeight() / 2f, COLLIDER_RADIES);
    }
    
    public boolean collidesWithPipe() {
        return plumber.collides(getCollider());
    }
}
