package ch.fhnw.roundtable.etopia;

import ch.fhnw.roundtable.etopia.input.Input;
import ch.fhnw.roundtable.etopia.minigames.MainMenu;
import ch.fhnw.roundtable.etopia.view.Menu;
import ch.fhnw.roundtable.etopia.view.MiniGame;
import ch.fhnw.roundtable.etopia.view.View;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;

public class ETopia implements ApplicationListener {

    private Renderer renderer;
    private Input input;

    private final List<View> views = List.of(new MainMenu());
    private int currentView = 0;

    // todo global game state, maybe via singleton

    @Override
    public void create() {
        // todo move magic values
        renderer = new Renderer(1920, 1080);
        input = new Input();

        // todo rework this shit (very very bad)
        views.get(currentView).create();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(Color.BLACK);

        views.get(currentView).update(delta, input);

        renderer.begin();
        views.get(currentView).render(renderer);
        renderer.end();

        var nextView = -1;

        if (views.get(currentView) instanceof Menu menu && menu.changeView() != null) {
            for (int i = 0; i < views.size(); i++) {
                if (menu.changeView().isInstance(views.get(i))) {
                    nextView = i;
                }
            }
        }

        if (views.get(currentView) instanceof MiniGame minigame) {
            if (minigame.isCompleted()) {
                nextView = 0;
            }
        }

        if (nextView != -1) {
            views.get(currentView).dispose();

            views.get(nextView).create();
            currentView = nextView;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
