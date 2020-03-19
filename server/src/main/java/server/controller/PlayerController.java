package server.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.entity.Player;
import server.queries.PlayerQueries;
import server.util.Password;
import server.util.Token;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@Controller
@RequestMapping(value = "player")
public class PlayerController {

    private transient PlayerQueries playerQueries;

    public PlayerController(PlayerQueries playerQueries) {
        this.playerQueries = playerQueries;
    }

    /**
     * Checks if the player can login.
     *
     * @param json with the name and password
     * @return Status with token if successful login.
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody
    String loginPlayer(@RequestBody String json) {

        JSONObject res = new JSONObject();
        res.put("status", false);

        try {
            JSONObject obj = new JSONObject(json);
            String name = obj.getString("name");
            String password = obj.getString("password");

            if (name.isEmpty() || password.isEmpty()) {
                res.put("message", "incorrect parameters");
                return res.toString();
            }

            Player player = playerQueries.getPlayerByName(name);
            if (player == null || !Password.valid(player.getPassword(), password, player.getSalt())) {
                res.put("message", "username and password do not match");
                return res.toString();
            }

            res.put("status", true);
            res.put("token", Token.generate(name));
            return res.toString();

        } catch (JSONException ex) {
            res.put("message", "incorrect parameters");
            return res.toString();
        }
    }

    /**
     * Create a new player.
     *
     * @param json with the name and password
     * @return Status with token if successful creation.
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public @ResponseBody
    String createPlayer(@RequestBody String json) {

        JSONObject res = new JSONObject();
        res.put("status", false);

        try {
            JSONObject obj = new JSONObject(json);
            String name = obj.getString("name");
            String password = obj.getString("password");

            if (name.isEmpty() || password.isEmpty()) {
                res.put("message", "incorrect parameters");
                return res.toString();
            }

            if (playerQueries.countPlayerByName(name) != 0) {
                res.put("message", "username already exists");
                return res.toString();
            }

            String salt = Password.salt();
            password = Password.hash(password, salt);

            Player player = new Player();
            player.setName(name);
            player.setPassword(password);
            player.setSalt(salt);

            playerQueries.save(player);
            res.put("status", true);
            res.put("token", Token.generate(name));
            return res.toString();

        } catch (JSONException ex) {
            res.put("message", "incorrect parameters");
            return res.toString();
        }
    }
}
