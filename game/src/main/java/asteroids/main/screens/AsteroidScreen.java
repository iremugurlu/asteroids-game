package asteroids.main.screens;

import asteroids.managers.EntityManager;
import asteroids.managers.MusicManager;
import asteroids.managers.ScoreManager;
import asteroids.managers.SoundManager;
import client.controller.ScoreController;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class AsteroidScreen extends AbstractScreen {

    private transient EntityManager entityManager;

    private transient boolean playing;
    private transient SpriteBatch batch;
    private transient BitmapFont font;
    private transient SoundManager soundManager;
    private transient MusicManager musicManager;
    private transient TextureRegion background;
    private transient int offsetY;
    private transient int width;
    private transient int height;
    private transient boolean flip = false;
    private static final transient int BACKGROUND_SPEED = 2;

    /**
     * Constructor for the AsteroidScreen.
     *
     * @param thisGame Game that it is currently part of
     * @param player   the current player.
     */
    public AsteroidScreen(Game thisGame, client.entity.Player player) {
        super(thisGame);
        setPlayer(player);
        musicManager = MusicManager.getInstance();
        soundManager = SoundManager.getInstance();
        // clear all previous sounds
        soundManager.dispose();
        Texture backgroundTexture =
                new Texture("game/src/main/resources/backgrounds/background.jpg");
        width = backgroundTexture.getWidth();
        height = backgroundTexture.getHeight();
        offsetY = height;
        background = new TextureRegion(backgroundTexture, 0, offsetY - Gdx.graphics.getHeight(),
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        playing = true;
        entityManager = EntityManager.getInstance();
        entityManager.init();
        musicManager.setSong("CrabRave.mp3");
        batch = new SpriteBatch();
        font = new BitmapFont();
        super.show();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            playing = !playing;
        }

        if (!playing) {
            pauseHelper();
        } else if (entityManager.isRunning()) {
            musicManager.play();
            Gdx.gl.glClearColor(211f / 255f, 211f / 255f, 211f / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();

            drawBackground();
            drawScore();
            drawPlayerLives();

            batch.end();
            entityManager.update(Gdx.graphics.getDeltaTime());

        } else {
            ScoreController scoreController = new ScoreController();
            scoreController.create(getPlayer(), ScoreManager.getInstance().getScore(0));
            ScoreManager.reset();
            game.setScreen(new RetryScreen(game, getPlayer()));
            current.dispose();
            musicManager.dispose();
            soundManager.setSound("game over.wav");
            soundManager.play(false);
        }
    }

    @Override
    public void dispose() {
        entityManager.dispose();
        super.dispose();
    }

    private void pauseHelper() {
        musicManager.pause();
        soundManager.pause();
        batch.begin();
        Sprite pause = new Sprite(new Texture(Gdx.files.getFileHandle("ui/pause.png",
                Files.FileType.Local)));
        batch.draw(
                pause,
                (Gdx.graphics.getWidth() / 2) - 25,
                (Gdx.graphics.getHeight() / 2) - 25, 50, 50
        );
        batch.end();
    }

    private void drawScore(){
        int totalScore = 0;
        for (int i = 0; i < EntityManager.getInstance().getPlayers().size(); i++) {
            totalScore += ScoreManager.getInstance().getScore(i);
            String score = Integer.toString(ScoreManager.getInstance().getScore(i));
            font.draw(batch, score, 10 + 100 * i, 50);
        }

        if (!entityManager.getInstance().getVersus()) {
            String score = Integer.toString(totalScore);
            font.draw(
                    batch,
                    "Total: " + score,
                    10 + 100 * EntityManager.getInstance().getPlayers().size(),
                    50
            );
        }
    }

    private void drawPlayerLives(){
        Sprite life = new Sprite(
                new Texture(Gdx.files.getFileHandle("ui/heart.png", Files.FileType.Local))
        );
        int offsetX = 10;
        for (int z = 0; z < entityManager.getPlayers().size(); z++) {
            font.draw(batch, "Player " + z + " :", offsetX, 20);
            offsetX += 100;
            for (int i = 0; i < entityManager.getPlayers().get(z).getLives(); i++) {
                batch.draw(life, offsetX, 5, 20, 20);
                offsetX += 22;
            }
            offsetX += 25;
        }
    }

    private void drawBackground(){
        background.setRegion(
                0,
                offsetY - Gdx.graphics.getHeight(),
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
        offsetY -= BACKGROUND_SPEED;
        if (offsetY - Gdx.graphics.getHeight() < 0) {
            offsetY = height - BACKGROUND_SPEED - Gdx.graphics.getHeight();
        }
        background.flip(false, flip);
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
