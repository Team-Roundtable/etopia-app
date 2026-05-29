package ch.fhnw.roundtable.etopia.views.status.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class AnimatedStatusIcon {
    private final Vector2 start;
    private final Vector2 end;
    private final StatusAsset asset;
    private float t;

    public StatusAsset getAsset() {
        return asset;
    }

    public Vector2 getPosition() {
        return position;
    }

    private Vector2 position;

    public AnimatedStatusIcon(Vector2 start, Vector2 end, StatusAsset asset) {
        this.start = start;
        this.end = end;
        this.asset = asset;
        this.t = 0;
        this.position = Vector2.Zero;
    }

    public void move(float delta, Interpolation interpolation) {
        t += delta;
        position = start.interpolate(end, t, interpolation);
    }

    public float distanceToTarget() {
        return position.dst(end);
    }
}
