package ch.fhnw.roundtable.etopia.minigames.wind;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.MiniGame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wind implements MiniGame {

    private final Random random = new Random();

    private Texture background;
    private Turbine turbine;
    private List<Gust> gusts;
    private Production production;
    private Power power;

    private final float GUST_COOLDOWN = 0.5f;
    private float gustTimer = 0f;

    // todo time limit
    // todo energy level

    @Override
    public boolean isCompleted() {
        return power.getLevel() == 1.0f;
    }

    @Override
    public void create() {
        background = new Texture("assets/wind/background.png");

        turbine = new Turbine();
        turbine.create();

        gusts = new ArrayList<>();

        production = new Production();
        production.create();

        power = new Power();
        power.create();
    }

    @Override
    public void update(float delta, Input input) {
        gustTimer -= delta;
        if (gustTimer < 0) {
            gustTimer = GUST_COOLDOWN;

            var gust = new Gust();
            gust.create();
            gust.setX(ETopia.WORLD_WIDTH);
            gust.setY(random.nextFloat(ETopia.WORLD_HEIGHT));

            gusts.add(gust);

            production.addIndicator(-0.05f);
        }

        turbine.update(delta, input);

        for (Gust gust : gusts) {
            gust.update(delta, input);
        }

        var turbineBounds = turbine.getBounds();
        var cutoffBounds = new Rectangle(-100, 0, 100, ETopia.WORLD_HEIGHT);
        var iterator = gusts.iterator();
        while (iterator.hasNext()) {
            var gust = iterator.next();

            if (turbineBounds.overlaps(gust.getBounds())) {
                iterator.remove();
                production.addIndicator(0.1f);
                if (production.getLevel() >= -0.3 && production.getLevel() <= 0.3) {
                    power.addIndicator(0.05f);
                }
            } else if (cutoffBounds.overlaps(gust.getBounds())) {
                iterator.remove();
            }
        }

        production.update(delta, input);
    }

    @Override
    public void render(Renderer renderer) {

        renderer.batch(batch -> {
            batch.draw(background, 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
        });

        turbine.render(renderer);

        for (Gust gust : gusts) {
            gust.render(renderer);
        }

        production.render(renderer);
        power.render(renderer);
    }

    @Override
    public void dispose() {

    }
}
