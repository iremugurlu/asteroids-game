package asteroids.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the ScoreManager class.
 */
public class ScoreManagerTest {

    private transient ScoreManager scoreManager;

    /**
     * Setup, create scoreManager object before running each test.
     */
    @BeforeEach
    void setUp() {
        scoreManager = new ScoreManager();
    }

    /**
     * Tests the constructor.
     */
    @Test
    void testConstructor() {
        ScoreManager testScoreManager = new ScoreManager();

        assertEquals(2, testScoreManager.getScoreAll().length);
    }

    //    /**
    //     * Tests if the constructor throws an AssertionError
    //     * if an incorrect player amount is provided.
    //     */
    //    @Test
    //    void testConstructorInvalid() {
    //        assertThrows(AssertionError.class, () -> {
    //            new ScoreManager(0);
    //        });
    //    }

    /**
     * Tests the scoreAll method.
     */
    @Test
    void testScoreAll() {
        scoreManager.increaseScore(10, 0);
        scoreManager.increaseScore(20, 1);

        int[] expected = {10, 20};
        assertTrue(Arrays.equals(expected, scoreManager.getScoreAll()));
    }

    /**
     * Tests the increaseScore method.
     */
    @Test
    void testIncreaseScore() {
        scoreManager.increaseScore(50, 0);

        assertEquals(50, scoreManager.getScore(0));
    }

    /**
     * Tests if the increaseScore method works with than 1 player.
     */
    @Test
    void testIncreaseScoreTwoPlayers() {
        scoreManager.increaseScore(150, 0);
        scoreManager.increaseScore(550, 1);
        scoreManager.increaseScore(125, 0);

        assertEquals(275, scoreManager.getScore(0));
        assertEquals(550, scoreManager.getScore(1));
    }

    //    /**
    //     * Tests if the increaseScore method throws an assertion error
    //     * when providing an invalid playerId.
    //     */
    //    @Test
    //    void testIncreaseScoreInvalidPlayer() {
    //        assertThrows(AssertionError.class, () -> {
    //            scoreManager.increaseScore(50, 5);
    //        });
    //
    //        assertThrows(AssertionError.class, () -> {
    //            scoreManager.increaseScore(50, -1);
    //        });
    //    }

    /**
     * Tests the decreaseScore method.
     */
    @Test
    void testDecreaseScore() {
        scoreManager.increaseScore(150, 0);
        scoreManager.decreaseScore(50, 0, false);

        assertEquals(100, scoreManager.getScore(0));
    }

    /**
     * Tests the decreaseScore method with the scoreMultiplier enabled.
     */
    @Test
    void testDecreaseScoreWithMultiplier() {
        scoreManager.increaseScore(150, 0);
        scoreManager.decreaseScore(50, 0, true);

        assertEquals(100, scoreManager.getScore(0));
    }

    /**
     * Tests the decreaseScore method with the scoreMultiplier enabled, that was actually changed.
     */
    @Test
    void testDecreaseScoreWithChangedMultiplier() {
        scoreManager.increaseScore(150, 0);
        scoreManager.setScoreMultiplier(2, 0);
        scoreManager.decreaseScore(50, 0, true);

        assertEquals(50, scoreManager.getScore(0));
    }

    //    /**
    //     * Tests if the increaseScore method throws an assertion error
    //     * when providing an invalid playerId.
    //     */
    //    @Test
    //    void testDecreaseScoreInvalidPlayer() {
    //        assertThrows(AssertionError.class, () -> {
    //            scoreManager.decreaseScore(50, 4, false);
    //        });
    //    }

    /**
     * Tests the SetScoreMultiplierAll method.
     */
    @Test
    void testSetScoreMultiplierAll() {
        scoreManager.setScoreMultiplierAll(2);
        scoreManager.increaseScore(50, 0);
        scoreManager.increaseScore(250, 1);

        assertEquals(100, scoreManager.getScore(0));
        assertEquals(500, scoreManager.getScore(1));
    }

    /**
     * Tests the SetScoreMultiplier method.
     */
    @Test
    void testSetScoreMultiplier() {
        scoreManager.setScoreMultiplier(2, 1);
        scoreManager.increaseScore(50, 1);
        scoreManager.setScoreMultiplier(3, 1);
        scoreManager.increaseScore(50, 1);
        scoreManager.setScoreMultiplier(1, 1);
        scoreManager.increaseScore(500, 1);

        assertEquals(750, scoreManager.getScore(1));
    }

    //    /**
    //     * Tests if the setScoreMultiplier method throws an assertion error
    //     * when providing an incorrect scoreMultiplier.
    //     */
    //    @Test
    //    void testSetInvalidScoreMultiplier() {
    //        assertThrows(AssertionError.class, () -> {
    //            scoreManager.setScoreMultiplier(-1, 0);
    //        });
    //    }

    //    /**
    //     * Tests if the setScoreMultiplier method throws an assertion error
    //     * when providing an incorrect playerId.
    //     */
    //    @Test
    //    void testSetScoreMultiplierInvalidPlayer() {
    //        assertThrows(AssertionError.class, () -> {
    //            scoreManager.setScoreMultiplier(1, -1);
    //        });
    //    }

    /**
     * Tests the ResetScoreMultiplier method.
     */
    @Test
    void testResetScoreMultiplier() {
        scoreManager.setScoreMultiplier(2, 1);
        scoreManager.increaseScore(50, 1);
        scoreManager.resetScoreMultiplier(1);
        scoreManager.increaseScore(50, 1);

        assertEquals(150, scoreManager.getScore(1));
    }

    //    /**
    //     * Tests if the ResetScoreMultiplier throws an assertionError
    //     * when provided with an incorrect playerId.
    //     */
    //    @Test
    //    void testResetScoreMultiplierInvalidPlayer() {
    //        assertThrows(AssertionError.class, () -> {
    //            scoreManager.resetScoreMultiplier(99);
    //        });
    //    }
}
