package asteroids.main.screens;

import asteroids.main.AppPreferences;
import asteroids.main.GdxGame;
import asteroids.managers.SoundManager;
import client.entity.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingsScreen extends AbstractScreen {

    private transient Label volumeMusicLabel;
    private transient Label volumeSoundLabel;
    private transient Label musicOnOffLabel;
    private transient Label soundOnOffLabel;
    private transient Label largeScreenLabel;
    private transient Label title;
    private transient AppPreferences preferences;
    private transient String black = "black";


    /**
     * Constructor for the settings screen in order to set the stage.
     * @param game Game that the screen is currently part of
     */
    public SettingsScreen(Game game, Player player) {
        super(game);
        super.setPlayer(player);

        this.preferences = new AppPreferences();
        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        super.addActor(table);
        // temporary until we have asset manager in
        Skin skin = GdxGame.gameSkin;

        // music volume
        final Slider volumeMusicSlider = volumeSlider(skin);

        // sound volume
        final Slider soundMusicSlider = soundSlider(skin);

        // music on/off
        final CheckBox musicCheckbox = musicCheckbox(skin);

        // sound on/off
        final CheckBox soundEffectsCheckbox = soundCheckbox(skin);

        // largescreen on/off
        final CheckBox largeScreenCheckBox = screenCheckbox(skin);


        title = new Label("SETTINGS", GdxGame.gameSkin,"big-black");
        volumeMusicLabel = new Label("Music Volume", skin, black);
        volumeSoundLabel = new Label("Sound Volume", skin, black);
        musicOnOffLabel = new Label("Music", skin, black);
        soundOnOffLabel = new Label("Sound Effect", skin, black);
        largeScreenLabel = new Label("Large Screen", skin, black);

        table.add(title).center().pad(0, 100, 0, 0);
        table.row().pad(30,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(10,0,0,10);
        table.add(largeScreenLabel).left();
        table.add(largeScreenCheckBox);

        TextButton backButton = new TextButton("Back",GdxGame.gameSkin, "small");
        backButton.setWidth(Gdx.graphics.getWidth() / 7.f);
        backButton.setHeight(Gdx.graphics.getHeight() / 12.f);
        backButton.setPosition(
                9.f * Gdx.graphics.getWidth() / 10.f - backButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 10.f - backButton.getHeight() / 2
        );
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game, getPlayer()));
                current.dispose();
            }

            @Override
            public boolean touchDown(InputEvent event,
                                     float x, float y, int pointer, int button) {
                return true;
            }
        });
        table.row().pad(30,0,0,10);
        table.add(backButton).left();
    }

    private Slider volumeSlider(Skin skin){
        Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(this.preferences.getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                preferences.setMusicVolume(volumeMusicSlider.getValue());
                // updateVolumeLabel();
                return false;
            }
        });
        return volumeMusicSlider;
    }

    private Slider soundSlider(Skin skin){
        Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(preferences.getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                preferences.setSoundVolume(soundMusicSlider.getValue());
                SoundManager.getInstance().updateVolume(soundMusicSlider.getValue());
                return false;
            }
        });
        return soundMusicSlider;
    }

    private CheckBox musicCheckbox(Skin skin){
        CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(preferences.isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                preferences.setMusicEnabled(enabled);
                return false;
            }
        });
        return musicCheckbox;
    }

    private CheckBox soundCheckbox(Skin skin){
        CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(preferences.isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                if (enabled) {
                    SoundManager.getInstance().setSoundState(true);
                } else {
                    SoundManager.getInstance().setSoundState(false);
                }
                preferences.setSoundEffectsEnabled(enabled);
                return false;
            }
        });
        return soundEffectsCheckbox;
    }

    private CheckBox screenCheckbox(Skin skin){
        CheckBox largeScreenCheckBox = new CheckBox(null, skin);
        largeScreenCheckBox.setChecked(preferences.isLargeScreenEnabled());
        largeScreenCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (largeScreenCheckBox.isChecked()) {
                    int width = Gdx.graphics.getWidth();
                    int height = Gdx.graphics.getHeight();
                    Gdx.graphics.setWindowedMode(2 * width, 2 * height);
                    preferences.setLargeScreenEnabled(true);
                } else {
                    int width = Gdx.graphics.getWidth();
                    int height = Gdx.graphics.getHeight();
                    Gdx.graphics.setWindowedMode(width / 2, height / 2);
                    preferences.setLargeScreenEnabled(false);
                }

            }
        });
        return largeScreenCheckBox;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(211f / 255f, 211f / 255f, 211f / 255f, 1);

        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        getStage().getViewport().update(width, height, true);
    }
}
