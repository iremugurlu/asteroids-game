
import asteroids.main.GdxGame;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {

    /**
     * Run the application.
     *
     * @param arg Array of arguments.
     */
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "AMALGAM";
        config.addIcon("ui/icon.png", Files.FileType.Local);
        new LwjglApplication(new GdxGame(), config);
    }
}

