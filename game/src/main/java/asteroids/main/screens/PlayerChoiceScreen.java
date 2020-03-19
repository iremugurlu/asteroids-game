package asteroids.main.screens;

import asteroids.main.GdxGame;
import asteroids.managers.EntityManager;
import client.entity.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class PlayerChoiceScreen extends AbstractScreen {

    /**
     * Constructor for the menu screen in order to set the stage.
     * @param thisGame Game that it is currently part of
     */
    public PlayerChoiceScreen(Game thisGame, Player player) {
        super(thisGame);
        super.setPlayer(player);

        Label title = new Label("Game Options", GdxGame.gameSkin,"big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 7 / 10.f);
        title.setWidth(Gdx.graphics.getWidth());
        super.addActor(title);

        TextButton loginButton = new TextButton("Single Player", GdxGame.gameSkin, "small");
        loginButton.setWidth((float) 0.4 * Gdx.graphics.getWidth());
        loginButton.setPosition(Gdx.graphics.getWidth() / 2.f - loginButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2.f - loginButton.getHeight() / 2);
        loginButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntityManager.setNumOfPlayers(1);
                game.setScreen(new AsteroidScreen(game, getPlayer()));
                current.dispose();
            }
        });
        super.addActor(loginButton);

        TextButton registerButton = new TextButton("Multi player", GdxGame.gameSkin, "small");
        registerButton.setWidth((float) 0.4 * Gdx.graphics.getWidth());
        registerButton.setPosition(Gdx.graphics.getWidth() / 2.f - registerButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 5.f - registerButton.getHeight() / 2);
        registerButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntityManager.setNumOfPlayers(2);
                game.setScreen(new AsteroidScreen(game, getPlayer()));
                current.dispose();
            }
        });
        super.addActor(registerButton);

        TextButton backButton = new TextButton("Back", GdxGame.gameSkin, "small");
        backButton.setWidth(Gdx.graphics.getWidth() / 7.f);
        backButton.setHeight(Gdx.graphics.getHeight() / 12.f);
        backButton.setPosition(
                Gdx.graphics.getWidth() / 10.f - backButton.getWidth() / 2,
                9 * Gdx.graphics.getHeight() / 10.f - backButton.getHeight() / 2
        );
        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, getPlayer()));
                current.dispose();
            }
        });
        super.addActor(backButton);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(211f / 255f, 211f / 255f, 211f / 255f, 1);
        super.render(delta);
    }
}
