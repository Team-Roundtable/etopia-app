package ch.fhnw.roundtable.etopia.views;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;

public class Assets<T extends Enum<T> & Asset> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Assets.class);
    private final Map<T, Texture> textures;

    public Assets(Class<T> type) {
        this.textures = new EnumMap<>(type);

        for (T asset : type.getEnumConstants()) {
            this.textures.put(asset, new Texture(asset.getPath()));
        }
    }

    public Texture getTexture(T asset) {
        var value = textures.get(asset);

        if (value == null) {
            LOGGER.error("Asset {}.{} is not loaded", asset.getClass().getSimpleName(), asset.name());
        }

        return value;
    }

    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
    }
}
