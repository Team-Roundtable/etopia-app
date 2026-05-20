package ch.fhnw.roundtable.etopia.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedAsset {

    private final Animation<TextureRegion> animation;
    private float time;

    public AnimatedAsset(Texture texture, int frames, float frameDuration) {
        int frameWidth = texture.getWidth() / frames;
        int frameHeight = texture.getHeight();

        TextureRegion[] regions = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {
            regions[i] = new TextureRegion(
                    texture,
                    i * frameWidth,
                    0,
                    frameWidth,
                    frameHeight
            );
        }

        animation = new Animation<>(frameDuration, regions);
    }

    public TextureRegion getKeyFrame(boolean looping) {
        return animation.getKeyFrame(time, looping);
    }

    public void update(float delta) {
        time += delta;
    }

    public void reset() {
        time = 0;
    }
}
