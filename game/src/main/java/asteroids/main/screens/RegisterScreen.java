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

public class RegisterScreen extends AbstractScreen {

    transient String small = "small";

    private TextField createNewTextField(String message, float x, float y, boolean password) {
        TextField temp = new TextField("", GdxGame.gameSkin);
        temp.setWidth((float) 0.6 * Gdx.graphics.getWidth());
        temp.setPosition(
                Gdx.graphics.getWidth() / 2.f - temp.getWidth() / 2,
                y * Gdx.graphics.getHeight() / x - temp.getHeight() / 2
        );

        temp.setMessageText(message);
        temp.setPasswordCharacter('*');
        temp.setPasswordMode(password);
        super.addActor(temp);
        return temp;
    }

    private void setupWelcomeText() {
        Label title = new Label("Welcome", GdxGame.gameSkin, "big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 4 / 5.f);
        title.setWidth(Gdx.graphics.getWidth());
        super.addActor(title);
    }

    private TextButton registerButton() {
        TextButton temp = new TextButton("Register!", GdxGame.gameSkin);
        temp.setWidth((float) 0.6 * Gdx.graphics.getWidth());
        temp.setPosition(
                Gdx.graphics.getWidth() / 2.f - temp.getWidth() / 2,
                Gdx.graphics.getHeight() / 6.f - temp.getHeight() / 2
        );
        return temp;
    }

    private void setupBackButton() {
        TextButton backButton = new TextButton("Back",GdxGame.gameSkin, small);
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
    /**
     * Constructor for the RegisterScreen class.
     *
     * @param thisGame Game that the screen is currently part of
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public RegisterScreen(Game thisGame) {
        super(thisGame);

        setupWelcomeText();

        TextField passwordField = createNewTextField("Password", 24, 13, true);
        TextField passwordField2 = createNewTextField("Password Again", 12, 5, true);
        TextField usernameField = createNewTextField("Username", 3, 2, false);

        TextButton registerButton = registerButton();

        registerButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (passwordField.getText().equals(passwordField2.getText())) {
                    Player p = new Player(usernameField.getText(), passwordField.getText());
                    PlayerController a = new PlayerController(p);
                    if (a.create()) {
                        createPopup("Success", "Registered Successfully!", "Continue", new LoginScreen(game));
                    } else {
                        createPopup("Warning", "Username already exists!", "Try again", new RegisterScreen(game));
                    }
                } else {
                    createPopup("Warning", "Passwords do not match!", "Try again", new RegisterScreen(game));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        super.addActor(registerButton);

        setupBackButton();
    }

    private void createPopup(String title, String message, String continueButton, AbstractScreen nextScreen) {
        Window warning = new Window(title, GdxGame.gameSkin);
        warning.add(new Label(message, GdxGame.gameSkin,"black"));
        warning.add();
        TextButton exitButton = new TextButton(continueButton, GdxGame.gameSkin, small);
        exitButton.setWidth(getStage().getWidth() / 6f);
        exitButton.setHeight(getStage().getHeight() / 12f);
        exitButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                game.setScreen(nextScreen);
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
        RegisterScreen.super.addActor(warning);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(211f / 255f, 211f / 255f, 211f / 255f, 1);
        super.render(delta);
    }
}
