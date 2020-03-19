package client.controller;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import client.entity.Player;
import client.utils.PostRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RunWith(PowerMockRunner.class)
@PrepareForTest({PostRequest.class, PlayerController.class})
public class PlayerControllerTest {

    private transient Player player;
    private transient PlayerController playerController;
    private transient PostRequest mock;

    /**
     * Ran before starting to test.
     */
    @Before
    public void setUp() throws Exception {
        System.setProperty("illegal-access", "deny");
        player = new Player("bob", "123");
        playerController = new PlayerController(player);
        mock = mock(PostRequest.class);
        whenNew(PostRequest.class).withAnyArguments().thenReturn(mock);
    }

    @Test
    public void loginSuccess() {
        JSONObject res = new JSONObject();
        res.put("status", true);
        res.put("token", "a token");
        when(mock.send()).thenReturn(res.toString());

        assertEquals(playerController.login(), true);
        assertEquals(player.getToken(), "a token");
    }

    @Test
    public void loginFail() {
        JSONObject res = new JSONObject();
        res.put("status", false);
        res.put("message", "incorrect parameters");
        when(mock.send()).thenReturn(res.toString());

        assertEquals(playerController.login(), false);
        assertEquals(player.getToken(), null);
    }

    @Test
    public void loginNoResponse() {
        when(mock.send()).thenReturn("");

        assertEquals(playerController.login(), false);
        assertEquals(player.getToken(), null);
    }

    @Test
    public void createSuccess() {
        JSONObject res = new JSONObject();
        res.put("status", true);
        res.put("token", "a token");
        when(mock.send()).thenReturn(res.toString());

        assertEquals(playerController.create(), true);
        assertEquals(player.getToken(), "a token");
    }

    @Test
    public void createFail() {
        JSONObject res = new JSONObject();
        res.put("status", false);
        res.put("message", "incorrect parameters");
        when(mock.send()).thenReturn(res.toString());

        assertEquals(playerController.create(), false);
        assertEquals(player.getToken(), null);
    }

    @Test
    public void createNoResponse() {
        when(mock.send()).thenReturn("");

        assertEquals(playerController.create(), false);
        assertEquals(player.getToken(), null);
    }
}
