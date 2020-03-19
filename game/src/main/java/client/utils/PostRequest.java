package client.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class PostRequest {

    private final transient String url;
    private final transient boolean ssl;
    private transient JSONObject parameters;

    /**
     * Constructor for the PostRequest.
     *
     * @param url the url of the target server.
     * @param ssl determines if the post request should use SSL.
     */
    public PostRequest(String url, boolean ssl) {
        this.url = url;
        this.ssl = ssl;
        this.parameters = new JSONObject();
    }

    /**
     * Adds a parameter for the request.
     *
     * @param key of the parameter.
     * @param value of the parameter.
     */
    public void addParameterString(String key, String value) {
        parameters.put(key, value);
    }

    /**
     * Adds a parameter for the request.
     *
     * @param key of the parameter.
     * @param value of the parameter.
     */
    public void addParameterInt(String key, int value) {
        parameters.put(key, value);
    }

    /**
     * Send a post request to the server.
     *
     * @return String with response.
     */
    @SuppressWarnings("PMD.CloseResource")
    public String send() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection request;
            if (ssl) {
                request = (HttpsURLConnection) url.openConnection();
            } else {
                request = (HttpURLConnection) url.openConnection();
            }
            request.setRequestMethod("POST");
            request.setRequestProperty("Content-Type", "application/json; utf-8");
            request.setRequestProperty("Accept", "application/json");
            request.setDoOutput(true);

            OutputStream outputStream = request.getOutputStream();
            byte[] parametersBytes = parameters.toString().getBytes(StandardCharsets.UTF_8);
            outputStream.write(parametersBytes, 0, parametersBytes.length);
            outputStream.close();
            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(request.getInputStream()));

            StringBuilder response = new StringBuilder();
            String responseLine = buffer.readLine();
            while (responseLine != null) {
                response.append(responseLine);
                responseLine = buffer.readLine();
            }
            buffer.close();
            request.disconnect();
            return response.toString();
        } catch (IOException ex) {
            return "IOException: " + ex.toString();
        }
    }
}
