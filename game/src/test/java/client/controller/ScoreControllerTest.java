package client.controller;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

import client.entity.Player;
import client.entity.Score;
import client.utils.PostRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RunWith(PowerMockRunner.class)
@PrepareForTest({PostRequest.class, ScoreController.class})
public class ScoreControllerTest {

    private transient ScoreController scoreController;
    private transient PostRequest mock;
    private transient Player player;
    private transient List<Score> scores;
    private transient JSONArray scoresJson;

    /**
     * Ran before starting to test.
     */
    @Before
    public void setUp() throws Exception {
        System.setProperty("illegal-access", "deny");

        scoreController = new ScoreController();
        player = new Player("bob", "123");
        player.setToken("some token");

        mock = mock(PostRequest.class);
        whenNew(PostRequest.class).withAnyArguments().thenReturn(mock);

        scores = new ArrayList<>();
        Score score = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));
        scores.add(score);

        scoresJson = new JSONArray();
        JSONObject scoreJson = new JSONObject();
        scoreJson.put("value", 100);
        scoreJson.put("name", "bob");
        scoreJson.put("date", Timestamp.valueOf("2019-01-01 00:00:00.0"));
        scoresJson.put(scoreJson);
    }

    @Test
    public void createNoResponse() {
        when(mock.send()).thenReturn("");

        assertEquals(scoreController.create(player, 100), false);
    }

    @Test
    public void createSuccess() {
        JSONObject res = new JSONObject();
        res.put("status", true);
        when(mock.send()).thenReturn(res.toString());

        assertEquals(scoreController.create(player, 100), true);
    }

    @Test
    public void getHighScoresNoResponse() {
        when(mock.send()).thenReturn("");

        assertEquals(scoreController.getHighScores(), null);
    }

    @Test
    public void getHighScoresFail() {
        JSONObject res = new JSONObject();
        res.put("status", false);
        when(mock.send()).thenReturn(res.toString());

        assertEquals(scoreController.getHighScores(), null);
    }

    @Test
    public void getHighScoresSuccess() {
        JSONObject res = new JSONObject();
        res.put("status", true);
        res.put("scores", scoresJson);

        when(mock.send()).thenReturn(res.toString());

        assertEquals(scoreController.getHighScores(), scores);
    }

    @Test
    public void getPlayerScoresNoResponse() {
        when(mock.send()).thenReturn("");

        assertEquals(scoreController.getPlayerScores(player), null);
    }

    @Test
    public void getPlayerScoresFail() {
        JSONObject res = new JSONObject();
        res.put("status", false);
        when(mock.send()).thenReturn(res.toString());

        assertEquals(scoreController.getPlayerScores(player), null);
    }

    @Test
    public void getPlayerScoresSuccess() {
        JSONObject res = new JSONObject();
        res.put("status", true);
        res.put("scores", scoresJson);

        when(mock.send()).thenReturn(res.toString());

        assertEquals(scoreController.getPlayerScores(player), scores);
    }

    @Test
    public void getPlayerHighScoresNoResponse() {
        when(mock.send()).thenReturn("");

        assertEquals(scoreController.getPlayerHighScores(player), null);
    }

    @Test
    public void getPlayerHighScoresFail() {
        JSONObject res = new JSONObject();
        res.put("status", false);
        when(mock.send()).thenReturn(res.toString());

        assertEquals(scoreController.getPlayerHighScores(player), null);
    }

    @Test
    public void getPlayerHighScoresSuccess() {
        JSONObject res = new JSONObject();
        res.put("status", true);
        res.put("scores", scoresJson);

        when(mock.send()).thenReturn(res.toString());

        assertEquals(scoreController.getPlayerHighScores(player), scores);
    }
}
