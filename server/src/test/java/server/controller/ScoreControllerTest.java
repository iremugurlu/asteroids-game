package server.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import server.entity.Player;
import server.entity.Score;
import server.queries.PlayerQueries;
import server.queries.ScoreQueries;
import server.util.Token;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@SuppressWarnings({"PMD.AvoidDuplicateLiterals"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({PlayerQueries.class, ScoreQueries.class, Token.class})
public class ScoreControllerTest {

    private transient ScoreController scoreController;
    private transient PlayerQueries playerQueries;
    private transient ScoreQueries scoreQueries;
    private transient List<Score> scores;
    private transient JSONArray scoresJson;

    @Before
    public void setUp() {
        System.setProperty("illegal-access", "deny");
        PowerMockito.mockStatic(ScoreQueries.class, PlayerQueries.class, Token.class);
        scoreController = new ScoreController(scoreQueries, playerQueries);

        Player player = new Player();
        player.setName("bob");
        scores = new ArrayList<>();
        Score score = new Score();
        score.setValue(100);
        score.setPlayer(player);
        score.setDate(Timestamp.valueOf("2019-01-01 00:00:00.0"));
        scores.add(score);

        scoresJson = new JSONArray();
        JSONObject scoreJson = new JSONObject();
        scoreJson.put("value", 100);
        scoreJson.put("name", "bob");
        scoreJson.put("date", Timestamp.valueOf("2019-01-01 00:00:00.0"));
        scoresJson.put(scoreJson);
    }

    @Test
    public void createNotJson() {
        JSONObject res = new JSONObject(scoreController.createScore("not json"));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void createInvalidToken() {
        JSONObject req = new JSONObject();

        req.put("name", "bob");
        req.put("token", "some token");
        req.put("value", 0);

        JSONObject res = new JSONObject(scoreController.createScore(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "invalid token");
    }

    @Test
    public void createNoPlayer() {
        JSONObject req = new JSONObject();

        req.put("name", "bob");
        req.put("token", "some token");
        req.put("value", 0);

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);
        when(mockPlayerQueries.countPlayerByName("bob")).thenReturn(0);

        when(Token.verifyToken("some token", "bob")).thenReturn(true);

        ScoreController scoreController = new ScoreController(scoreQueries, mockPlayerQueries);
        JSONObject res = new JSONObject(scoreController.createScore(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "player does not exist");
    }

    @Test
    public void createSuccess() {
        JSONObject req = new JSONObject();

        req.put("name", "bob");
        req.put("token", "some token");
        req.put("value", 0);

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);
        when(mockPlayerQueries.countPlayerByName("bob")).thenReturn(1);

        ScoreQueries mockScoreQueries = mock(ScoreQueries.class);

        when(Token.verifyToken("some token", "bob")).thenReturn(true);

        ScoreController scoreController = new ScoreController(mockScoreQueries, mockPlayerQueries);
        JSONObject res = new JSONObject(scoreController.createScore(req.toString()));

        assertEquals(res.getBoolean("status"), true);
    }

    @Test
    public void getHighSuccess() {
        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);

        ScoreQueries mockScoreQueries = mock(ScoreQueries.class);
        when(mockScoreQueries.getHighScores()).thenReturn(scores);

        ScoreController scoreController = new ScoreController(mockScoreQueries, mockPlayerQueries);
        JSONObject res = new JSONObject(scoreController.getHighScores());

        assertEquals(res.getBoolean("status"), true);
        assertEquals(res.getJSONArray("scores").toString(), scoresJson.toString());
    }

    @Test
    public void getPlayerScoresNoJson() {
        JSONObject res = new JSONObject(scoreController.getPlayerScores("not json"));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void getPlayerScoresInvalidToken() {
        JSONObject req = new JSONObject();

        req.put("name", "bob");
        req.put("token", "some token");

        JSONObject res = new JSONObject(scoreController.getPlayerScores(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "invalid token");
    }


    @Test
    public void getPlayerScoresSuccess() {
        JSONObject req = new JSONObject();

        req.put("name", "bob");
        req.put("token", "some token");

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);

        ScoreQueries mockScoreQueries = mock(ScoreQueries.class);
        when(mockScoreQueries.getScoresByPlayer("bob")).thenReturn(scores);

        when(Token.verifyToken("some token", "bob")).thenReturn(true);

        ScoreController scoreController = new ScoreController(mockScoreQueries, mockPlayerQueries);
        JSONObject res = new JSONObject(scoreController.getPlayerScores(req.toString()));

        assertEquals(res.getBoolean("status"), true);
        assertEquals(res.getJSONArray("scores").toString(), scoresJson.toString());
    }

    @Test
    public void getPlayerHighScoresNoJson() {
        JSONObject res = new JSONObject(scoreController.getPlayerHighScores("not json"));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void getPlayerHighScoresInvalidToken() {
        JSONObject req = new JSONObject();

        req.put("name", "bob");
        req.put("token", "some token");

        JSONObject res = new JSONObject(scoreController.getPlayerHighScores(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "invalid token");
    }


    @Test
    public void getPlayerHighScoresSuccess() {
        JSONObject req = new JSONObject();

        req.put("name", "bob");
        req.put("token", "some token");

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);

        ScoreQueries mockScoreQueries = mock(ScoreQueries.class);
        when(mockScoreQueries.getHighScoresByPlayer("bob")).thenReturn(scores);

        when(Token.verifyToken("some token", "bob")).thenReturn(true);

        ScoreController scoreController = new ScoreController(mockScoreQueries, mockPlayerQueries);
        JSONObject res = new JSONObject(scoreController.getPlayerHighScores(req.toString()));

        assertEquals(res.getBoolean("status"), true);
        assertEquals(res.getJSONArray("scores").toString(), scoresJson.toString());
    }
}
