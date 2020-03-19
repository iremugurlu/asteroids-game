package client.controller;

import client.entity.Player;
import client.utils.PostRequest;
import client.utils.PropertyFile;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerController {

    private transient Player player;
    private final transient PropertyFile propertyFile;
    private transient String message;

    /**
     * Constructor for the PlayerController class.
     * @param player parameter player.
     */
    public PlayerController(Player player) {
        this.player = player;
        propertyFile = new PropertyFile("url.properties");
        this.message = "";
    }

    /**
     * Makes a post request to the server to
     * check the credentials of the user.
     * @return true if login is successful false if login has failed.
     */
    public boolean login() {
        String loginUrl = propertyFile.getProperties().getProperty("url.player.login");
        PostRequest postRequest = new PostRequest(loginUrl, true);
        postRequest.addParameterString("name", player.getName());
        postRequest.addParameterString("password", player.getPassword());
        String response = postRequest.send();
        try {
            JSONObject res = new JSONObject(response);
            boolean status = res.getBoolean("status");
            if (status) {
                String token = res.getString("token");
                player.setToken(token);
                return true;
            } else {
                message = res.getString("message");
                return false;
            }
        } catch (JSONException ex) {
            return false;
        }
    }

    /**
     * Makes a post request to the server to
     * create the user.
     * @return true if login is successful false if login has failed.
     */
    public boolean create() {
        String createUrl = propertyFile.getProperties().getProperty("url.player.create");
        PostRequest postRequest = new PostRequest(createUrl, true);
        postRequest.addParameterString("name", player.getName());
        postRequest.addParameterString("password", player.getPassword());
        String response = postRequest.send();
        try {
            JSONObject res = new JSONObject(response);
            boolean status = res.getBoolean("status");
            if (status) {
                String token = res.getString("token");
                player.setToken(token);
                return true;
            } else {
                message = res.getString("message");
                return false;
            }
        } catch (JSONException ex) {
            return false;
        }
    }

    public String getMessage() {
        return message;
    }
}