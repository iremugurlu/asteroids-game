package asteroids.main.screens;

import asteroids.main.GdxGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class EntranceScreen extends AbstractScreen {

    /**
     * Constructor for the menu screen in order to set the stage.
     * @param thisGame Game that it is currently part of
     */
    public EntranceScreen(Game thisGame) {
        super(thisGame);

        Label title = new Label("AMALGAM", GdxGame.gameSkin,"big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 7 / 10.f);
        title.setWidth(Gdx.graphics.getWidth());
        super.addActor(title);

        TextButton registerButton = new TextButton("Login", GdxGame.gameSkin);
        registerButton.setWidth((float) 0.6 * Gdx.graphics.getWidth());
        registerButton.setPosition(Gdx.graphics.getWidth() / 2.f - registerButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2.f - registerButton.getHeight() / 2);
        registerButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoginScreen(game));
                current.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        super.addActor(registerButton);

        TextButton loginButton = new TextButton("Register", GdxGame.gameSkin);
        loginButton.setWidth((float) 0.6 * Gdx.graphics.getWidth());
        loginButton.setPosition(Gdx.graphics.getWidth() / 2.f - loginButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 5.f - loginButton.getHeight() / 2);
        loginButton.addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new RegisterScreen(game));
                current.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        super.addActor(loginButton);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(211f / 255f, 211f / 255f, 211f / 255f, 1);
        super.render(delta);
    }
}
