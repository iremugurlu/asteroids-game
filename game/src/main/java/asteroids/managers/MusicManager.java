package asteroids.managers;

import asteroids.main.AppPreferences;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class MusicManager {
    private static MusicManager manager = null;
    private transient Music song;
    private transient AppPreferences settings = new AppPreferences();

    private MusicManager() {
        return;
    }

    /**
     * Initialise the MusicManager Singleton if this has not been done yet.
     *
     * @return An instance of the MusicManager Singleton.
     */
    public static MusicManager getInstance() {
        if (manager == null) {
            manager = new MusicManager();
        }
        return manager;
    }

    public void setSong(String path) {
        this.song = Gdx.audio.newMusic(Gdx.files.getFileHandle(path, Files.FileType.Local));
    }

    /**
     * Plays the track that is currently set.
     */
    public void play() {
        if (!(this.song == null) && settings.isMusicEnabled()) {
            this.song.setVolume(settings.getMusicVolume());
            this.song.play();
            this.song.setLooping(true);
        }
    }

    public void pause() {
        this.song.pause();
    }

    public void dispose() {
        this.song.dispose();
    }
}
