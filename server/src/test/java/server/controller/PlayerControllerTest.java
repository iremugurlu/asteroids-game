package server.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import server.entity.Player;
import server.queries.PlayerQueries;
import server.util.Password;
import server.util.Token;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RunWith(PowerMockRunner.class)
@PrepareForTest({PlayerQueries.class, Password.class, Token.class})
public class PlayerControllerTest {

    private transient PlayerController playerController;
    private transient PlayerQueries playerQueries;

    @Before
    public void setUp() {
        System.setProperty("illegal-access", "deny");
        PowerMockito.mockStatic(PlayerQueries.class, Password.class, Token.class);
        playerController = new PlayerController(playerQueries);
    }

    @Test
    public void loginNotJson() {
        JSONObject res = new JSONObject(playerController.loginPlayer("not json"));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void loginEmptyName() {
        JSONObject req = new JSONObject();
        req.put("name", "");
        req.put("password", "123");

        JSONObject res = new JSONObject(playerController.loginPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void loginEmptyPassword() {
        JSONObject req = new JSONObject();
        req.put("name", "bob");
        req.put("password", "");

        JSONObject res = new JSONObject(playerController.loginPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void loginNoPlayerExists() {
        JSONObject req = new JSONObject();
        req.put("name", "bob");
        req.put("password", "123");

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);
        when(mockPlayerQueries.getPlayerByName("bob")).thenReturn(null);

        when(Password.valid(anyString(), anyString(), anyString())).thenReturn(false);

        PlayerController playerController = new PlayerController(mockPlayerQueries);
        JSONObject res = new JSONObject(playerController.loginPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "username and password do not match");
    }

    @Test
    public void loginFail() {
        JSONObject req = new JSONObject();
        req.put("name", "bob");
        req.put("password", "123");

        Player player = new Player();
        player.setName("bob");
        player.setPassword("123");
        player.setSalt("some salt");

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);
        when(mockPlayerQueries.getPlayerByName("bob")).thenReturn(player);

        when(Password.valid(anyString(), anyString(), anyString())).thenReturn(false);

        PlayerController playerController = new PlayerController(mockPlayerQueries);
        JSONObject res = new JSONObject(playerController.loginPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "username and password do not match");
    }

    @Test
    public void loginSuccess() {
        JSONObject req = new JSONObject();
        req.put("name", "bob");
        req.put("password", "123");

        Player player = new Player();
        player.setName("bob");
        player.setPassword("123");
        player.setSalt("some salt");

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);
        when(mockPlayerQueries.getPlayerByName("bob")).thenReturn(player);

        when(Password.valid(anyString(), anyString(), anyString())).thenReturn(true);
        when(Token.generate(anyString())).thenReturn("a token");

        PlayerController playerController = new PlayerController(mockPlayerQueries);
        JSONObject res = new JSONObject(playerController.loginPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), true);
        assertEquals(res.getString("token"), "a token");
    }

    @Test
    public void createNotJson() {
        JSONObject res = new JSONObject(playerController.createPlayer("not json"));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void createEmptyName() {
        JSONObject req = new JSONObject();
        req.put("name", "");
        req.put("password", "123");

        JSONObject res = new JSONObject(playerController.createPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void createEmptyPassword() {
        JSONObject req = new JSONObject();
        req.put("name", "bob");
        req.put("password", "");

        JSONObject res = new JSONObject(playerController.createPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "incorrect parameters");
    }

    @Test
    public void createPlayerExists() {
        JSONObject req = new JSONObject();
        req.put("name", "bob");
        req.put("password", "123");

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);
        when(mockPlayerQueries.countPlayerByName("bob")).thenReturn(1);

        PlayerController playerController = new PlayerController(mockPlayerQueries);
        JSONObject res = new JSONObject(playerController.createPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), false);
        assertEquals(res.getString("message"), "username already exists");
    }

    @Test
    public void createSuccess() {
        JSONObject req = new JSONObject();
        req.put("name", "bob");
        req.put("password", "123");

        PlayerQueries mockPlayerQueries = mock(PlayerQueries.class);
        when(mockPlayerQueries.countPlayerByName("bob")).thenReturn(0);

        when(Password.salt()).thenReturn("some salt");
        when(Password.hash("123", "some salt")).thenReturn("hashed password");
        when(Token.generate(anyString())).thenReturn("a token");

        PlayerController playerController = new PlayerController(mockPlayerQueries);
        JSONObject res = new JSONObject(playerController.createPlayer(req.toString()));

        assertEquals(res.getBoolean("status"), true);
        assertEquals(res.getString("token"), "a token");
    }

}
