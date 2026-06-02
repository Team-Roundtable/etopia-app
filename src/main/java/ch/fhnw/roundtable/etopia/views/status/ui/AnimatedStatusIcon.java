package ch.fhnw.roundtable.etopia.views.status.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class AnimatedStatusIcon {
    private final Vector2 start;
    private final Vector2 end;
    private final StatusAsset asset;
    private final Interpolation interpolation;
    private final float speed;
    private float t;

    public StatusAsset getAsset() {
        return asset;
    }

    public Vector2 getPosition() {
        return position;
    }

    private Vector2 position;

    public AnimatedStatusIcon(Vector2 start, Vector2 end, StatusAsset asset, Interpolation interpolation, float speed) {
        this.start = start;
        this.end = end;
        this.asset = asset;
        this.interpolation = interpolation;
        this.t = 0;
        this.position = Vector2.Zero;
        this.speed = speed;
    }

    public AnimatedStatusIcon(Vector2 start, Vector2 end, StatusAsset asset, Interpolation interpolation) {
        this(start, end, asset, interpolation, 1f);
    }

    public void move(float delta) {
        t += delta * speed;
        position = start.interpolate(end, t, interpolation);
    }

    public float distanceToTarget() {
        return position.dst(end);
    }
}
