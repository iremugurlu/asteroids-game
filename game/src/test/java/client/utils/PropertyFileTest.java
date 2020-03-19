package client.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import client.controller.PlayerController;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Properties.class, PlayerController.class})
class PropertyFileTest {

    @Test
    void validFile() {
        PropertyFile propertyFile = new PropertyFile("url.properties");
        Properties properties = propertyFile.getProperties();
        String url = properties.getProperty("url.player.login");

        assertEquals(url, "https://asteroids58.herokuapp.com/player/login/");
    }

    @Test
    void inValidFile() {
        PropertyFile propertyFile = new PropertyFile("some file");
        Properties properties = propertyFile.getProperties();

        assertEquals(properties, null);
    }
}
