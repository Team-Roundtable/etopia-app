package ch.fhnw.roundtable.etopia.views;

import ch.fhnw.roundtable.etopia.input.Input;

public abstract class Entity implements View {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public final void update(float delta, Input input) {
        updateEntity(delta, input);
    }

    @Override
    public final void render(Renderer renderer) {
        renderEntity(renderer);
    }

    // todo rethink if necessary since all assets should be injected
    @Override
    public final void dispose() {
        disposeEntity();
    }

    public abstract void updateEntity(float delta, Input input);

    public abstract void renderEntity(Renderer renderer);

    public void disposeEntity() {
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
