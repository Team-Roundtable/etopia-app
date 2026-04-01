package ch.fhnw.roundtable.etopia.views.geothermal;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.views.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Pipe implements View {
    private final List<PipeSegment> segments;
    private final Texture segmentTexture;

    public Pipe(Texture segmentTexture) {
        this.segmentTexture = segmentTexture;
        segments = new ArrayList<>();
    }

    @Override
    public void update(float delta, Input input) {
        segments.forEach(s -> s.update(delta, input));
    }

    @Override
    public void render(Renderer renderer) {
        segments.forEach(s -> s.render(renderer));
    }

    @Override
    public void dispose() {
        segments.forEach(PipeSegment::dispose);
        segments.clear();
    }

    public void layPipe(Vector2 start, Vector2 end) {
        segments.add(new PipeSegment(segmentTexture, new Vector2(start), new Vector2(end)));
    }

    public boolean collides(Circle collider) {
        return segments.stream()
                .anyMatch(s -> s.getCollider().overlaps(collider));
    }
}
