package asteroids.managers;

import java.util.Arrays;

/**
 * Score Manager class, used to track the scores of all players in the current game.
 */
public class ScoreManager {

    private transient int[] scores;
    private transient int[] scoreMultiplier;

    private static ScoreManager object = null;

    /**
     * Creates a new score manager, supports up to 2 players.
     */
    public ScoreManager() {
        this.scores = new int[2];
        this.scoreMultiplier = new int[2];

        Arrays.fill(scoreMultiplier, 1);
    }

    /**
     * Method to get the instance of the ScoreManager class.
     *
     * @return Instance of ScoreManager.
     */
    public static ScoreManager getInstance() {
        if (object == null) {
            object = new ScoreManager();
        }

        return object;
    }

    /**
     * Method to get reset the instance of the ScoreManager class.
     */
    public static void reset() {
        object = new ScoreManager();
    }


    /**
     * Increases the score of a player by a given amount of points.
     *
     * @param pointAmount The amount of points the player needs to receive.
     * @param playerId    The Id of the player that needs to receive the points.
     */
    public void increaseScore(int pointAmount, int playerId) {
        //assert playerId >= 0 && playerId < playerAmount;

        scores[playerId] += pointAmount * scoreMultiplier[playerId];
    }

    /**
     * Decrease the score of a player by a given amount of points.
     *
     * @param pointAmount        The amount of points that the score of
     *                           the player needs to be decreased with.
     * @param playerId           The Id of the player whose points needs to be decreased.
     * @param useScoreMultiplier Should we use the current scoreMultiplier of the player?
     */
    public void decreaseScore(int pointAmount, int playerId, boolean useScoreMultiplier) {
        //assert playerId >= 0 && playerId < playerAmount;

        if (!useScoreMultiplier) {
            scores[playerId] -= pointAmount;
        } else {
            scores[playerId] -= pointAmount * scoreMultiplier[playerId];
        }
    }

    /**
     * Set score Multiplier of a player, expects the multiplier to be higher than 0.
     *
     * @param scoreMultiplier The new scoreMultiplier of this player.
     * @param playerId        The Id of the player that the scoreMultiplier needs to be set of.
     */
    public void setScoreMultiplier(int scoreMultiplier, int playerId) {
        //assert scoreMultiplier > 0;
        //assert playerId >= 0 && playerId < playerAmount;

        this.scoreMultiplier[playerId] = scoreMultiplier;
    }

    /**
     * Sets the scoreMultiplier of all players in the current game to a certain value.
     *
     * @param scoreMultiplier The value that the score multiplier
     *                        of every player needs to be set to.
     */
    public void setScoreMultiplierAll(int scoreMultiplier) {
        for (int i = 0; i < this.scoreMultiplier.length; i++) {
            this.scoreMultiplier[i] = scoreMultiplier;
        }
    }

    /**
     * Resets the the scoreMultiplier of a player.
     *
     * @param playerId The Id of the player that the scoreMultiplier needs to be reset of.
     */
    public void resetScoreMultiplier(int playerId) {
        //assert playerId >= 0 && playerId < playerAmount;

        this.scoreMultiplier[playerId] = 1;
    }

    /**
     * Get the score of a specific player.
     *
     * @param playerId The Id of the player that you need to score of.
     * @return The current score of the player.
     */
    public int getScore(int playerId) {
        //assert playerId >= 0 && playerId < playerAmount;

        return scores[playerId];
    }

    /**
     * Get a list of with the score of all players in the game.
     *
     * @return A list with the score of all players in the game,
     *     in order and starting with player 0.
     */
    public int[] getScoreAll() {
        return scores;
    }

    /**
     * Disposes of the current instance of ScoreManager.
     */
    @SuppressWarnings("PMD.NullAssignment")
    public void dispose() {
        object = null;
    }
}
