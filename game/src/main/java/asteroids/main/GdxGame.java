package asteroids.main;

import asteroids.main.screens.EntranceScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GdxGame extends Game {

    public static Skin gameSkin;

    @Override
    public void create() {
        gameSkin = new Skin(Gdx.files.internal("game/assets/skin/glassy-ui.json"));
        this.setScreen(new EntranceScreen(this));
        Gdx.gl.glClearColor(211f / 255f, 211f / 255f, 211f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setResizable(false);
        AppPreferences.setLargeScreenEnabled(false);

    }

    public void render() {
        super.render();
    }

    public void dispose() {
    }

}
