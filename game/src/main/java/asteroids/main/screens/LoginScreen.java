package asteroids.main.screens;

import asteroids.main.GdxGame;
import client.controller.PlayerController;
import client.entity.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;


public class LoginScreen extends AbstractScreen {

    /**
     * Constructor for the LoginScreen class.
     * @param thisGame Game that the screen is currently part of
     */
    public LoginScreen(Game thisGame) {
        super(thisGame);

        Label title = new Label("Welcome", GdxGame.gameSkin,"big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 4 / 5.f);
        title.setWidth(Gdx.graphics.getWidth());
        super.addActor(title);

        TextField usernameField = new TextField("", GdxGame.gameSkin);
        usernameField.setWidth((float) 0.6 * Gdx.graphics.getWidth());
        usernameField.setPosition(
                Gdx.graphics.getWidth() / 2.f - usernameField.getWidth() / 2,
                2 * Gdx.graphics.getHeight() / 3.f - usernameField.getHeight() / 2
        );
        usernameField.setMessageText("Username");
        super.addActor(usernameField);

        TextField passwordField = new TextField("", GdxGame.gameSkin);
        passwordField.setWidth((float) 0.6 * Gdx.graphics.getWidth());
        passwordField.setPosition(
                Gdx.graphics.getWidth() / 2.f - passwordField.getWidth() / 2,
                Gdx.graphics.getHeight() / 2.f - passwordField.getHeight() / 2
        );
        passwordField.setMessageText("Password");
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        super.addActor(passwordField);

        TextButton loginButton = new TextButton("Login!", GdxGame.gameSkin);
        loginButton.setWidth((float) 0.6 * Gdx.graphics.getWidth());
        loginButton.setPosition(
                Gdx.graphics.getWidth() / 2.f - loginButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 6.f - loginButton.getHeight() / 2
        );
        loginButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Player p = new Player(usernameField.getText(), passwordField.getText());
                PlayerController a = new PlayerController(p);
                if (a.login()) {
                    game.setScreen(new MenuScreen(game, p));
                    current.dispose();
                } else {
                    Window warning = new Window("Warning", GdxGame.gameSkin);
                    warning.add(new Label("Invalid credentials", GdxGame.gameSkin,"black"));
                    warning.add();
                    TextButton exitButton = new TextButton("Exit", GdxGame.gameSkin, "small");
                    exitButton.setWidth(getStage().getWidth() / 8f);
                    exitButton.setHeight(getStage().getHeight() / 12f);
                    exitButton.addListener(new InputListener() {

                        @Override
                        public void touchUp(InputEvent event, float x, float y,
                                            int pointer, int button) {
                            game.setScreen(new LoginScreen(game));
                            current.dispose();
                        }

                        @Override
                        public boolean touchDown(InputEvent event, float x, float y,
                                                 int pointer, int button) {
                            return true;
                        }
                    });
                    exitButton.setPosition(warning.getWidth() / 2 - exitButton.getWidth() / 2,
                            warning.getHeight() / 2 - exitButton.getHeight() / 2);
                    warning.addActor(exitButton);
                    warning.setSize(getStage().getWidth() / 1.5f, getStage().getHeight() / 2f);
                    warning.setPosition(getStage().getWidth() / 2 - warning.getWidth() / 2,
                            getStage().getHeight() / 2 - warning.getHeight() / 2);
                    warning.setKeepWithinStage(false);
                    warning.setMovable(false);
                    LoginScreen.super.addActor(warning);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        super.addActor(loginButton);

        TextButton backButton = new TextButton("Back", GdxGame.gameSkin, "small");
        backButton.setWidth(Gdx.graphics.getWidth() / 7.f);
        backButton.setHeight(Gdx.graphics.getHeight() / 12.f);
        backButton.setPosition(
                Gdx.graphics.getWidth() / 10.f - backButton.getWidth() / 2,
                9 * Gdx.graphics.getHeight() / 10.f - backButton.getHeight() / 2
        );
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new EntranceScreen(game));
                current.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event,
                                     float x, float y, int pointer, int button) {
                return true;
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
