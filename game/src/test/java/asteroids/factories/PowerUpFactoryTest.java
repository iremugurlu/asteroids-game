package asteroids.factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import asteroids.entities.Powerup;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PowerUpFactoryTest {

    private transient PowerUpFactory puf;

    @Mock
    private transient Random random;

    @Mock
    private transient Texture texture;

    @BeforeEach
    public void setUp() {
        puf = new PowerUpFactory(texture, random);
    }

    /**
     * Tests if a powerUp spawns if the random number generator returns 0.
     */
    @Test
    public void create_WhenRandomIsZero_ThenSpawnPowerUp() {
        when(random.nextInt()).thenReturn(0);

        Powerup pup = puf.create(new Vector2f(0,0));
        assertNotNull(pup);
    }

    /**
     * Tests if a powerUp does not spawn if the random number generator does not return 0.
     */
    @Test
    public void create_WhenRandomIsNotZero_ThenDoNotSpawnPowerUp() {
        when(random.nextInt()).thenReturn(1);

        Powerup pup = puf.create(new Vector2f(0,0));
        assertNull(pup);
    }
}
