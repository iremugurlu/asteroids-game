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

public class MenuScreen extends AbstractScreen {

    private transient String small = "small";


    private void setupTitle() {
        Label title = new Label("AMALGAM", GdxGame.gameSkin, "big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 4 / 5.f);
        title.setWidth(Gdx.graphics.getWidth());
        super.addActor(title);
    }

    private void setupPlayButton() {
        TextButton playButton = new TextButton("Play!", GdxGame.gameSkin, small);
        playButton.setWidth((float) 0.4 * Gdx.graphics.getWidth());
        playButton.setHeight((float) 0.1 * Gdx.graphics.getHeight());
        playButton.setPosition(Gdx.graphics.getWidth() / 2.f - playButton.getWidth() / 2,
                7 * Gdx.graphics.getHeight() / 12.f - playButton.getHeight() / 2);
        playButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayerChoiceScreen(game, getPlayer()));
                current.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        super.addActor(playButton);
    }

    private void setupScoreButton() {
        TextButton scoresButton = new TextButton("Scoreboard", GdxGame.gameSkin, small);
        scoresButton.setWidth((float) 0.4 * Gdx.graphics.getWidth());
        scoresButton.setHeight((float) 0.1 * Gdx.graphics.getHeight());
        scoresButton.setPosition(Gdx.graphics.getWidth() / 2.f - scoresButton.getWidth() / 2,
                5 * Gdx.graphics.getHeight() / 12.f - scoresButton.getHeight() / 2);
        scoresButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ScoreboardScreen(game, getPlayer()));
                current.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        super.addActor(scoresButton);
    }


    private void setupSettingsButton() {
        TextButton settingsButton = new TextButton("Settings", GdxGame.gameSkin, small);
        settingsButton.setWidth((float) 0.4 * Gdx.graphics.getWidth());
        settingsButton.setHeight((float) 0.1 * Gdx.graphics.getHeight());
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2.f - settingsButton.getWidth() / 2,
                3 * Gdx.graphics.getHeight() / 12.f - settingsButton.getHeight() / 2);
        settingsButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingsScreen(game, getPlayer()));
                current.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        super.addActor(settingsButton);
    }

    private void setupExitButton() {
        TextButton exitButton = new TextButton("Exit", GdxGame.gameSkin, small);
        exitButton.setWidth((float) 0.4 * Gdx.graphics.getWidth());
        exitButton.setHeight((float) 0.1 * Gdx.graphics.getHeight());
        exitButton.setPosition(Gdx.graphics.getWidth() / 2.f - exitButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 12.f - exitButton.getHeight() / 2);
        exitButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        super.addActor(exitButton);
    }

    /**
     * Constructor for the menu screen in order to set the stage.
     *
     * @param thisGame Game that the screen is currently part of
     * @param player   the current player.
     */
    public MenuScreen(Game thisGame, Player player) {
        super(thisGame);
        setPlayer(player);

        setupTitle();

        //Setup all the menu buttons.
        setupPlayButton();
        setupScoreButton();
        setupSettingsButton();
        setupExitButton();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(211f / 255f, 211f / 255f, 211f / 255f, 1);
        super.render(delta);
    }
}
