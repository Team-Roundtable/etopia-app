package ch.fhnw.roundtable.etopia.views.wind;

import ch.fhnw.roundtable.etopia.ETopia;
import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.views.Entity;
import ch.fhnw.roundtable.etopia.views.Renderer;
import com.badlogic.gdx.graphics.Texture;

public class LifePoints extends Entity {

    private final Texture hearts;
    private int currentLives = 3;

    public LifePoints(Texture hearts){
        super(ETopia.WORLD_WIDTH / 3f - 64 * 3, ETopia.WORLD_HEIGHT - 64, 64, 64);
        this.hearts = hearts;
    }

    @Override
    public void updateEntity(float delta, Input input) {

    }

    @Override
    public void renderEntity(Renderer renderer) {
        renderer.batch(batch -> {
            for(int i = 0; i < currentLives; i++){
                float spaceX = x + (i * (width + 10f));
                batch.draw(hearts, spaceX, y, width, height);
            }
        });
    }

    public void loseLife(){
        if(currentLives > 0){
            currentLives--;
        }
    }

    public int getCurrentLives(){
        return currentLives;
    }

}
