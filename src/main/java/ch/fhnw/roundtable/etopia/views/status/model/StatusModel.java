package ch.fhnw.roundtable.etopia.views.status.model;

import ch.fhnw.roundtable.etopia.Renderable;
import ch.fhnw.roundtable.etopia.Updateable;
import ch.fhnw.roundtable.etopia.configuration.Configuration;
import ch.fhnw.roundtable.etopia.input.Controls;
import ch.fhnw.roundtable.etopia.views.status.state.StatusState;

public class StatusModel implements Updateable, Renderable<StatusState> {

    private final float totalTime;
    private final int totalHealth;

    private float energy;
    private int health;
    private float time;

    public StatusModel(Configuration configuration, float totalTime) {
        this.totalTime = totalTime;
        this.totalHealth = configuration.status().totalHealth();
        this.energy = 0;
        this.health = totalHealth;
        this.time = totalTime;
    }

    public void addEnergy(float amount) {
        energy += Math.clamp(amount, 0, 1);
    }

    public void setEnergy(float amount) {
        energy = Math.clamp(amount, 0, 1);
    }

    public void removeHealth() {
        health--;
    }

    public boolean isPowered() {
        return energy >= 1f;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isFinished() {
        return time <= 0;
    }

    @Override
    public void update(float delta, Controls controls) {
        time -= delta;
    }

    @Override
    public StatusState state() {
        return new StatusState(
                Math.clamp(time / totalTime, 0f, 1f),
                Math.clamp(energy, 0f, 1f),
                Math.clamp((float) health / totalHealth, 0f, 1f)
        );
    }
}
