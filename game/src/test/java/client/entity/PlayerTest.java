package client.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    private transient Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("bob", "123");
    }

    @Test
    public void getName() {
        assertEquals(player.getName(), "bob");
    }

    @Test
    public void getPassword() {
        assertEquals(player.getPassword(), "123");
    }

    @Test
    public void tokenTest() {
        player.setToken("a token");

        assertEquals(player.getToken(), "a token");
    }
}
