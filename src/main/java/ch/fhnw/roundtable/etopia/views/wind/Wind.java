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
    private final Power power;
    private final List<Gust> gusts = new ArrayList<>();
    private final List<Tornado> tornadoes = new ArrayList<>();
    private final LifePoints lifePoints;

    private final float GUST_COOLDOWN = 0.5f;
    private final float TORNADO_COOLDOWN = 2.5f;
    private float gustTimer = 0f;
    private float tornadoTimer = 0f;
    private float freezeTimer = 0f;
    private float gameTimer = 40f;

    public Wind() {
        super(WindAsset.class);

        turbine = new Turbine(getTexture(WindAsset.TURBINE), getTexture(WindAsset.POLE));
        power = new Power(getTexture(WindAsset.POWER_BAR), getTexture(WindAsset.POWER_INDICATOR));
        lifePoints = new LifePoints(getTexture(WindAsset.LIFEPOINT));
    }

    @Override
    public void updateScene(float delta, Input input) {
        gustTimer -= delta;
        if (gustTimer < 0) {
            gustTimer = GUST_COOLDOWN;
            gusts.add(new Gust(ETopia.WORLD_WIDTH, random.nextFloat(ETopia.WORLD_HEIGHT), getTexture(WindAsset.GUST)));
        }

        tornadoTimer -= delta;
        if (tornadoTimer < 0) {
            tornadoTimer = TORNADO_COOLDOWN;
            tornadoes.add(new Tornado(ETopia.WORLD_WIDTH, random.nextFloat(ETopia.WORLD_HEIGHT), getTexture(WindAsset.TORNADO)));
        }

        if (freezeTimer > 0){
            freezeTimer -= delta;
            turbine.setFrozen(true);
            if(freezeTimer <= 0){
                turbine.setFrozen(false);
            }
        } else {
            turbine.update(delta, input);
        }

        gameTimer -= delta;
        if (gameTimer <= 0){
            //todo add a finish state
            System.out.println("Timer expired");
        }

        for (Gust gust : gusts) {
            gust.update(delta, input);
        }

        for (Tornado tornado : tornadoes) {
            tornado.update(delta, input);
        }

        var turbineBounds = turbine.getBounds();
        var iterator = gusts.iterator();
        var tornadoIterator = tornadoes.iterator();
        while (iterator.hasNext()) {
            var gust = iterator.next();

            if (turbineBounds.overlaps(gust.getBounds())) {
                iterator.remove();
                power.addIndicator(0.05f);
            } else if (gust.getX() + gust.getWidth() < 0) {
                iterator.remove();
            }
        }

        while(tornadoIterator.hasNext()){
            var tornado = tornadoIterator.next();

            if (turbineBounds.overlaps(tornado.getBounds())) {
                tornadoIterator.remove();
                freezeTimer = 1f;
                power.addIndicator(-0.25f);
                lifePoints.loseLife();

                //todo add a proper game fail screen
                if(lifePoints.getCurrentLives() <= 0){
                    System.out.println("Game Over");
                }

            } else if (tornado.getX() + tornado.getWidth() < 0) {
                tornadoIterator.remove();
            }
        }
    }

    @Override
    public void renderScene(Renderer renderer) {

        renderer.batch(batch -> {
            batch.draw(getTexture(WindAsset.BACKGROUND), 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
        });

        turbine.render(renderer);

        lifePoints.render(renderer);

        power.render(renderer);

        for (Gust gust : gusts) {
            gust.render(renderer);
        }

        for(Tornado tornado : tornadoes){
            tornado.render(renderer);
        }
    }

    @Override
    public SceneType change() {
        return null;
    }
}
