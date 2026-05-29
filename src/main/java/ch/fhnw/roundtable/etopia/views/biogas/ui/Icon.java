package ch.fhnw.roundtable.etopia.views.biogas.ui;

import ch.fhnw.roundtable.etopia.views.biogas.state.BiogasTrashState;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class Icon {
    private final BiogasTrashState state;
    private Vector2 goal;
    private Vector2 position;

    public Icon(BiogasTrashState state) {
        this.position = new Vector2(state.x(), state.y());
        this.state = state;
    }

    public BiogasTrashState getState() {
        return state;
    }

    public Vector2 getAnimatedPosition() {
        return position;
    }

    public void setGoal(Vector2 goal) {
        this.goal = goal;
    }

    public void move(float delta) {
        position = position.interpolate(goal, delta * 20f, Interpolation.linear);
    }
}
