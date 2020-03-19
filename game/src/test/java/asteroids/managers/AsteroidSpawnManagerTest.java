package asteroids.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import asteroids.input.InputHandler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;
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
public class AsteroidSpawnManagerTest {

    private AsteroidSpawnManager asteroidSpawnManager;

    @Mock
    private Texture texture;

    @Mock
    private SpriteBatch batch;

    @Mock
    private InputHandler inputHandler;

    @Mock
    private Random random;

    private EntityManager manager;

    /**
     * Setup an default AsteroidSpawnManager object, ran before each test.
     */
    @BeforeEach
    public void setUp() {
        manager = new EntityManager(batch, texture, random);
        manager.init(texture, inputHandler);

        this.asteroidSpawnManager = new AsteroidSpawnManager(texture);
    }

    /**
     * Tests if no asteroids get spanwed after if it hasn't been 2s yet.
     */
    @Test
    public void updateTest1() {
        asteroidSpawnManager.update(1);

        assertEquals(0, manager.getEntities().size());
    }

    /**
     * Tests if an asteroid gets spawned after 2s.
     */
    @Test
    public void updateTest2() {
        asteroidSpawnManager.update(3);
        manager.update(0);

        assertEquals(2, manager.getEntities().size());
    }

    /**
     * Tests if multiple asteroids spawn correctly.
     */
    @Test
    public void updateTest3() {
        asteroidSpawnManager.update(2);
        asteroidSpawnManager.update(2);
        asteroidSpawnManager.update(2);
        asteroidSpawnManager.update(2);
        manager.update(0);

        assertEquals(5, manager.getEntities().size());
    }
}
