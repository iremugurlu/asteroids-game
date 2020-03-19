package client.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;

import org.junit.jupiter.api.Test;

class PostRequestTest {

    private static String testUrl = "https://httpbin.org/post";

    @Test
    void validPostTest() {
        PostRequest postRequest = new PostRequest(testUrl, false);
        postRequest.addParameterString("key1", "value1");
        postRequest.addParameterInt("key2", 100);
        String response = postRequest.send();
        JSONObject jsonResponse = new JSONObject(response);
        assertEquals(jsonResponse.get("data"), "{\"key1\":\"value1\",\"key2\":100}");
    }

    @Test
    void validPostSslTest() {
        PostRequest postRequest = new PostRequest(testUrl, true);
        postRequest.addParameterString("key2", "value2");
        String response = postRequest.send();
        JSONObject jsonResponse = new JSONObject(response);
        assertEquals(jsonResponse.get("data"), "{\"key2\":\"value2\"}");
    }

    @Test
    void invalidPostTest() {
        String invalidUrl = "invalidUrl";
        PostRequest postRequest = new PostRequest(invalidUrl, true);
        String response = postRequest.send();
        assertEquals(response,
                "IOException: java.net.MalformedURLException: no protocol: invalidUrl");
    }
}