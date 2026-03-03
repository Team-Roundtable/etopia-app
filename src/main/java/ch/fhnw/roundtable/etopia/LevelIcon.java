package ch.fhnw.roundtable.etopia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;

public class LevelIcon extends Navigable {
    public float x;
    public float y;

    public static final float RADIUS = 10f;

    public LevelIcon(float x, float y) {
        isSelected = false;
        this.x = x;
        this.y = y;
    }

    public void draw(ETopia game) {
        game.shape.setColor(Color.GRAY);
        game.shape.circle(x, y, RADIUS);

        if (isSelected) {
            game.shape.circle(x, y, RADIUS * 1.5f);
        }
    }
}

