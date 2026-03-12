package ch.fhnw.roundtable.etopia.minigames.map.technologies;

import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.helpers.Navigatable;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SelectableTechnology implements Navigatable, View {
    public final float x;
    public final float y;
    private boolean isSelected;
    private final static float SCALE = 2f;

    private Navigatable up;
    private Navigatable down;
    private Navigatable left;
    private Navigatable right;

    private final Class<? extends View> correspondingView;
    private final Texture texture;

    public SelectableTechnology(float x, float y, Class<? extends View> correspondingView, Texture texture) {
        this.x = x;
        this.y = y;
        this.correspondingView = correspondingView;
        this.texture = texture;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void unselect() {
        isSelected = false;
    }

    @Override
    public void select() {
        isSelected = true;
    }

    @Override
    public void create() {

    }

    @Override
    public void update(float delta, Input input) {

    }

    @Override
    public void render(Renderer renderer) {
        var scale = SCALE;
        if (isSelected()) {
            scale *= (float) (1.1 + 0.02 * Math.sin(System.currentTimeMillis() * 0.006));
        }
        float width = texture.getWidth() * scale;
        float height = texture.getHeight() * scale;

        renderer.batch.begin();
        renderer.batch.draw(texture, x - width/2, y - height/2, width, height);
        renderer.batch.end();

        if (correspondingView == null) {
            renderer.shape.begin(ShapeRenderer.ShapeType.Filled);
            renderer.shape.setColor(Color.CORAL);

            var offset = width/3;
            var lineWidth = 8f;
            renderer.shape.rectLine(new Vector2(x - offset, y - offset), new Vector2(x + offset, y + offset), lineWidth);
            renderer.shape.end();
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Class<? extends View> getCorrespondingView() {
        return correspondingView;
    }

    @Override
    public Navigatable getUp() {
        return up;
    }

    @Override
    public Navigatable getDown() {
        return down;
    }

    @Override
    public Navigatable getLeft() {
        return left;
    }

    @Override
    public Navigatable getRight() {
        return right;
    }

    @Override
    public void setUp(Navigatable navigatable) {
         up = navigatable;
    }

    @Override
    public void setDown(Navigatable navigatable) {
         down = navigatable;
    }

    @Override
    public void setLeft(Navigatable navigatable) {
         left = navigatable;
    }

    @Override
    public void setRight(Navigatable navigatable) {
         right = navigatable;
    }
}
