package server.controller;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.entity.Player;
import server.entity.Score;
import server.queries.PlayerQueries;
import server.queries.ScoreQueries;
import server.util.Token;


@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.DataflowAnomalyAnalysis"})
@Controller
@RequestMapping(value = "score")
public class ScoreController {

    private transient ScoreQueries scoreQueries;
    private transient PlayerQueries playerQueries;

    public ScoreController(ScoreQueries scoreQueries, PlayerQueries playerQueries) {
        this.scoreQueries = scoreQueries;
        this.playerQueries = playerQueries;
    }

    /**
     * Insert a new score in the database.
     *
     * @param json with the token, score and username.
     * @return Status.
     */
    @PostMapping(value = "create")
    public @ResponseBody
    String createScore(@RequestBody String json) {
        JSONObject res = new JSONObject();
        res.put("status", false);

        try {
            JSONObject obj = new JSONObject(json);
            String token = obj.getString("token");
            String name = obj.getString("name");
            int value = obj.getInt("value");

            if (!Token.verifyToken(token, name)) {
                res.put("message", "invalid token");
                return res.toString();
            }

            if (playerQueries.countPlayerByName(name) == 0) {
                res.put("message", "player does not exist");
                return res.toString();
            }

            Player player = playerQueries.getPlayerByName(name);

            Score score = new Score();
            score.setValue(value);
            score.setDate(new Timestamp(System.currentTimeMillis()));
            score.setPlayer(player);
            scoreQueries.save(score);

            res.put("status", true);
            return res.toString();

        } catch (JSONException ex) {
            res.put("message", "incorrect parameters");
            return res.toString();
        }
    }

    /**
     * Return top 5 scores ever.
     *
     * @return Status and scores.
     */
    @PostMapping(value = "high")
    public @ResponseBody
    String getHighScores() {
        JSONObject res = new JSONObject();
        res.put("status", false);

        List<Score> scores = scoreQueries.getHighScores();

        res.put("status", true);
        res.put("scores", scoresToJson(scores));

        return res.toString();
    }

    /**
     * Return all the scores of a player.
     *
     * @param json with the token and username.
     * @return Status and scores.
     */
    @PostMapping(value = "player/all")
    public @ResponseBody
    String getPlayerScores(@RequestBody String json) {
        JSONObject res = new JSONObject();
        res.put("status", false);

        try {
            JSONObject obj = new JSONObject(json);
            String token = obj.getString("token");
            String name = obj.getString("name");

            if (!Token.verifyToken(token, name)) {
                res.put("message", "invalid token");
                return res.toString();
            }

            List<Score> scores = scoreQueries.getScoresByPlayer(name);

            res.put("status", true);
            res.put("scores", scoresToJson(scores));

            return res.toString();

        } catch (JSONException ex) {
            res.put("message", "incorrect parameters");
            return res.toString();
        }
    }

    /**
     * Return the top 5 scores of a player.
     *
     * @param json with the token and username.
     * @return Status and scores.
     */
    @PostMapping(value = "player/high")
    public @ResponseBody
    String getPlayerHighScores(@RequestBody String json) {
        JSONObject res = new JSONObject();
        res.put("status", false);

        try {
            JSONObject obj = new JSONObject(json);
            String token = obj.getString("token");
            String name = obj.getString("name");

            if (!Token.verifyToken(token, name)) {
                res.put("message", "invalid token");
                return res.toString();
            }

            List<Score> scores = scoreQueries.getHighScoresByPlayer(name);

            res.put("status", true);
            res.put("scores", scoresToJson(scores));

            return res.toString();

        } catch (JSONException ex) {
            res.put("message", "incorrect parameters");
            return res.toString();
        }
    }

    /**
     * Converts a score list to json array.
     * @param scores as list.
     * @return json array with scores.
     */
    private JSONArray scoresToJson(List<Score> scores) {
        JSONArray scoresJson = new JSONArray();
        for (Score score : scores) {
            JSONObject scoreJson = new JSONObject();
            scoreJson.put("value", score.getValue());
            scoreJson.put("date", score.getDate());
            scoreJson.put("name", score.getPlayer().getName());
            scoresJson.put(scoreJson);
        }
        return scoresJson;
    }
}
