package server.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private transient Player player;

    @BeforeEach
    public void setUp() {
        player = new Player();
        player.setName("bob");
        player.setPassword("123");
        player.setSalt("some salt");
    }

    @Test
    public void nameTest() {
        assertEquals(player.getName(), "bob");
    }

    @Test
    public void passwordTest() {
        assertEquals(player.getPassword(), "123");
    }

    @Test
    public void saltTest() {
        assertEquals(player.getSalt(), "some salt");
    }
}
