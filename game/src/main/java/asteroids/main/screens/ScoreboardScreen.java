package asteroids.main.screens;

import asteroids.main.GdxGame;
import client.controller.ScoreController;
import client.entity.Player;
import client.entity.Score;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.SimpleDateFormatNeedsLocale"})
public class ScoreboardScreen extends AbstractScreen {

    private transient String black = "black";

    /**
     * Constructor for the ScoreboardScreen class.
     *
     * @param thisGame Game that the screen is currently part of
     * @param player   the current player
     */
    public ScoreboardScreen(Game thisGame, Player player) {
        super(thisGame);
        setPlayer(player);

        setLabels();
        addScores();

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
        super.addActor(backButton);
    }

    private void setLabels(){
        Label title = new Label("SCOREBOARD", GdxGame.gameSkin, "big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 4 / 5.f);
        title.setWidth(Gdx.graphics.getWidth());
        super.addActor(title);

        Label name = new Label("NAME", GdxGame.gameSkin, black);
        name.setX(Gdx.graphics.getWidth() / 4 - name.getWidth() / 2);
        name.setY(Gdx.graphics.getHeight() * 7 / 10.f);
        name.setWidth(Gdx.graphics.getWidth());
        super.addActor(name);

        Label score = new Label("SCORE", GdxGame.gameSkin, black);
        score.setX(Gdx.graphics.getWidth() / 2 - score.getWidth() / 2);
        score.setY(Gdx.graphics.getHeight() * 7 / 10.f);
        score.setWidth(Gdx.graphics.getWidth());
        super.addActor(score);

        Label date = new Label("DATE", GdxGame.gameSkin, black);
        date.setX(3 * Gdx.graphics.getWidth() / 4 - date.getWidth() / 2);
        date.setY(Gdx.graphics.getHeight() * 7 / 10.f);
        date.setWidth(Gdx.graphics.getWidth());
        super.addActor(date);

    }

    private void addScores(){
        ScoreController scoreController = new ScoreController();
        List<Score> highScores = scoreController.getHighScores();
        int size = 5;

        if (size > highScores.size()) {
            size = highScores.size();
        }

        for (int i = 0; i < size; i++) {
            Label labelName = new Label(highScores.get(i).getName(), GdxGame.gameSkin, black);
            labelName.setX(Gdx.graphics.getWidth() / 4 - labelName.getWidth() / 2);
            labelName.setY(Gdx.graphics.getHeight()
                    * (6 / 10.f - i * (1 / 10.f)) - labelName.getHeight() / 2);
            labelName.setWidth(Gdx.graphics.getWidth());
            super.addActor(labelName);
            Label labelScore = new Label(String.valueOf(highScores.get(i).getValue()),
                    GdxGame.gameSkin, black);
            labelScore.setX(Gdx.graphics.getWidth() / 2 - labelScore.getWidth() / 2);
            labelScore.setY(Gdx.graphics.getHeight()
                    * (6 / 10.f - i * (1 / 10.f)) - labelScore.getHeight() / 2);
            labelScore.setWidth(Gdx.graphics.getWidth());
            super.addActor(labelScore);

            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Label labelDate = new Label(format.format(highScores.get(i).getDate()),
                    GdxGame.gameSkin, black);
            labelDate.setX(3 * Gdx.graphics.getWidth() / 4 - labelDate.getWidth() / 2);
            labelDate.setY(Gdx.graphics.getHeight()
                    * (6 / 10.f - i * (1 / 10.f)) - labelDate.getHeight() / 2);
            labelDate.setWidth(Gdx.graphics.getWidth());
            super.addActor(labelDate);
        }

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(211f / 255f, 211f / 255f, 211f / 255f, 1);
        super.render(delta);
    }
}
