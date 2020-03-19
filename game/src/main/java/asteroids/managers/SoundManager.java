package asteroids.managers;

import asteroids.main.AppPreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static SoundManager manager = null;
    private transient Sound sound;
    private transient boolean on = true;
    private transient double volume = 0.5;

    private SoundManager() {
        AppPreferences preferences = new AppPreferences();
        setSoundState(preferences.isSoundEffectsEnabled());
        updateVolume(preferences.getSoundVolume());
        return;
    }

    /**
     * Initialises the SoundManager Singleton.
     *
     * @return An instance of the SoundManager Singleton.
     */
    public static SoundManager getInstance() {
        if (manager == null) {
            manager = new SoundManager();
        }
        return manager;
    }

    public void setSoundState(boolean on) {
        this.on = on;
    }

    public void updateVolume(double volume) {
        this.volume = volume;
    }

    public void setSound(String name) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + name));
    }

    /**
     * Plays the sound that is currently set.
     *
     * @param loop Boolean if the track should loop once finished.
     */
    public void play(boolean loop) {
        if (this.sound != null && on) {
            long id = this.sound.play((float) volume);
            this.sound.setLooping(id, loop);
        }
    }

    /**
     * Pauses the sound that is currently being played.
     */
    public void pause() {
        if (!(this.sound == null)) {
            this.sound.pause();
        }
    }

    public void stop() {
        this.sound.stop();
    }

    public void resume() {
        this.sound.resume();
    }

    /**
     * Disposes of the SoundManager.
     */
    public void dispose() {
        if (!(this.sound == null)) {
            this.sound.dispose();
        }
    }
}
