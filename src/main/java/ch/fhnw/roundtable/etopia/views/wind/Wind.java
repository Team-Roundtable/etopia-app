package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Scene;
import ch.fhnw.roundtable.etopia.views.SceneType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wind extends Scene<WindAsset> {

    private final Random random = new Random();

    private final Turbine turbine;
    private final Production production;
    private final Power power;
    private final List<Gust> gusts = new ArrayList<>();

    private final float GUST_COOLDOWN = 0.5f;
    private float gustTimer = 0f;

    public Wind() {
        super(WindAsset.class);

        turbine = new Turbine(getTexture(WindAsset.TURBINE), getTexture(WindAsset.POLE));
        production = new Production(getTexture(WindAsset.PRODUCTION_BAR), getTexture(WindAsset.PRODUCTION_INDICATOR));
        power = new Power(getTexture(WindAsset.POWER_BAR), getTexture(WindAsset.POWER_INDICATOR));
    }

    @Override
    public void updateScene(float delta, Input input) {
        gustTimer -= delta;
        if (gustTimer < 0) {
            gustTimer = GUST_COOLDOWN;

            gusts.add(new Gust(ETopia.WORLD_WIDTH, random.nextFloat(ETopia.WORLD_HEIGHT), getTexture(WindAsset.GUST)));

            production.addIndicator(-0.05f);
        }

        turbine.update(delta, input);

        for (Gust gust : gusts) {
            gust.update(delta, input);
        }

        var turbineBounds = turbine.getBounds();
        var iterator = gusts.iterator();
        while (iterator.hasNext()) {
            var gust = iterator.next();

            if (turbineBounds.overlaps(gust.getBounds())) {
                iterator.remove();
                production.addIndicator(0.1f);
                if (production.getLevel() >= -0.3 && production.getLevel() <= 0.3) {
                    power.addIndicator(0.05f);
                }
            } else if (gust.getX() + gust.getWidth() < 0) {
                iterator.remove();
            }
        }

        production.update(delta, input);
    }

    @Override
    public void renderScene(Renderer renderer) {

        renderer.batch(batch -> {
            batch.draw(getTexture(WindAsset.BACKGROUND), 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
        });

        turbine.render(renderer);

        for (Gust gust : gusts) {
            gust.render(renderer);
        }

        production.render(renderer);
        power.render(renderer);
    }

    @Override
    public SceneType change() {
        return power.getLevel() == 1.0f ? SceneType.MAP : null;
    }
}
