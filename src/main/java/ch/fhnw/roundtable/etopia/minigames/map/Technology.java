package ch.fhnw.roundtable.etopia.minigames.map;

import ch.fhnw.roundtable.etopia.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.minigames.panel.Panel;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Technology implements View {

    private final Class<? extends View> view;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final String texturePath;
    private final Panel panel;
    private TextureRegion textureUnselected;
    private TextureRegion textureSelected;

    private boolean selected = false;

    protected Technology(Class<? extends View> view, float x, float y, float width, float height, String texturePath, Panel panel) {
        this.view = view;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texturePath = texturePath;
        this.panel = panel;
    }

    @Override
    public void create() {
        var textures = TextureRegion.split(new Texture(texturePath), 64, 64)[0];
        textureUnselected = textures[0];
        textureSelected = textures[1];
        panel.create();
    }

    @Override
    public void update(float delta, Input input) {
    }

    @Override
    public void render(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(selected ? textureSelected : textureUnselected, x, y, width, height);
        });

        if (selected) {
            panel.render(renderer);
        }
    }

    @Override
    public void dispose() {

    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Class<? extends View> getView() {
        return view;
    }
}
