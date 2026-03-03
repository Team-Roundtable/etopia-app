package ch.fhnw.roundtable.etopia;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.LinkedList;
import java.util.List;


public class Menu implements Screen {

    private final ETopia eTopia;
    private List<LevelIcon> levelIcons;
    private LevelIcon currentSelected;


    public Menu(ETopia eTopia) {
        this.eTopia = eTopia;

        this.levelIcons = new LinkedList<>();
        this.levelIcons.add(new LevelIcon(200, 400));
        this.levelIcons.add(new LevelIcon(100, 300));
        this.levelIcons.add(new LevelIcon(200, 300));
        this.levelIcons.add(new LevelIcon(300, 300));
        this.levelIcons.add(new LevelIcon(200, 200));

        this.levelIcons.get(0).down = this.levelIcons.get(2);
        this.levelIcons.get(2).up = this.levelIcons.get(0);

        this.levelIcons.get(1).right = this.levelIcons.get(2);
        this.levelIcons.get(2).left = this.levelIcons.get(1);

        this.levelIcons.get(2).right = this.levelIcons.get(3);
        this.levelIcons.get(3).left = this.levelIcons.get(2);

        this.levelIcons.get(2).down = this.levelIcons.get(4);
        this.levelIcons.get(4).up = this.levelIcons.get(2);

        select(this.levelIcons.get(0));

    }


    private void select(LevelIcon element) {
        if (currentSelected != null) {
            currentSelected.isSelected = false;
        }
        currentSelected = element;
        currentSelected.isSelected = true;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);

        eTopia.viewport.apply();
        eTopia.batch.setProjectionMatrix(eTopia.viewport.getCamera().combined);

        eTopia.batch.begin();
        //draw text. Remember that x and y are in meters
        eTopia.font.draw(eTopia.batch, "Welcome to Drop!!! ", 1, 1.5f);
        eTopia.font.draw(eTopia.batch, "Tap anywhere to begin!", 1, 1);
        eTopia.font.draw(eTopia.batch, "ARROW KEYS to select (but no logic implemented)", 1.6f, 1.2f);

        eTopia.batch.end();

        if (Gdx.input.isTouched()) {
            eTopia.setScreen(new GameScreen(eTopia));
            dispose();
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (currentSelected.left != null) select((LevelIcon) currentSelected.left);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (currentSelected.right != null) select((LevelIcon) currentSelected.right);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (currentSelected.up != null) select((LevelIcon) currentSelected.up);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (currentSelected.down != null) select((LevelIcon) currentSelected.down);
        }


        eTopia.shape.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < levelIcons.size(); i++) {
            levelIcons.get(i).draw(eTopia);
        }
        eTopia.shape.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
