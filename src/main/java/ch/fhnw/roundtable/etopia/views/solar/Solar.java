package ch.fhnw.roundtable.etopia.views.solar;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.Scene;
import ch.fhnw.roundtable.etopia.views.SceneType;

public class Solar extends Scene<SolarAsset> {

    private final Sun sun;
    private final Panel panel;
    private final Power power;
    private float powerOutput;

    public Solar() {
        super(SolarAsset.class);
        this.sun = new Sun(getTexture(SolarAsset.SUN));
        this.panel = new Panel(getTexture(SolarAsset.PANEL), getTexture(SolarAsset.STAND), getTexture(SolarAsset.DIRT));
        this.power = new Power(getTexture(SolarAsset.POWER_BAR), getTexture(SolarAsset.POWER_INDICATOR));
    }

    @Override
    public void updateScene(float delta, Input input) {

        if (!sun.getIsCycleOver()) {
            powerOutputHandler(delta);
        } else {
            powerOutput = 0;
        }

        sun.update(delta, input);
        panel.update(delta, input);
    }

    private void powerOutputHandler(float delta) {
        float diff = calculatePanelAngleDifference();

        powerOutput = Math.max(0, (float) Math.cos(Math.toRadians(diff)) * (
                panel.getDirtLayer() == 0
                        ? 1f
                        : panel.getDirtLayer() == 1
                        ? 0.6f
                        : panel.getDirtLayer() == 2
                        ? 0.3f
                        : 0
        ));

        power.addIndicator(powerOutput * delta);
    }

    private float calculatePanelAngleDifference() {
        float xDifference = sun.getCenterX() - panel.getCenterX();
        float yDifference = sun.getCenterY() - panel.getCenterY();

        float targetAngle = (float) Math.toDegrees(Math.atan2(yDifference, xDifference)) - 90f; // -90f (degrees) to turn it into the correct direction

        float diff = Math.abs(panel.getRotation() - targetAngle) % 360;

        if (diff > 180) {
            diff = 360 - diff;
        }
        return diff;
    }

    @Override
    public void renderScene(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(getTexture(SolarAsset.BACKGROUND), 0, 0, ETopia.WORLD_WIDTH, ETopia.WORLD_HEIGHT);
            renderer.font.draw(batch, (powerOutput * 100) + "%", ETopia.WORLD_WIDTH / 3f, ETopia.WORLD_HEIGHT);
        });
        sun.render(renderer);
        panel.render(renderer);
        power.render(renderer);
    }

    @Override
    public SceneType change() {
        return power.getLevel() == 1.0f ? SceneType.MAP : null;
    }
}