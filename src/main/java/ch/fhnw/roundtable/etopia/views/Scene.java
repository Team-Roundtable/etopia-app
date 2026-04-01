package ch.fhnw.roundtable.etopia.views;

import ch.fhnw.roundtable.etopia.input.Input;
import com.badlogic.gdx.graphics.Texture;

import java.util.EnumMap;

public abstract class Scene<T extends Enum<T> & Asset> implements View {

    private final EnumMap<T, Texture> textures;
    // todo rethink showing panels, startup should be directly called and
    //  end screen should be handled in a nice way via call

    public Scene(Class<T> type) {
        this.textures = new EnumMap<>(type);

        for (T asset : type.getEnumConstants()) {
            this.textures.put(asset, new Texture(asset.getPath()));
        }
    }

    public Texture getTexture(T asset) {
        var value = textures.get(asset);

        if (value == null) {
            throw new IllegalStateException(String.format(
                    "The asset %s.%s is not loaded in scene constructor",
                    asset.getClass().getSimpleName(),
                    asset.name()));
        }

        return value;
    }

    @Override
    public final void update(float delta, Input input) {
        updateScene(delta, input);
    }

    @Override
    public final void render(Renderer renderer) {
        renderScene(renderer);
    }

    @Override
    public final void dispose() {
        disposeScene();

        for (Texture texture : textures.values()) {
            texture.dispose();
        }
    }

    public abstract void updateScene(float delta, Input input);

    public abstract void renderScene(Renderer renderer);

    public abstract SceneType change();

    public void disposeScene() {
    }
}
