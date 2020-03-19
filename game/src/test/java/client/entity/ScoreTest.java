package client.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"PMD.AvoidDuplicateLiterals"})
public class ScoreTest {

    private transient Score score;

    @BeforeEach
    public void setUp() {
        score = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));
    }

    @Test
    public void getName() {
        assertEquals(score.getName(), "bob");
    }

    @Test
    public void getValue() {
        assertEquals(score.getValue(), 100);
    }

    @Test
    public void getDate() {
        assertEquals(score.getDate(), Timestamp.valueOf("2019-01-01 00:00:00.0"));
    }

    @Test
    public void equalsNull() {
        Score score1 = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));
        Score score2 = null;

        assertEquals(score1.equals(score2), false);
    }

    @Test
    public void equalsNotScore() {
        Score score1 = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));
        Object score2 = new Object();

        assertEquals(score1.equals(score2), false);
    }

    @Test
    public void equalsFailName() {
        Score score1 = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));
        Object score2 = new Score("alice", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));

        assertEquals(score1.equals(score2), false);
    }

    @Test
    public void equalsFailScore() {
        Score score1 = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));
        Object score2 = new Score("bob", 200, Timestamp.valueOf("2019-01-01 00:00:00.0"));

        assertEquals(score1.equals(score2), false);
    }

    @Test
    public void equalsFailDate() {
        Score score1 = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));
        Object score2 = new Score("bob", 100, Timestamp.valueOf("2019-12-12 00:00:00.0"));

        assertEquals(score1.equals(score2), false);
    }

    @Test
    public void equalsSuccess() {
        Score score1 = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));
        Object score2 = new Score("bob", 100, Timestamp.valueOf("2019-01-01 00:00:00.0"));

        assertEquals(score1.equals(score2), true);
    }
}
