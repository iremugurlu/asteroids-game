package asteroids.managers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import asteroids.entities.Asteroid;
import asteroids.entities.Entity;
import asteroids.entities.Player;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CollisionsManagerTest {

    @Mock
    private transient Texture texture;

    private transient ArrayList<Entity> entities;
    private transient CollisionsManager manager;

    private transient Player p0;
    private transient Asteroid a1;

    @Mock
    private transient SpriteBatch batch;

    @Mock
    private transient Random random;

    /**
     * Setup, ran before each test.
     * Sets up the CollisionsManager and texture/entities variables.
     */
    @BeforeEach
    public void setUp() {
        when(texture.getWidth()).thenReturn(32);
        entities = new ArrayList<>();
        manager = new CollisionsManager(entities);

        new EntityManager(batch, texture, random);
    }

    private void calculateCollisions_preConditions(Vector2f zero, Vector2f one) {
        //Spawn players
        p0 = new Player(0, 1, 32, zero, texture,0, 0);
        a1 = new Asteroid(0, 1, 0.05, one, texture);

        entities.add(p0);
        entities.add(a1);
        //Check that they are alive at the beginning of the game
        assertTrue(p0.isAlive());
        assertTrue(a1.isAlive());
    }

    @Test
    public void calculateCollisions_EntitiesSeparated_ThenNoCollisions() {
        calculateCollisions_preConditions(new Vector2f(0, 0), new Vector2f(0, 100));

        //Calculate collisions, killing colliding Entities
        manager.calculateCollisions();

        //Check that the players are still alive
        assertTrue(p0.isAlive());
        assertTrue(a1.isAlive());
    }

    @Test
    public void calculateCollisions_EntitiesTooClose_ThenCollide() {
        calculateCollisions_preConditions(new Vector2f(0, 0), new Vector2f(0, 10));

        //Calculate collisions, killing colliding Entities
        manager.calculateCollisions();

        //Check that both players are dead
        assertFalse(p0.isAlive());
        assertFalse(a1.isAlive());
    }

    @Test
    public void calculateCollisions_EntitiesScrapeByEachOther_ThenNoCollisions() {
        calculateCollisions_preConditions(new Vector2f(0, 0), new Vector2f(0, 32));

        //Calculate collisions, killing colliding Entities
        manager.calculateCollisions();

        //Check that both players are still alive
        assertTrue(p0.isAlive());
        assertTrue(a1.isAlive());
    }

    /**
     * Tests that a player doesn't die when it collides with a dead entity.
     */
    @Test
    public void calculateCollisions_OneEntityDead_ThenCollide() {
        calculateCollisions_preConditions(new Vector2f(0, 0), new Vector2f(0, 10));
        p0.die();

        //Calculate collisions, killing colliding Entities
        manager.calculateCollisions();

        //Check that one player is dead
        assertFalse(p0.isAlive());
        assertTrue(a1.isAlive());
    }

    /**
     * Tests that a player doesn't die when it collides with a dead entity.
     */
    @Test
    public void calculateCollisions_OneEntityDead_ThenCollide2() {
        calculateCollisions_preConditions(new Vector2f(0, 0), new Vector2f(0, 10));
        a1.die();

        //Calculate collisions, killing colliding Entities
        manager.calculateCollisions();

        //Check that one player is dead
        assertTrue(p0.isAlive());
        assertFalse(a1.isAlive());
    }
}
