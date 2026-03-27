package ch.fhnw.roundtable.etopia.views.map;

import ch.fhnw.roundtable.etopia.views.Renderer;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import ch.fhnw.roundtable.etopia.views.SceneType;
import ch.fhnw.roundtable.etopia.views.commons.Navigation;
import ch.fhnw.roundtable.etopia.views.commons.panel.Panel;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Technology extends Entity {

    private final SceneType view;
    private final Panel panel;
    private TextureRegion textureUnselected;
    private TextureRegion textureSelected;
    public final Navigation<Technology> navigation;

    private boolean selected = false;

    protected Technology(SceneType view, float x, float y, float width, float height, Texture texture, Panel panel) {
        super(x, y, width, height);
        this.view = view;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.panel = panel;

        var textures = TextureRegion.split(texture, 64, 64)[0];
        textureUnselected = textures[0];
        textureSelected = textures[1];

        navigation = new Navigation<>(this);
    }

    @Override
    public void updateEntity(float delta, Input input) {
    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            batch.draw(selected ? textureSelected : textureUnselected, x, y, width, height);
        });

        if (selected) {
            panel.render(renderer);
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public SceneType getView() {
        return view;
    }
}
