package server.entity;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    private transient Score score;
    private transient Player player;

    @BeforeEach
    public void setUp() {
        player = new Player();
        player.setName("bob");

        score = new Score();
        score.setPlayer(player);
        score.setDate(Timestamp.valueOf("2019-01-01 00:00:00.0"));
        score.setValue(100);
    }

    @Test
    public void scoreTest() {
        assertEquals(score.getValue(), 100);
    }

    @Test
    public void dateTest() {
        assertEquals(score.getDate().toString(), "2019-01-01 00:00:00.0");
    }

    @Test
    public void playerTest() {
        assertEquals(score.getPlayer(), player);
    }
}
