package asteroids.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import asteroids.entities.Asteroid;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EntityManagerTest {

    @Mock
    private transient Texture texture;

    @Mock
    private transient SpriteBatch batch;

    private transient EntityManager manager;
    private transient Asteroid asteroid;

    @Mock
    private transient Random random;

    @BeforeEach
    public void setUp() {
        manager = new EntityManager(batch, texture, random);
        asteroid = new Asteroid(1, 1, 1, new Vector2f(100, 100), texture);
    }

    @Test
    public void isRunningTest() {
        assertTrue(manager.isRunning());
    }

    @Test
    public void setVersusTest() {
        assertFalse(manager.getVersus());

        manager.setVersus(true);

        assertTrue(manager.getVersus());
    }

    @Test
    public void checkLiving_WhenAddEntity_ListNotEmpty() {
        //Spawn entity
        assertTrue(manager.getEntities().isEmpty());
        assertTrue(manager.spawnEntity(asteroid));
        manager.update(0);

        assertTrue(manager.getEntities().contains(asteroid));
    }

    @Test
    public void checkLiving_WhenNextFrame_LivingEntitiesAreStillAlive() {
        //Check that the entity is still alive after one frame
        assertTrue(manager.getEntities().isEmpty());

        manager.spawnEntity(asteroid);
        manager.update(0);

        assertFalse(manager.getEntities().isEmpty());
        manager.update(1);

        assertFalse(manager.getEntities().isEmpty());
    }

    @Test
    public void checkLiving_WhenEntityDies_RemoveEntityFromList() {
        //Kill entity
        assertTrue(manager.getEntities().isEmpty());

        manager.spawnEntity(asteroid);
        manager.update(0);

        assertFalse(manager.getEntities().isEmpty());

        asteroid.die();

        assertFalse(manager.getEntities().isEmpty());

        manager.update(1);

        assertTrue(manager.getEntities().isEmpty());
    }
}
