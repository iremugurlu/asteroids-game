package asteroids.main.screens;

import client.entity.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class AbstractScreen implements Screen {

    private transient Stage stage;
    transient Game game;
    transient AbstractScreen current;
    private Player player;

    /**
     * Constructor for the AbstractScreen.
     * @param game Game that it is currently part of
     */
    public AbstractScreen(Game game) {
        this.stage = new Stage(new ScreenViewport());
        this.game = game;
        this.current = this;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addActor(Actor actor) {
        stage.addActor(actor);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
        stage.dispose();
    }

    public AbstractScreen getCurrent() {
        return current;
    }

    public Stage getStage() {
        return stage;
    }
}
