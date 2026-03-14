package ch.fhnw.roundtable.etopia;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// todo I know bad pattern, mainly to experiment with this style of handling animations
public class AnimationHelper {

    private final Animation<TextureRegion> animation;
    private float time;

    public AnimationHelper(String path, int width, int height, float duration) {

        var texture = new Texture(path);

        var textureRegions = TextureRegion.split(texture, width, height);

        var textures = new TextureRegion[textureRegions.length * textureRegions[0].length];

        var index = 0;
        for (TextureRegion[] x : textureRegions) {
            for (TextureRegion y : x) {
                textures[index] = y;
                index++;
            }
        }

        animation = new Animation<>(duration, textures);
    }

    public TextureRegion getKeyFrame(boolean looping) {
        return animation.getKeyFrame(time, looping);
    }

    public void updateTime(float delta) {
        time += delta;
    }
}
