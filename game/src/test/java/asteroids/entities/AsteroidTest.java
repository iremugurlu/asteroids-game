package asteroids.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import asteroids.managers.EntityManager;
import asteroids.util.Vector2f;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests the Asteroid class.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ExtendWith(MockitoExtension.class)
public class AsteroidTest {

    private Asteroid asteroid;

    @Mock
    private Texture texture;

    @Mock
    private transient SpriteBatch batch;

    @Mock
    private Random random;

    private EntityManager manager;

    /**
     * Setup an default asteroid object, ran before each test.
     */
    @BeforeEach
    public void setUp() {
        manager = new EntityManager(batch, texture, random);

        when(texture.getWidth()).thenReturn(16);
        this.asteroid = new Asteroid(0,5,0.5,new Vector2f(0, 0), texture);
    }

    /**
     * Tests if the rotation of the sprite is set correctly when the asteroid is created.
     */
    @Test
    public void getSpriteTest() {
        Sprite s = asteroid.getSprite();
        assertEquals(s.getRotation(), asteroid.getRotation());
    }

    /**
     * Tests the getRotation function.
     */
    @Test
    public void getRotationTest() {
        assertEquals(0, asteroid.getRotation());
    }

    /**
     * Tests the setRotation function.
     */
    @Test
    public void setRotationTest() {
        asteroid.setRotation(-270);
        assertEquals(90, asteroid.getRotation());
    }

    /**
     * Tests the getAcceleration function.
     */
    @Test
    public void getAccelerationTest() {
        assertEquals(0, asteroid.getAcceleration());
    }

    /**
     * Tests the setAcceleration function.
     */
    @Test
    public void setAccelerationTest() {
        asteroid.setAcceleration(5.50f);
        assertEquals(5.50f, asteroid.getAcceleration());
    }

    /**
     * Tests the getRotationSpeed function.
     */
    @Test
    public void getRotationSpeedTest() {
        assertEquals(0, asteroid.getRotationSpeed());
    }

    /**
     * Tests the setRotationSpeed function.
     */
    @Test
    public void setRotationSpeedTest() {
        asteroid.setRotationSpeed(9);
        assertEquals(9, asteroid.getRotationSpeed());
    }

    /**
     * Tests the getSpeed function.
     */
    @Test
    public void getSpeedTest() {
        assertEquals(5, asteroid.getSpeed());
    }

    /**
     * Tests the setSpeed function.
     */
    @Test
    public void setSpeedTest() {
        asteroid.setSpeed(90);
        assertEquals(90, asteroid.getSpeed());
    }

    /**
     * Tests the setSpeed function.
     */
    @Test
    public void translateTest() {
        Vector2f translation = new Vector2f(
                (float) (1),
                (float) (2)
        );

        asteroid.translate(translation);
        assertEquals(1, asteroid.getPosition().getCoordinateX());
        assertEquals(2, asteroid.getPosition().getCoordinateY());
    }


    /**
     * Tests the getSize function.
     */
    @Test
    public void getSizeTest() {
        assertEquals(0.5, asteroid.getSize());
    }

    /**
     * Tests the getRadiusCollision function.
     */
    @Test
    public void getRadiusCollisionTest() {
        assertEquals(16, asteroid.getDiameterCollision());
        asteroid.getSprite();
        assertEquals(8, asteroid.getDiameterCollision());
    }

    /**
     * Tests if the asteroid "dies" after colliding with an player.
     * And spawns new asteroids.
     */
    @Test
    public void onCollisionTest3() {
        assertTrue(asteroid.isAlive());
        Player p = new Player(0, 0, 0, new Vector2f(0, 0), texture,1, 0);
        Asteroid a1 = new Asteroid(0,5,0.05,new Vector2f(0, 0), texture);

        a1.onCollision(p);
        assertFalse(a1.isAlive());

        manager.update(0);

        assertEquals(0, manager.getEntities().size());
    }

    /**
     * Tests if nothing happens when colliding with another asteroid.
     */
    @Test
    public void onCollisionTest2() {
        assertTrue(asteroid.isAlive());
        asteroid.onCollision(asteroid);
        assertTrue(asteroid.isAlive());
    }

    /**
     * Tests if the asteroid "dies" after colliding with an basicEnemyShip.
     */
    @Test
    public void onCollisionTest4() {
        assertTrue(asteroid.isAlive());
        BasicEnemy b = new BasicEnemy(0,5,0.5, new Vector2f(0, 0), texture);
        asteroid.onCollision(b);
        assertFalse(asteroid.isAlive());

        manager.update(0);
        assertTrue(manager.getEntities().size() > 1);
    }

    /**
     * Tests if the asteroid "dies" after colliding with an player.
     * And spawns new asteroids if it is small enough.
     */
    @Test
    public void onCollisionTest() {
        assertTrue(asteroid.isAlive());
        Player p = new Player(0, 0, 0, new Vector2f(0, 0), texture,1, 1);
        asteroid.onCollision(p);
        assertFalse(asteroid.isAlive());

        manager.update(0);
        assertTrue(manager.getEntities().size() > 1);
    }

    /**
     * Tests if nothing happens if an asteroid collides with a powerUp.
     */
    @Test
    public void onCollisionPowerupTest() {
        assertTrue(asteroid.isAlive());

        PowerupHeal powerupHeal = new PowerupHeal(1, new Vector2f(0, 0), texture);

        asteroid.onCollision(powerupHeal);
        assertTrue(asteroid.isAlive());
    }


    /**
     * Tests if the isAlive and die functions are working correctly.
     */
    @Test
    public void dieTest() {
        assertTrue(asteroid.isAlive());
        asteroid.die();
        assertFalse(asteroid.isAlive());
    }

    /**
     * Test if the asteroid moves forward correctly when rotated 0 degrees. (Looking to the upwards)
     */
    @Test
    public void updateTest() {
        assertEquals(-8, (int) asteroid.getSprite().getX());
        assertEquals(0, (int) asteroid.getSprite().getY());

        asteroid.update(1);

        assertEquals(-8, (int) asteroid.getSprite().getX());
        assertEquals(5, (int) asteroid.getSprite().getY());
    }

    /**
     * Test if the asteroid moves forward correctly when rotated 90 degrees. (Looking to the left)
     */
    @Test
    public void updateTest2() {
        asteroid.setRotation(90);
        assertEquals(-8, (int) asteroid.getSprite().getX());
        assertEquals(0, (int) asteroid.getSprite().getY());

        asteroid.update(1);

        assertEquals(-13, (int) asteroid.getSprite().getX());
        assertEquals(0, (int) asteroid.getSprite().getY());
    }
}
