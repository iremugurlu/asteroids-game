package asteroids.main.screens;

import asteroids.main.GdxGame;
import client.entity.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;


public class RetryScreen extends AbstractScreen {

    /**
     * Constructor for the RetryScreen class.
     *
     * @param thisGame Game that the screen is currently part of
     * @param player   the current player.
     */
    public RetryScreen(Game thisGame, Player player) {
        super(thisGame);
        setPlayer(player);

        Label title = new Label("YOU DIED", GdxGame.gameSkin, "big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() / 2.f);
        title.setWidth(Gdx.graphics.getWidth());
        super.addActor(title);

        TextButton letDie = new TextButton("Let Die", GdxGame.gameSkin);
        letDie.setWidth(0.4f * Gdx.graphics.getWidth());
        letDie.setPosition(
                0.f,
                0.f
        );
        letDie.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game, getPlayer()));
                current.dispose();
            }
        });
        super.addActor(letDie);

        TextButton retry = new TextButton("Try Again", GdxGame.gameSkin);
        retry.setWidth(0.4f * Gdx.graphics.getWidth());
        retry.setPosition(
                Gdx.graphics.getWidth() - retry.getWidth(),
                0.f
        );
        retry.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new AsteroidScreen(game, player));
                current.dispose();
            }
        });
        super.addActor(retry);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        super.render(delta);
    }
}
