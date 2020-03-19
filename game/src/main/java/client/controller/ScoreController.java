package client.controller;

import client.entity.Player;
import client.entity.Score;
import client.utils.PostRequest;
import client.utils.PropertyFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"PMD.AvoidDuplicateLiterals"})
public class ScoreController {

    private final transient PropertyFile propertyFile;

    public ScoreController() {
        propertyFile = new PropertyFile("url.properties");
    }

    /**
     * Makes a post request to the server to
     * insert a score in the database.
     *
     * @param player which creates the score.
     * @param score  the score to be inserted.
     * @return true if insert was successful.
     */
    public boolean create(Player player, int score) {
        String createScoreUrl = propertyFile.getProperties().getProperty("url.score.create");
        PostRequest postRequest = new PostRequest(createScoreUrl, true);
        postRequest.addParameterString("token", player.getToken());
        postRequest.addParameterString("name", player.getName());
        postRequest.addParameterInt("value", score);
        String response = postRequest.send();
        try {
            JSONObject res = new JSONObject(response);
            return res.getBoolean("status");
        } catch (JSONException ex) {
            return false;
        }
    }

    /**
     * Makes a post request to the server to
     * get the top 5 scores ever.
     *
     * @return the scores as list.
     */
    public List<Score> getHighScores() {
        String highScoreUrl = propertyFile.getProperties().getProperty("url.score.high");
        PostRequest postRequest = new PostRequest(highScoreUrl, true);
        return getScores(postRequest);
    }

    /**
     * Makes a post request to the server to
     * get all the scores of a player.
     *
     * @return the scores as list.
     */
    public List<Score> getPlayerScores(Player player) {
        String playerScoreUrl = propertyFile.getProperties().getProperty("url.score.player.all");
        PostRequest postRequest = new PostRequest(playerScoreUrl, true);
        postRequest.addParameterString("token", player.getToken());
        postRequest.addParameterString("name", player.getName());
        return getScores(postRequest);
    }

    /**
     * Makes a post request to the server to
     * get top 5 high scores of a player.
     *
     * @return the scores as list.
     */
    public List<Score> getPlayerHighScores(Player player) {
        String playerHighScoreUrl = propertyFile
                .getProperties().getProperty("url.score.player.high");
        PostRequest postRequest = new PostRequest(playerHighScoreUrl, true);
        postRequest.addParameterString("token", player.getToken());
        postRequest.addParameterString("name", player.getName());
        return getScores(postRequest);
    }

    /**
     * Makes a post request to the server to get scores.
     *
     * @return the scores as list.
     */
    public List<Score> getScores(PostRequest postRequest) {
        String response = postRequest.send();

        try {
            JSONObject res = new JSONObject(response);
            if (res.getBoolean("status")) {
                List<Score> scores = new ArrayList<Score>();

                JSONArray scoresJson = res.getJSONArray("scores");
                for (int i = 0; i < scoresJson.length(); i++) {
                    JSONObject scoreJson = (JSONObject) scoresJson.get(i);
                    scores.add(
                            new Score(
                                    scoreJson.getString("name"),
                                    scoreJson.getInt("value"),
                                    Timestamp.valueOf(scoreJson.getString("date"))
                            )
                    );
                }
                return scores;
            } else {
                return null;
            }
        } catch (JSONException ex) {
            return null;
        }
    }
}