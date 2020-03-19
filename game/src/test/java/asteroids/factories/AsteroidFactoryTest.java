package asteroids.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import asteroids.entities.Asteroid;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests the AsteroidFactory class.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ExtendWith(MockitoExtension.class)
public class AsteroidFactoryTest {

    private AsteroidFactory factory;

    @Mock
    private Texture texture;

    /**
     * Setup an default AsteroidFactory object, ran before each test.
     */
    @BeforeEach
    public void setUp() {
        this.factory = new AsteroidFactory(1, 1, texture, true);
    }

    /**
     * Tests if the AsteroidFactory correctly creates an asteroid
     * when the createAsteroid method is called.
     */
    @Test
    public void createAsteroidTest() {
        Asteroid asteroid = factory.create(new Vector2f(0, 0));

        assertEquals(1, asteroid.getSize());
    }
}
